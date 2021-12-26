# -*- coding:utf-8 -*-
import tensorflow as tf

from ..inputs import input_from_feature_columns, get_linear_logit,build_input_features,combined_dnn_input
from ..layers.core import PredictionLayer, DNN
from ..layers.interaction import FM
from ..layers.interaction import CIN
from ..layers.interaction import InteractingLayer
from ..layers.utils import concat_fun
from tensorflow.python.keras.initializers import RandomNormal
from tensorflow.python.keras.regularizers import l2

def DeepDNN(linear_feature_columns, dnn_feature_columns, att_layer_num=3, att_embedding_size=8, att_head_num=2, att_res=True,
            dnn_hidden_units=(256, 256),cin_layer_size=(128, 128,), cin_split_half=True, cin_activation='relu',embedding_size=8,
        l2_reg_embedding=1e-5, l2_reg_linear=1e-5, l2_reg_dnn=0,l2_reg_cin=0, init_std=0.0001, seed=1024, dnn_dropout=0,
        dnn_activation='relu', task='binary'):
    """Instantiates the Factorization-supported Neural Network architecture.

    :param linear_feature_columns: An iterable containing all the features used by linear part of the model.
    :param dnn_feature_columns: An iterable containing all the features used by deep part of the model.
    :param embedding_size: positive integer,sparse feature embedding_size
    :param dnn_hidden_units: list,list of positive integer or empty list, the layer number and units in each layer of deep net
    :param l2_reg_embedding: float. L2 regularizer strength applied to embedding vector
    :param l2_reg_linear: float. L2 regularizer strength applied to linear weight
    :param l2_reg_dnn: float . L2 regularizer strength applied to DNN
    :param init_std: float,to use as the initialize std of embedding vector
    :param seed: integer ,to use as random seed.
    :param dnn_dropout: float in [0,1), the probability we will drop out a given DNN coordinate.
    :param dnn_activation: Activation function to use in DNN
    :param task: str, ``"binary"`` for  binary logloss or  ``"regression"`` for regression loss
    :return: A Keras model instance.
    """
    features = build_input_features(linear_feature_columns + dnn_feature_columns)

    inputs_list = list(features.values())

    sparse_embedding_list, dense_value_list = input_from_feature_columns(features,dnn_feature_columns,
                                                                              embedding_size,
                                                                              l2_reg_embedding,init_std,
                                                                              seed)

    linear_logit = get_linear_logit(features, linear_feature_columns, l2_reg=l2_reg_linear, init_std=init_std,
                                    seed=seed, prefix='linear')
    fm_input = concat_fun(sparse_embedding_list, axis=1)
    fm_logit = FM()(fm_input)
    dense_embedding_list = []
    for dense_value in dense_value_list:
        dense_embedding = tf.keras.layers.Dense(embedding_size, use_bias=False, kernel_initializer=RandomNormal(
            mean=0.0, stddev=init_std, seed=seed),
                                                kernel_regularizer=l2(l2_reg_embedding),
                                                name="JustinDense_" + '_emb_' + dense_value.name.split(":")[0])(
            dense_value)
        dense_embedding = tf.reshape(dense_embedding, [-1, 1, embedding_size])
        dense_embedding_list.append(dense_embedding)

    att_input = concat_fun(sparse_embedding_list + dense_embedding_list, axis=1)

    fm_input = concat_fun(sparse_embedding_list, axis=1)

    if len(cin_layer_size) > 0:
        exFM_out = CIN(cin_layer_size, cin_activation,
                       cin_split_half, l2_reg_cin, seed)(fm_input)
        exFM_logit = tf.keras.layers.Dense(1, activation=None, )(exFM_out)

    for _ in range(att_layer_num):
        att_input = InteractingLayer(
            att_embedding_size, att_head_num, att_res)(att_input)
    att_output = tf.keras.layers.Flatten()(att_input)
    dnn_input = combined_dnn_input(sparse_embedding_list,dense_value_list)
    deep_out = DNN(dnn_hidden_units, dnn_activation, l2_reg_dnn,
                   dnn_dropout, False, seed)(dnn_input)
    deep_logit = tf.keras.layers.Dense(
        1, use_bias=False, activation=None)(deep_out)
    final_logit = tf.keras.layers.add([deep_logit, linear_logit])
    att_logit = tf.keras.layers.Dense(
        1, use_bias=False, activation=None)(att_output)
    final1_logit = tf.keras.layers.add([att_logit, final_logit])
    final2_logit = tf.keras.layers.add([fm_logit, final1_logit])
    output = PredictionLayer(task)(final2_logit)

    model = tf.keras.models.Model(inputs=inputs_list,
                                  outputs=output)
    return model

