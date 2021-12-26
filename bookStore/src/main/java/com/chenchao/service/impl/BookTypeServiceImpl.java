package com.chenchao.service.impl;
import com.chenchao.dao.BookTypeDao;
import com.chenchao.service.IBookTypeService;
import com.chenchao.model.BookType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookTypeServiceImpl implements IBookTypeService{
    @Autowired
    private BookTypeDao bookTypeDao;
    /**
     * 查询图书信息数据
     * @param
     * @param
     * @return
     */


    @Override
    public List<BookType> getBookList() {

        List<BookType> bookList = bookTypeDao.findAllBookType();
        System.out.println(bookList.size());

        return bookList;
    }

    @Override
    public BookType getBookType(int bookTypeId) {
        BookType bookType = bookTypeDao.getbookType(bookTypeId);
        return bookType;
    }
}
