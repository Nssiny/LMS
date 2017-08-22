package org.sysu.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReaderBorrow {
    private Long id;

    private String barCode;

    private String account;

    private Date dateBorrow;

    private Date dateReturn;

    private Date dateBack;

    private Integer penaltyFlag;

    private Integer reborrowCount;

    private Date gmtCreate;

    private Date gmtModified;
    
    private InfoBook infoBook;
    
    private InfoCollection infoCollection;
    
    public InfoBook getInfoBook() {
    	return infoBook;
    }
    
    public void setInfoBook(InfoBook infoBook) {	
    	this.infoBook = infoBook;
    }
    
    public InfoCollection getInfoCollection() {
    	return infoCollection;
    }
    
    public void setInfoCollection(InfoCollection infoCollection) {
    	this.infoCollection = infoCollection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getDateBorrow() {
        return dateBorrow;
    }

    public void setDateBorrow(Date dateBorrow) {
        this.dateBorrow = dateBorrow;
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getDateBack() {
        return dateBack;
    }

    public void setDateBack(Date dateBack) {
        this.dateBack = dateBack;
    }

    public Integer getPenaltyFlag() {
        return penaltyFlag;
    }

    public void setPenaltyFlag(Integer penaltyFlag) {
        this.penaltyFlag = penaltyFlag;
    }

    public Integer getReborrowCount() {
        return reborrowCount;
    }

    public void setReborrowCount(Integer reborrowCount) {
        this.reborrowCount = reborrowCount;
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