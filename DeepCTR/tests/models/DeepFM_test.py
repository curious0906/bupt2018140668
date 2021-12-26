# import pytest
from deepctr.models import DeepFM
from tests.utils import check_model, get_test_data, SAMPLE_SIZE, get_test_data_test


def  test_DeepFM(use_fm, hidden_size, sparse_feature_num):
    model_name = "DeepFM"
    sample_size = SAMPLE_SIZE
    train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label, feature_columns = get_test_data(sample_size, sparse_feature_num, sparse_feature_num)

    model = DeepFM(feature_columns,feature_columns, use_fm=use_fm, dnn_hidden_units=hidden_size, dnn_dropout=0.5)
    check_model(model, model_name, train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label)


if __name__ == "__main__":
    test_DeepFM(use_fm=True, hidden_size=(128, 128),sparse_feature_num=1)
    pass
