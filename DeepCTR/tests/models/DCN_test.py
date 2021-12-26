
from deepctr.models import DCN
from tests.utils import check_model, get_test_data,SAMPLE_SIZE


def test_DCN(embedding_size, cross_num, hidden_size, sparse_feature_num):
    model_name = "DCN"

    sample_size = SAMPLE_SIZE
    train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label, feature_columns = get_test_data(
        sample_size, sparse_feature_num, sparse_feature_num)
    model = DCN(feature_columns, embedding_size=embedding_size, cross_num=cross_num, dnn_hidden_units=hidden_size, dnn_dropout=0.5)
    check_model(model, model_name, train_dense_x, train_sparse_x, train_label, val_dense_x, val_sparse_x, val_label)


# def test_DCN_invalid(embedding_size=8, cross_num=0, hidden_size=()):
#     feature_dim_dict = {'sparse': [SparseFeat('sparse_1', 2), SparseFeat('sparse_2', 5), SparseFeat('sparse_3', 10)],
#                         'dense': [SparseFeat('dense_1', 1), SparseFeat('dense_1', 1), SparseFeat('dense_1', 1)]}
#     with pytest.raises(ValueError):
#         _ = DCN(None, embedding_size=embedding_size, cross_num=cross_num, dnn_hidden_units=hidden_size, dnn_dropout=0.5)


if __name__ == "__main__":
    test_DCN(embedding_size=1, cross_num=2, hidden_size=(128, 128,), sparse_feature_num=1)
    pass
