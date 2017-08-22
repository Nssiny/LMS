package org.sysu.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReaderLicense {
    private Long id;

    private String account;

    private Long categoryId;

    private Date gmtAllocate;

    private Date gmtExpire;

    private Integer penaltyCount;

    private Integer borrowed;

    private Byte status;

    private Date gmtCreate;

    private Date gmtModified;
    
    private ReaderCategory readerCategory;
    
    public ReaderCategory getReaderCategory() {
    	return readerCategory;
    }
    
    public void setReaderCategory(ReaderCategory readerCategory) {
    	this.readerCategory = readerCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getGmtAllocate() {
        return gmtAllocate;
    }

    public void setGmtAllocate(Date gmtAllocate) {
        this.gmtAllocate = gmtAllocate;
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getGmtExpire() {
        return gmtExpire;
    }

    public void setGmtExpire(Date gmtExpire) {
        this.gmtExpire = gmtExpire;
    }

    public Integer getPenaltyCount() {
        return penaltyCount;
    }

    public void setPenaltyCount(Integer penaltyCount) {
        this.penaltyCount = penaltyCount;
    }

    public Integer getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Integer borrowed) {
        this.borrowed = borrowed;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}