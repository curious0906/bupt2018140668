package com.chenchao.dao;

import com.chenchao.model.BookDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface BookInfoMapper {
    void updateByPrimaryKey(BookDetails bookInfo);
    BookDetails selectByPrimaryKey(long bookId);
}
