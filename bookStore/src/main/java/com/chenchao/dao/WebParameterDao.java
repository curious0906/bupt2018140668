package com.chenchao.dao;

import com.chenchao.model.WebParameter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WebParameterDao {
    WebParameter selectWebParameter();
}
