# -*- coding:utf-8 -*-
import tensorflow as tf
import numpy as np

from deepctr.layers.attention_layer import SelfAttention
from deepctr.layers.ffn_layer import FeedFowardNetwork
from ..inputs import input_from_feature_columns, build_input_features
from ..layers.core import PredictionLayer, DNN
from ..layers.utils import concat_fun
from tensorflow.python.keras.initializers import RandomNormal
from tensorflow.python.keras.regularizers import l2
from ..utils import model_utils

_NEG_INF = -1e9


class TransformerModel(object):
    """Transformer model for non-sequential data.
  
    Implemented as described in: https://arxiv.org/pdf/1706.03762.pdf
    """
    
    def __init__(self, feature_columns, params, hidden_size=10, l2_reg_embedding=0.0, dropout_embedding=0,
                 num_hidden_layers=6, num_heads=2, dropout_attention=0.0, feed_forward_hidden_size=1024,
                 dropout_relu=0.0, dnn_hidden_units=(), dnn_activation='relu', l2_reg_dnn=0.0, dnn_use_bn=False,
                 dropout_dnn=0.0, task='binary', seed=1024):
        """Initialize TransformerModel
        
        :param feature_columns:
        :param params:
        :param hidden_size:
        :param l2_reg_embedding:
        :param dropout_embedding:
        :param num_hidden_layers:
        :param num_heads:
        :param dropout_attention:
        :param feed_forward_hidden_size:
        :param dropout_relu:
        :param dnn_hidden_units:
        :param dnn_activation:
        :param l2_reg_dnn:
        :param dnn_use_bn:
        :param dropout_dnn:
        :param task:
        :param seed:
        """
        super(TransformerModel, self).__init__()
        self.feature_columns = feature_columns
        self.params = params
        
        self.hidden_size = hidden_size
        self.l2_reg_embedding = l2_reg_embedding
        self.dropout_embedding = dropout_embedding
        
        self.num_hidden_layers = num_hidden_layers
        self.num_heads = num_heads
        self.dropout_attention = dropout_attention
        self.feed_forward_hidden_size = feed_forward_hidden_size
        self.dropout_relu = dropout_relu
        
        self.dnn_hidden_units = dnn_hidden_units
        self.dnn_activation = dnn_activation
        self.l2_reg_dnn = l2_reg_dnn
        self.dnn_use_bn = dnn_use_bn
        self.dropout_dnn = dropout_dnn
        
        self.task = task
        self.seed = seed
        
        tf.enable_resource_variables()
        return
    
    def get_model(self):
        self.features, self.inputs_list, self.inputs = self.get_inputs()
        self.encoder_inputs = self.get_encoder_inputs()
        self.encoder_output, self.mha_outputs, self.ff_outputs = self.get_encoder_outputs()
        self.output = self.to_predict()
        model = tf.keras.models.Model(inputs=self.inputs_list, outputs=self.output)
        return model
    
    def get_inputs(self):
        # 生成Multi-Inputs，合并为一个Input
        features = build_input_features(self.feature_columns)
        inputs_list = list(features.values())
        # TODO: how to generate inputs
        # inputs = concat_fun(inputs_list, axis=1)
        return features, inputs_list, None
    
    def get_encoder_inputs(self):
        # 1. 计算sparse+dense feature embedding
        init_std = 0.01 * (self.hidden_size ** -0.5)
        sparse_embedding_list, dense_value_list = input_from_feature_columns(self.features, self.feature_columns,
                                                                             self.hidden_size,
                                                                             self.l2_reg_embedding,
                                                                             init_std,
                                                                             self.seed)
        dense_embedding_list = []
        for dense_value in dense_value_list:
            dense_embedding = tf.keras.layers.Dense(self.hidden_size, activation=None, use_bias=False,
                                                    kernel_initializer=RandomNormal(mean=0.0, stddev=init_std),
                                                    kernel_regularizer=l2(self.l2_reg_embedding),
                                                    name="JustinDense" + '_emb_' + dense_value.name.split(":")[0])(
                dense_value)
            dense_embedding = tf.reshape(dense_embedding, [-1, 1, self.hidden_size])
            dense_embedding_list.append(dense_embedding)
        
        embedded_inputs = concat_fun(sparse_embedding_list + dense_embedding_list, axis=1)
        # 1.1. do scale, Scale embedding by the sqrt of the hidden size
        # TODO: do mask here if need
        embedded_inputs *= self.hidden_size ** 0.5
        encoder_inputs = embedded_inputs
        
        # 2. + position embedding
        if self.params.get("add_pos_encoding", False):
            with tf.name_scope("add_pos_encoding"):
                length = tf.shape(embedded_inputs)[1]
                pos_encoding = model_utils.get_position_encoding(length, self.hidden_size)
                encoder_inputs += pos_encoding
        
        # 3. dropout
        encoder_inputs = tf.keras.layers.Dropout(self.dropout_embedding)(encoder_inputs)
        
        # 4. normalize
        # encoder_inputs = LayerNormalization(self.hidden_size)(encoder_inputs)
        return encoder_inputs
    
    def get_encoder_outputs(self):
        # TODO: Calculate attention bias for encoder self-attention layers
        # inputs_padding = model_utils.get_padding(self.inputs)
        self.encoder_layers = [EncoderLayer(self.hidden_size, self.num_heads, self.feed_forward_hidden_size,
                                            self.dropout_attention, self.dropout_relu)
                               for _ in range(self.num_hidden_layers)]
        
        mha_outputs, ff_outputs = [], []
        ff_output = self.encoder_inputs
        for i in range(self.num_hidden_layers):
            mha_output, ff_output = self.encoder_layers[i](ff_output)
            mha_outputs.append(mha_output)
            ff_outputs.append(ff_output)
        return ff_output, mha_outputs, ff_outputs
    
    def to_predict(self):
        enconder_outputs = tf.keras.layers.Flatten()(self.encoder_output)
        deep_out = DNN(self.dnn_hidden_units, self.dnn_activation, self.l2_reg_dnn, self.dropout_dnn,
                       self.dnn_use_bn, self.seed)(enconder_outputs)
        final_logit = tf.keras.layers.Dense(1, use_bias=True, activation=None, )(deep_out)
        predict_output = PredictionLayer(self.task, use_bias=False)(final_logit)
        return predict_output


