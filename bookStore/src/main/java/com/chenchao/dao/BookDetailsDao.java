package com.chenchao.dao;
import com.chenchao.model.BookDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface BookDetailsDao {
    List<BookDetails> findBookListByTypeId(int bookTypeId, int startingPosition, int endingPosition);
    List<BookDetails> findBookInfoByKeywords(String s,int page, int pageSize);
    List<BookDetails> findBookInfoByTypeId(int typeId,int page, int pageSize);
    List<BookDetails> findBookInfoInHomePageNavigation(int typeId,int pages,int pageSize);
    List<BookDetails> findBookInfoByIsShelf(int page, int pageSize);
    List<BookDetails> findBookInfoByStoreId(int shopId);
    List<BookDetails> findBookInfo(int page, int pageSize);
    BookDetails queryBookAvailable(int bookId);
}
