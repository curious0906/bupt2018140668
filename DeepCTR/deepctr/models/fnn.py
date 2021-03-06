# -*- coding:utf-8 -*-
import tensorflow as tf

from ..inputs import input_from_feature_columns, get_linear_logit,build_input_features,combined_dnn_input
from ..layers.core import PredictionLayer, DNN



def FNN(linear_feature_columns, dnn_feature_columns, embedding_size=8, dnn_hidden_units=(128, 128),
        l2_reg_embedding=1e-5, l2_reg_linear=1e-5, l2_reg_dnn=0, init_std=0.0001, seed=1024, dnn_dropout=0,
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

    dnn_input = combined_dnn_input(sparse_embedding_list,dense_value_list)
    deep_out = DNN(dnn_hidden_units, dnn_activation, l2_reg_dnn,
                   dnn_dropout, False, seed)(dnn_input)
    deep_logit = tf.keras.layers.Dense(
        1, use_bias=False, activation=None)(deep_out)
    final_logit = tf.keras.layers.add([deep_logit, linear_logit])
    output = PredictionLayer(task)(final_logit)

    model = tf.keras.models.Model(inputs=inputs_list,
                                  outputs=output)
    return model
