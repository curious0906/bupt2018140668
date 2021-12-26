package com.chenchao.dao;

import com.chenchao.model.Shop;
import com.chenchao.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository

public interface UserMapper {

    @Select("SELECT * FROM users")
    List<Users> findAll();
    Integer findCharacterIdByUserId(String username);
    List<Users> selectByNameActive(String name,String active);
    Users selectByPrimaryKey(int storeManagerId);

    List<Users> selectAll();

}

