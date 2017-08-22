package org.sysu.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InfoPenalty {
    private Long id;

    private Long rbid;

    private String account;

    private Date datePenalty;

    private Integer pricePenalty;

    private Date datePay;

    private Integer pricePay;

    private Date gmtCreate;

    private Date gmtModified;
    
    private InfoBook infoBook;
    
    private ReaderBorrow readerBorrow;
    
    private InfoCollection infoCollection;
    
    public InfoBook getInfoBook() {
    	return infoBook;
    }
    
    public void setInfoBook(InfoBook infoBook) {	
    	this.infoBook = infoBook;
    }
    
    public ReaderBorrow getReaderBorrow() {
    	return readerBorrow;
    }
    
    public void setReaderBorrow(ReaderBorrow readerBorrow) {
    	this.readerBorrow = readerBorrow;
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

    public Long getRbid() {
        return rbid;
    }

    public void setRbid(Long rbid) {
        this.rbid = rbid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getDatePenalty() {
        return datePenalty;
    }

    public void setDatePenalty(Date datePenalty) {
        this.datePenalty = datePenalty;
    }

    public Integer getPricePenalty() {
        return pricePenalty;
    }

    public void setPricePenalty(Integer pricePenalty) {
        this.pricePenalty = pricePenalty;
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getDatePay() {
        return datePay;
    }

    public void setDatePay(Date datePay) {
        this.datePay = datePay;
    }

    public Integer getPricePay() {
        return pricePay;
    }

    public void setPricePay(Integer pricePay) {
        this.pricePay = pricePay;
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