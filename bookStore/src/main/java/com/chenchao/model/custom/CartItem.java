package com.chenchao.model.custom;

import com.chenchao.model.BookDetails;

/**
 * 购物项
 */
public class CartItem {


    private BookDetails bookDetails;

    private double subTotal;

    private int buyNum;

    private boolean checked = true;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public BookDetails getBookInfo() {
        return bookDetails;
    }

    public void setBookInfo(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }
}
