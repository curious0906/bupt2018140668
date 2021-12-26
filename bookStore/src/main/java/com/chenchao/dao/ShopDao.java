package com.chenchao.dao;

import com.chenchao.model.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShopDao {
    List<Shop> selectByUserId(Integer userId);

    void updateByPrimaryKeySelective(Shop store);

    List<Shop> selectAll();

    Shop selectShopByPrimaryKey(int storeId);
}