def DeepDNNV1(linear_feature_columns, dnn_feature_columns, att_layer_num=3, att_embedding_size=8, att_head_num=2, att_res=True,
            dnn_hidden_units=(256, 256),cin_layer_size=(128, 128,), cin_split_half=True, cin_activation='relu',embedding_size=8,
        l2_reg_embedding=1e-5, l2_reg_linear=1e-5, l2_reg_dnn=0,l2_reg_cin=0, init_std=0.0001, seed=1024, dnn_dropout=0,
        dnn_activation='relu', task='binary'):
    """Instantiates the Factorization-supported Neural Network architecture.

    :param linear_feature_columns: An iterable containing all the features used by linear part of the model.
    :param dnn_feature_columns: An iterable containing all the features used by deep part of the model.
    :param embedding_size: positive integer,sparse feature embedding_size
    :param dnn_hidden_units: list,list of positive integer or empty list, the layer number and units in each layer of deep net
    :param l2_reg_embedding: float. L2 regularizer strength applied to embedding vector
    :param l2_reg_linear: float. L2 regularizer strength applied to linear weight
    :param l2_reg_dnn: float . L2 regularizer strength applied to DNN
    :param init_std: float,to use as the initialize std of embedding vector
    :param seed: integer ,to use as random seed.
    :param dnn_dropout: float in [0,1), the probability we will drop out a given DNN coordinate.
    :param dnn_activation: Activation function to use in DNN
    :param task: str, ``"binary"`` for  binary logloss or  ``"regression"`` for regression loss
    :return: A Keras model instance.
    """
    features = build_input_features(linear_feature_columns + dnn_feature_columns)

    inputs_list = list(features.values())

    sparse_embedding_list, dense_value_list = input_from_feature_columns(features,dnn_feature_columns,
                                                                              embedding_size,
                                                                              l2_reg_embedding,init_std,
                                                                              seed)

    linear_logit = get_linear_logit(features, linear_feature_columns, l2_reg=l2_reg_linear, init_std=init_std,
                                    seed=seed, prefix='linear')
    fm_input = concat_fun(sparse_embedding_list, axis=1)
    fm_logit = FM()(fm_input)
    dense_embedding_list = []
    for dense_value in dense_value_list:
        dense_embedding = tf.keras.layers.Dense(embedding_size, use_bias=False, kernel_initializer=RandomNormal(
            mean=0.0, stddev=init_std, seed=seed),
                                                kernel_regularizer=l2(l2_reg_embedding),
                                                name="JustinDense_" + '_emb_' + dense_value.name.split(":")[0])(
            dense_value)
        dense_embedding = tf.reshape(dense_embedding, [-1, 1, embedding_size])
        dense_embedding_list.append(dense_embedding)

    att_input = concat_fun(sparse_embedding_list + dense_embedding_list, axis=1)
    for _ in range(att_layer_num):
        att_input = InteractingLayer(
            att_embedding_size, att_head_num, att_res)(att_input)
    att_output = tf.keras.layers.Flatten()(att_input)
    dnn_input = combined_dnn_input(sparse_embedding_list,dense_value_list)
    deep_out = DNN(dnn_hidden_units, dnn_activation, l2_reg_dnn,
                   dnn_dropout, False, seed)(dnn_input)
    deep_logit = tf.keras.layers.Dense(
        1, use_bias=False, activation=None)(deep_out)
    att_logit = tf.keras.layers.Dense(
        1, use_bias=False, activation=None)(att_output)
    vote_embedding_list = []
    vote_embedding_list.append(deep_logit)
    vote_embedding_list.append(linear_logit)
    vote_embedding_list.append(att_logit)
    vote_embedding_list.append(fm_logit)
    vate_embedding_list = []
    for vote_value in vote_embedding_list:
        vote_embedding = tf.keras.layers.Dense(embedding_size, use_bias=False, kernel_initializer=RandomNormal(
            mean=0.0, stddev=init_std, seed=seed),
                                                kernel_regularizer=l2(l2_reg_embedding),
                                                name="JustinDense_" + '_emb_' + vote_value.name.split(":")[0])(
            vote_value)
        vote_embedding = tf.reshape(vote_embedding, [-1, 1, embedding_size])
        vate_embedding_list.append(vote_embedding)

    vote_att_input = concat_fun(vate_embedding_list, axis=1)
    for _ in range(att_layer_num):
        vote_att_input = InteractingLayer(
            att_embedding_size, att_head_num, att_res)(vote_att_input)
    vote_att_output = tf.keras.layers.Flatten()(vote_att_input)
    final_logit = tf.keras.layers.Dense(
        1, use_bias=False, activation=None)(vote_att_output)
    output = PredictionLayer(task)(final_logit)

    model = tf.keras.models.Model(inputs=inputs_list,
                                  outputs=output)
    return model
