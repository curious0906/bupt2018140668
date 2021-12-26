package com.chenchao.model;
import java.util.Date;

public class BookType {
    private Integer bookTypeId;
    private Integer bookTypeParentId;
    private String bookTypeName;
    private Integer bookTypeStatus;
    private Integer bookTypeOrder;
    private Boolean bookTypeIsParent;
    private Date bookTypeCreated;
    private Date bookTypeUpdated;

    public void setBookTypeId(Integer bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public void setBookTypeParentId(Integer bookTypeParentId) {
        this.bookTypeParentId = bookTypeParentId;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public void setBookTypeStatus(Integer bookTypeStatus) {
        this.bookTypeStatus = bookTypeStatus;
    }

    public void setBookTypeOrder(Integer bookTypeOrder) {
        this.bookTypeOrder = bookTypeOrder;
    }

    public void setBookTypeIsParent(Boolean bookTypeIsParent) {
        this.bookTypeIsParent = bookTypeIsParent;
    }

    public void setBookTypeCreated(Date bookTypeCreated) {
        this.bookTypeCreated = bookTypeCreated;
    }

    public void setBookTypeUpdated(Date bookTypeUpdated) {
        this.bookTypeUpdated = bookTypeUpdated;
    }

    public Integer getBookTypeId() {
        return bookTypeId;
    }

    public Integer getBookTypeParentId() {
        return bookTypeParentId;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public Integer getBookTypeStatus() {
        return bookTypeStatus;
    }

    public Integer getBookTypeOrder() {
        return bookTypeOrder;
    }

    public Boolean getBookTypeIsParent() {
        return bookTypeIsParent;
    }

    public Date getBookTypeCreated() {
        return bookTypeCreated;
    }

    public Date getBookTypeUpdated() {
        return bookTypeUpdated;
    }




}

