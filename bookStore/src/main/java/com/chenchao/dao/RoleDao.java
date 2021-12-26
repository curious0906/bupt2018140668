package com.chenchao.dao;

import com.chenchao.model.CharacterAlter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {
    List<CharacterAlter> selectAll();
}