class LayerNormalization(tf.keras.layers.Layer):
    """Applies layer normalization."""
    
    def __init__(self, hidden_size):
        super(LayerNormalization, self).__init__()
        self.hidden_size = hidden_size
    
    def build(self, _):
        # 参数共享，可省一些参数
        # with tf.variable_scope(name_or_scope='lay_normalization', reuse=tf.AUTO_REUSE):
        #     self.scale = tf.get_variable("layer_norm_scale", [self.hidden_size],
        #                              initializer=tf.ones_initializer())
        #     self.bias = tf.get_variable("layer_norm_bias", [self.hidden_size],
        #                             initializer=tf.zeros_initializer())
        # 参数非共享
        self.scale = self.add_weight("layer_norm_scale", [self.hidden_size], initializer=tf.ones_initializer())
        self.bias = self.add_weight("layer_norm_bias", [self.hidden_size], initializer=tf.zeros_initializer())
        self.built = True
    
    def call(self, x, epsilon=1e-6):
        mean = tf.reduce_mean(x, axis=[-1], keepdims=True)
        variance = tf.reduce_mean(tf.square(x - mean), axis=[-1], keepdims=True)
        norm_x = (x - mean) * tf.rsqrt(variance + epsilon)
        return norm_x * self.scale + self.bias


class EncoderLayer(tf.keras.layers.Layer):
    def __init__(self, hidden_size, num_heads, feed_forward_hidden_size, dropout_attention, dropout_relu):
        super(EncoderLayer, self).__init__()
        
        self.mha = SelfAttention(hidden_size, num_heads, dropout_attention)
        self.ffn = FeedFowardNetwork(hidden_size, feed_forward_hidden_size, dropout_relu, )
        
        self.layer_normal_1 = LayerNormalization(hidden_size)
        self.layer_normal_2 = LayerNormalization(hidden_size)
        
        self.dropout1 = tf.keras.layers.Dropout(dropout_attention)
        self.dropout2 = tf.keras.layers.Dropout(dropout_relu)
    
    def call(self, x, training=None):
        # TODO: Create binary mask of size [batch_size, length]
        # mask = tf.to_float(tf.not_equal(x, 0))
        # TODO: add bias
        # attention_bias = model_utils.get_padding_bias(self.inputs)
        
        attn_output = self.mha(x, bias=None)  # (batch_size, input_seq_len, d_model)
        attn_output = self.dropout1(attn_output)
        out1 = self.layer_normal_1(x + attn_output)  # (batch_size, input_seq_len, d_model)
        
        ffn_output = self.ffn(out1)  # (batch_size, input_seq_len, d_model)
        ffn_output = self.dropout2(ffn_output)
        out2 = self.layer_normal_2(out1 + ffn_output)  # (batch_size, input_seq_len, d_model)
        
        return out1, out2
