package com.chenchao.service;
import com.chenchao.model.Shop;
import com.chenchao.pojo.BSResult;

import java.util.List;

public interface IShopService {
    Shop findStoreByUserId(Integer userId);

    List<Shop> findStores();

    Shop findById(int storeId);

    BSResult updateStore(Shop store);

    void deleteStore(int storeId);

    void addStore(Shop store);
}
