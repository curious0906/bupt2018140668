package com.chenchao.model;

import java.util.Date;

public class BookChara {
    private long bookId;
    private String bookChara;
    private Date bookCharaCreated;
    private Date bookCharaUpdated;

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setBookChara(String bookChara) {
        this.bookChara = bookChara;
    }

    public void setBookCharaCreated(Date bookCharaCreated) {
        this.bookCharaCreated = bookCharaCreated;
    }

    public void setBookCharaUpdated(Date bookCharaUpdated) {
        this.bookCharaUpdated = bookCharaUpdated;
    }
    public long getBookId() {
        return bookId;
    }

    public String getBookChara() {
        return bookChara;
    }

    public Date getBookCharaCreated() {
        return bookCharaCreated;
    }

    public Date getBookCharaUpdated() {
        return bookCharaUpdated;
    }


}
