package org.sysu.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReaderCategory {
    private Long id;

    private String name;

    private Integer maxBookBorrow;

    private Integer bookBorrowAgain;

    private Integer bookBorrowTime;

    private Date gmtCreate;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getMaxBookBorrow() {
        return maxBookBorrow;
    }

    public void setMaxBookBorrow(Integer maxBookBorrow) {
        this.maxBookBorrow = maxBookBorrow;
    }

    public Integer getBookBorrowAgain() {
        return bookBorrowAgain;
    }

    public void setBookBorrowAgain(Integer bookBorrowAgain) {
        this.bookBorrowAgain = bookBorrowAgain;
    }

    public Integer getBookBorrowTime() {
        return bookBorrowTime;
    }

    public void setBookBorrowTime(Integer bookBorrowTime) {
        this.bookBorrowTime = bookBorrowTime;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}