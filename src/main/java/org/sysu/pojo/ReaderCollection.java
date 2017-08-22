package org.sysu.pojo;

import java.util.Date;

public class ReaderCollection {
    private Long id;

    private String account;

    private Long bid;

    private Date gmtCreate;

    private Date gmtModified;
    
    private InfoBook infoBook;
    
    public InfoBook getInfoBook() {
    	return infoBook;
    }
    
    public void setInfoBook(InfoBook infoBook) {	
    	this.infoBook = infoBook;
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

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
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