package com.ldh.edu.maiyu.sys.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductDo implements Serializable {
    private String id;

    private String busmanId;

    private BigDecimal price;

    private String name;

    private Integer stock;

    private Integer sell;

    private String imgPath;

    private String details;

    private Date gmtUpdate;

    private Date gmtCreate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBusmanId() {
        return busmanId;
    }

    public void setBusmanId(String busmanId) {
        this.busmanId = busmanId == null ? null : busmanId.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", busmanId=").append(busmanId);
        sb.append(", price=").append(price);
        sb.append(", name=").append(name);
        sb.append(", stock=").append(stock);
        sb.append(", sell=").append(sell);
        sb.append(", imgPath=").append(imgPath);
        sb.append(", details=").append(details);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}