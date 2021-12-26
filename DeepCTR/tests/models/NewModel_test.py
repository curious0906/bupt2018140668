# import pytest
from deepctr.models import DeepDNN, DeepDNNV1
from tests.utils import check_model, get_test_data, SAMPLE_SIZE, get_test_data_test
import tensorflow as tf
from tensorflow.keras.utils import plot_model

def  test_DeepFM(att_layer_num,use_fm, hidden_size, sparse_feature_num):
    if tf.__version__ >= "2.0.0":
        return
    model_name = "newModel"

    sample_size = SAMPLE_SIZE
    train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label, feature_columns = get_test_data(sample_size, sparse_feature_num, sparse_feature_num)

    model = DeepDNNV1(feature_columns,feature_columns, att_layer_num=3, att_embedding_size=8, att_head_num=2, att_res=True,
            dnn_hidden_units=(256, 256), dnn_activation='relu', dnn_dropout=0.5)
    plot_model(model, to_file='model.png')
    check_model(model, model_name, train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label)


if __name__ == "__main__":
    test_DeepFM(att_layer_num=3,use_fm=True, hidden_size=(128, 128),sparse_feature_num=1)
    pass
