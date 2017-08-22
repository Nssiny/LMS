package org.sysu.pojo;

import java.util.Date;

public class InfoCollection {
    private Long id;

    private String barCode;

    private Long bid;

    private String callno;

    private String located;

    private String type;

    private Byte isReservation;

    private Byte isBorrowing;

    private Date gmtCreate;

    private Date gmtModified;
    
    private Byte status;
    
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getCallno() {
        return callno;
    }

    public void setCallno(String callno) {
        this.callno = callno == null ? null : callno.trim();
    }

    public String getLocated() {
        return located;
    }

    public void setLocated(String located) {
        this.located = located == null ? null : located.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Byte getIsReservation() {
        return isReservation;
    }

    public void setIsReservation(Byte isReservation) {
        this.isReservation = isReservation;
    }

    public Byte getIsBorrowing() {
        return isBorrowing;
    }

    public void setIsBorrowing(Byte isBorrowing) {
        this.isBorrowing = isBorrowing;
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
    
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}