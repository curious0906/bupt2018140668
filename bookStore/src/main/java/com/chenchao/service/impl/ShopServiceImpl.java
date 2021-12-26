package com.chenchao.service.impl;

import com.chenchao.model.Shop;
import com.chenchao.model.Users;
import com.chenchao.service.IShopService;
import com.chenchao.dao.ShopDao;
import com.chenchao.utils.BSResultUtil;
import com.chenchao.pojo.BSResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import com.chenchao.dao.UserMapper;

@Service
public class ShopServiceImpl implements IShopService{
    @Autowired
    private ShopDao shopDao;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Shop findStoreByUserId(Integer userId) {

        List<Shop> stores = shopDao.selectByUserId(userId);
        if(stores !=null && stores.size() > 0){
            return stores.get(0);
        }
        return null;
    }

    @Override
    public List<Shop> findStores() {
        return shopDao.selectAll();
    }

    @Override
    public Shop findById(int storeId) {
        Shop store = shopDao.selectShopByPrimaryKey(storeId);
        Users user = userMapper.selectByPrimaryKey(store.getShopManagerId());
//        store.setStoreManagerName(user.getUsername());
        return store;
    }

    @Override
    public BSResult updateStore(Shop store) {
        store.setShopCreated(new Date());
        shopDao.updateByPrimaryKeySelective(store);
        return BSResultUtil.success();
    }

    @Override
    public void deleteStore(int storeId) {

    }

    @Override
    public void addStore(Shop store) {

    }
}
