package com.chenchao.service;
import com.chenchao.model.BookType;
import java.util.List;

public interface IBookTypeService {
    public List<BookType> getBookList();
    public BookType getBookType(int bookTypeId);
}
