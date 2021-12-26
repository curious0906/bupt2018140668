package com.chenchao.dao;

import com.chenchao.model.BookType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository
public interface BookTypeDao {
    List<BookType> findAllBookType();

    BookType getbookType(int bookTypeId);
}
