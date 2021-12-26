
from deepctr.models import PNN
from tests.utils import check_model, get_test_data, SAMPLE_SIZE, get_test_data_test


def test_PNN(use_inner, use_outter, sparse_feature_num):
    model_name = "PNN"
    sample_size = SAMPLE_SIZE
    train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label, feature_columns = get_test_data(
        sample_size, sparse_feature_num, sparse_feature_num)

    model = PNN(feature_columns, embedding_size=4, dnn_hidden_units=[4, 4], dnn_dropout=0.5, use_inner=use_inner,
                use_outter=use_outter)
    check_model(model, model_name, train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label)


if __name__ == "__main__":
    test_PNN(use_inner=True, use_outter=False, sparse_feature_num=1)
    pass
