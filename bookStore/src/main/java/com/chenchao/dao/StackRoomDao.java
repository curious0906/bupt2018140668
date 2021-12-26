package com.chenchao.dao;

import com.chenchao.model.StackRoom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StackRoomDao {
    void insert(StackRoom stackRoom);
}
