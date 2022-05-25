package com.ldh.edu.maiyu.sys.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderDo implements Serializable {
    private String id;

    private String customerId;

    private String busmanId;

    private String address;

    private BigDecimal totalPrice;

    private Date gmtCreate;

    private Integer state;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getBusmanId() {
        return busmanId;
    }

    public void setBusmanId(String busmanId) {
        this.busmanId = busmanId == null ? null : busmanId.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", busmanId=").append(busmanId);
        sb.append(", address=").append(address);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}