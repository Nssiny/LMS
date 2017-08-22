package org.sysu.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReaderReservation {
    private Long id;

    private String barCode;

    private String account;

    private Date dateReservation;

    private Date dateBack;

    private Date dateEffective;

    private Byte isEffective;

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
    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getDateBack() {
        return dateBack;
    }

    public void setDateBack(Date dateBack) {
        this.dateBack = dateBack;
    }
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    public Date getDateEffective() {
        return dateEffective;
    }

    public void setDateEffective(Date dateEffective) {
        this.dateEffective = dateEffective;
    }

    public Byte getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Byte isEffective) {
        this.isEffective = isEffective;
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