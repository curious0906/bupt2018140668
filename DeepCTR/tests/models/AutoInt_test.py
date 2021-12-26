from packaging import version
import tensorflow as tf
from deepctr.models import AutoInt
from tests.utils import check_model, get_test_data,SAMPLE_SIZE


def test_AutoInt(att_layer_num, dnn_hidden_units, sparse_feature_num):
    if version.parse(tf.__version__) >= version.parse("1.14.0") and len(dnn_hidden_units)==0:#todo check version
        return
    model_name = "AutoInt"
    sample_size = SAMPLE_SIZE
    train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label, feature_columns = get_test_data(sample_size, sparse_feature_num, sparse_feature_num)

    model = AutoInt(feature_columns, att_layer_num=att_layer_num,
                    dnn_hidden_units=dnn_hidden_units, dnn_dropout=0.5, )
    check_model(model, model_name, train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label)


if __name__ == "__main__":
    test_AutoInt(att_layer_num=3, dnn_hidden_units=(256, 256), sparse_feature_num=1)
    pass
