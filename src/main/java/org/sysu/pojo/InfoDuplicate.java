package org.sysu.pojo;

import java.util.Date;

public class InfoDuplicate {
    private Long id;

    private Long bid;

    private Integer numDuplicate;

    private Integer numBorrow;

    private Date gmtCreate;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Integer getNumDuplicate() {
        return numDuplicate;
    }

    public void setNumDuplicate(Integer numDuplicate) {
        this.numDuplicate = numDuplicate;
    }

    public Integer getNumBorrow() {
        return numBorrow;
    }

    public void setNumBorrow(Integer numBorrow) {
        this.numBorrow = numBorrow;
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