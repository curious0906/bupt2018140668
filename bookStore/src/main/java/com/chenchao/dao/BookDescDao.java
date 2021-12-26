package com.chenchao.dao;

import com.chenchao.model.BookChara;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookDescDao {

    BookChara selectByPrimaryKey(long bookId);
}
