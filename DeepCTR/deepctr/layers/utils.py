# -*- coding:utf-8 -*-

import tensorflow as tf


class NoMask(tf.keras.layers.Layer):
    def __init__(self, **kwargs):
        super(NoMask, self).__init__(**kwargs)

    def build(self, input_shape):
        # Be sure to call this somewhere!
        super(NoMask, self).build(input_shape)

    def call(self, x, mask=None, **kwargs):
        return x

    def compute_mask(self, inputs, mask):
        return None


class Hash(tf.keras.layers.Layer):
    """
    hash the input to [0,num_buckets)
    if mask_zero = True,0 or 0.0 will be set to 0,other value will be set in range[1,num_buckets)
    """

    def __init__(self, num_buckets, mask_zero=False, **kwargs):
        self.num_buckets = num_buckets
        self.mask_zero = mask_zero
        super(Hash, self).__init__(**kwargs)

    def build(self, input_shape):
        # Be sure to call this somewhere!
        super(Hash, self).build(input_shape)

    def call(self, x, mask=None, **kwargs):
        if x.dtype != tf.string:
            x = tf.as_string(x, )
        try:
            hash_x = tf.string_to_hash_bucket_fast(x, self.num_buckets if not self.mask_zero else self.num_buckets - 1,
                                                    name=None)  # weak hash
        except:
            hash_x = tf.strings.to_hash_bucket_fast(x, self.num_buckets if not self.mask_zero else self.num_buckets - 1,
                                               name=None)  # weak hash
        if self.mask_zero:
            mask_1 = tf.cast(tf.not_equal(x, "0"), 'int64')
            mask_2 = tf.cast(tf.not_equal(x, "0.0"), 'int64')
            mask = mask_1 * mask_2
            hash_x = (hash_x + 1) * mask
        return hash_x

    def compute_mask(self, inputs, mask):
        return None

    def get_config(self, ):
        config = {'num_buckets': self.num_buckets, 'mask_zero': self.mask_zero}
        base_config = super(Hash, self).get_config()
        return dict(list(base_config.items()) + list(config.items()))


class Linear(tf.keras.layers.Layer):

    def __init__(self, l2_reg=0.0, mode=0, **kwargs):

        self.l2_reg = l2_reg
        # self.l2_reg = tf.contrib.layers.l2_regularizer(float(l2_reg_linear))
        self.mode = mode
        super(Linear, self).__init__(**kwargs)

    def build(self, input_shape):

        self.bias = self.add_weight(name='linear_bias',
                                    shape=(1,),
                                    initializer=tf.keras.initializers.Zeros(),
                                    trainable=True)

        self.dense = tf.keras.layers.Dense(units=1, activation=None, use_bias=False,
                                           kernel_regularizer=tf.keras.regularizers.l2(self.l2_reg))

        super(Linear, self).build(input_shape)  # Be sure to call this somewhere!

    def call(self, inputs , **kwargs):

        if self.mode == 0:
            sparse_input = inputs
            linear_logit = reduce_sum(sparse_input, axis=-1, keep_dims=True)
        elif self.mode == 1:
            dense_input = inputs
            linear_logit = self.dense(dense_input)

        else:
            sparse_input, dense_input = inputs

            linear_logit = reduce_sum(sparse_input, axis=-1, keep_dims=False) + self.dense(dense_input)

        linear_bias_logit = linear_logit + self.bias

        return linear_bias_logit

    def compute_output_shape(self, input_shape):
        return (None, 1)

    def get_config(self, ):
        config = {'mode': self.mode, 'l2_reg': self.l2_reg}
        base_config = super(Linear, self).get_config()
        return dict(list(base_config.items()) + list(config.items()))


def concat_fun(inputs, axis=-1):
    if len(inputs) == 1:
        return inputs[0]
    else:
        return tf.keras.layers.Concatenate(axis=axis)(inputs)


def reduce_mean(input_tensor,
               axis=None,
               keep_dims=False,
               name=None,
               reduction_indices=None):
    if tf.__version__ < '2.0.0':
        return tf.reduce_mean(input_tensor,
                   axis=axis,
                   keep_dims=keep_dims,
                   name=name,
                   reduction_indices=reduction_indices)
    else:
        return  tf.reduce_mean(input_tensor,
                   axis=axis,
                   keepdims=keep_dims,
                   name=name)


def reduce_sum(input_tensor,
               axis=None,
               keep_dims=False,
               name=None,
               reduction_indices=None):
    if tf.__version__ < '2.0.0':
        return tf.reduce_sum(input_tensor,
                   axis=axis,
                   keep_dims=keep_dims,
                   name=name,
                   reduction_indices=reduction_indices)
    else:
        return  tf.reduce_sum(input_tensor,
                   axis=axis,
                   keepdims=keep_dims,
                   name=name)

def reduce_max(input_tensor,
               axis=None,
               keep_dims=False,
               name=None,
               reduction_indices=None):
    if tf.__version__ < '2.0.0':
        return tf.reduce_max(input_tensor,
                   axis=axis,
                   keep_dims=keep_dims,
                   name=name,
                   reduction_indices=reduction_indices)
    else:
        return  tf.reduce_max(input_tensor,
                   axis=axis,
                   keepdims=keep_dims,
                   name=name)

def div(x, y, name=None):
    if tf.__version__ < '2.0.0':
        return tf.div(x, y, name=name)
    else:
        return tf.divide(x, y, name=name)

def softmax(logits, dim=-1, name=None):
    if tf.__version__ < '2.0.0':
        return tf.nn.softmax(logits, dim=dim, name=name)
    else:
        return tf.nn.softmax(logits, axis=dim, name=name)

def normalize(inputs, epsilon=1e-8):
    '''
    Applies layer normalization
    Args:
        inputs: A tensor with 2 or more dimensions
        epsilon: A floating number to prevent Zero Division
    Returns:
        A tensor with the same shape and data dtype
    '''
    inputs_shape = inputs.get_shape()
    params_shape = inputs_shape[-1:]

    mean, variance = tf.nn.moments(inputs, [-1], keep_dims=True)
    beta = tf.Variable(tf.zeros(params_shape), name="beta")
    gamma = tf.Variable(tf.ones(params_shape), name="gamma")
    normalized = (inputs - mean) / ((variance + epsilon) ** (.5))
    outputs = gamma * normalized + beta

    return outputs
