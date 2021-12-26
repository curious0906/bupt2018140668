package com.chenchao.service;
import com.chenchao.model.BookDetails;
import com.github.pagehelper.PageInfo;

import java.util.List;
import com.chenchao.pojo.BSResult;
public interface IBookDetailService {
    public List<BookDetails> findIndexBookList(int TypeId, int currentPage, int pageSize);
    public PageInfo<BookDetails> findBookListByKeywordsOrType(String keywords, int typeId, int page, int pageSize, int storeId);
    public List<BookDetails> findBookInfoHomePageNavigation(int typeId, int page, int pageSize);
    BSResult saveBook(BookDetails bookInfo,String bookDescStr);
    BookDetails queryBookAvailable(int bookId);
    BookDetails adminFindById(int bookId);

    BookDetails findById(long bookId);

    void updateBook(BookDetails bookInfo, String bookDesc);

    void deleteBook(int bookId);

    void changeShelfStatus(int bookId, int isShelf);
}
