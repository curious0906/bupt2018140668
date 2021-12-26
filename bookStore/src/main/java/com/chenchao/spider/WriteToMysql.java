package com.chenchao.spider;

import com.chenchao.model.StackRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chenchao.dao.StackRoomDao;


import java.sql.SQLException;
import java.util.List;

@Component
public class WriteToMysql {

    @Autowired
    private StackRoomDao stackRoomDao;

    public void executeInsert(List<StackRoom> bookdatas) throws SQLException
    {
        long start = System.currentTimeMillis() / 1000;
        System.out.println(start);
        for (StackRoom bookdata : bookdatas) {
            stackRoomDao.insert(bookdata);
        }

        System.out.println("成功插入" + bookdatas.size() + "条");
        System.out.println(System.currentTimeMillis() / 1000);
        System.out.println(System.currentTimeMillis() / 1000 - start);
    }

}
