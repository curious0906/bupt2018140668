# -*- coding:utf-8 -*-
"""

Author:
    Weichen Shen,wcshen1994@163.com

Reference:
    [1] Song W, Shi C, Xiao Z, et al. AutoInt: Automatic Feature Interaction Learning via Self-Attentive Neural Networks[J]. arXiv preprint arXiv:1810.11921, 2018.(https://arxiv.org/abs/1810.11921)

"""
import tensorflow as tf

from ..inputs import input_from_feature_columns,build_input_features,combined_dnn_input
from ..layers.core import PredictionLayer, DNN
from ..layers.interaction import InteractingLayer, AutoIntInteractingLayer
from ..layers.utils import concat_fun
from tensorflow.python.keras.initializers import RandomNormal
from tensorflow.python.keras.regularizers import l2

def AutoIntAttention(dnn_feature_columns, embedding_size=8, att_layer_num=3, att_embedding_size=8, att_head_num=2, att_res=True,
            dnn_hidden_units=(256, 256), dnn_activation='relu',
            l2_reg_dnn=0, l2_reg_embedding=1e-5, dnn_use_bn=False, dnn_dropout=0, init_std=0.01, seed=1024,
            task='binary', ):
    """Instantiates the AutoInt Network architecture.

    :param dnn_feature_columns: An iterable containing all the features used by deep part of the model.
    :param embedding_size: positive integer,sparse feature embedding_size
    :param att_layer_num: int.The InteractingLayer number to be used.
    :param att_embedding_size: int.The embedding size in multi-head self-attention network.
    :param att_head_num: int.The head number in multi-head  self-attention network.
    :param att_res: bool.Whether or not use standard residual connections before output.
    :param dnn_hidden_units: list,list of positive integer or empty list, the layer number and units in each layer of DNN
    :param dnn_activation: Activation function to use in DNN
    :param l2_reg_dnn: float. L2 regularizer strength applied to DNN
    :param l2_reg_embedding: float. L2 regularizer strength applied to embedding vector
    :param dnn_use_bn:  bool. Whether use BatchNormalization before activation or not in DNN
    :param dnn_dropout: float in [0,1), the probability we will drop out a given DNN coordinate.
    :param init_std: float,to use as the initialize std of embedding vector
    :param seed: integer ,to use as random seed.
    :param task: str, ``"binary"`` for  binary logloss or  ``"regression"`` for regression loss
    :return: A Keras model instance.
    """

    tf.enable_resource_variables()
    if len(dnn_hidden_units) <= 0 and att_layer_num <= 0:
        raise ValueError("Either hidden_layer or att_layer_num must > 0")

    features = build_input_features(dnn_feature_columns)
    inputs_list = list(features.values())

    sparse_embedding_list, dense_value_list = input_from_feature_columns(features,dnn_feature_columns,embedding_size,
                                                                                               l2_reg_embedding,
                                                                                                init_std,
                                                                                               seed)
    dense_embedding_list = []
    for dense_value in dense_value_list:
        dense_embedding = tf.keras.layers.Dense(embedding_size, activation=None, use_bias=False,
                                                kernel_initializer=RandomNormal(mean=0.0, stddev=init_std),
                                                kernel_regularizer=l2(l2_reg_embedding),
                                                name="JustinDense_" + '_emb_' + dense_value.name.split(":")[0])(dense_value)
        dense_embedding = tf.reshape(dense_embedding, [-1, 1, embedding_size])
        dense_embedding_list.append(dense_embedding)

    att_input = concat_fun(sparse_embedding_list + dense_embedding_list, axis=1)

    reattention_input = []
    for _ in range(att_layer_num):
        att_input = AutoIntInteractingLayer(
            att_embedding_size, att_head_num, att_res)(att_input)
        reattention_input.append(att_input)
    # 增加average
    # reattention_ouput = tf.keras.layers.average(reattention_input)
    # att_output = tf.keras.layers.Flatten()(reattention_ouput)
    # 不用average
    att_output = tf.keras.layers.Flatten()(att_input)

    final_logit = tf.keras.layers.Dense(1, use_bias=True, activation=None,
                                        kernel_initializer=tf.glorot_normal_initializer(),
                                        bias_initializer=tf.glorot_normal_initializer(),)(att_output)
    
    if len(dnn_hidden_units) > 0:
        dnn_input = combined_dnn_input(sparse_embedding_list, dense_embedding_list)
        deep_out = DNN(dnn_hidden_units, dnn_activation, l2_reg_dnn, dnn_dropout,
                       dnn_use_bn, seed)(dnn_input)
        dnn_logit = tf.keras.layers.Dense(
            1, use_bias=True, activation=None)(deep_out)
        final_logit += dnn_logit

    output = PredictionLayer(task, use_bias=False)(final_logit)

    model = tf.keras.models.Model(inputs=inputs_list, outputs=output)

    # 不需要此语句
    # tf.keras.backend.get_session().run(tf.global_variables_initializer())
    
    return model
