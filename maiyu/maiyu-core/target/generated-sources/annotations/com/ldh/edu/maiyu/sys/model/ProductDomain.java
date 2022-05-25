package com.ldh.edu.maiyu.sys.model;

import com.alibaba.fastjson.JSONObject;
import com.ldh.edu.maiyu.sys.domain.ProductTo;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductDomain {
  public String id;

  public String busmanId;

  public BigDecimal price;

  public String name;

  public Integer stock;

  public Integer sell;

  public String imgPath;

  public String details;

  public Date gmtUpdate;

  public Date gmtCreate;

  public ProductDomain() {
  }

  public ProductDomain(String id, String busmanId, BigDecimal price, String name, Integer stock,
      Integer sell, String imgPath, String details, Date gmtUpdate, Date gmtCreate) {
    this.id=id;
    this.busmanId=busmanId;
    this.price=price;
    this.name=name;
    this.stock=stock;
    this.sell=sell;
    this.imgPath=imgPath;
    this.details=details;
    this.gmtUpdate=gmtUpdate;
    this.gmtCreate=gmtCreate;
  }

  public static ProductDomain buildMapper(Product obj) {
    ProductDomain mapper = new ProductDomain();
    mapper.id=obj.getId();
    mapper.busmanId=obj.getBusmanId();
    mapper.price=obj.getPrice();
    mapper.name=obj.getName();
    mapper.stock=obj.getStock();
    mapper.sell=obj.getSell();
    mapper.imgPath=obj.getImgPath();
    mapper.details=obj.getDetails();
    mapper.gmtUpdate=obj.getGmtUpdate();
    mapper.gmtCreate=obj.getGmtCreate();
    return mapper;
  }

  public Map<String, Object> buildMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("id",this.id);
    map.put("busmanId",this.busmanId);
    map.put("price",this.price);
    map.put("name",this.name);
    map.put("stock",this.stock);
    map.put("sell",this.sell);
    map.put("imgPath",this.imgPath);
    map.put("details",this.details);
    map.put("gmtUpdate",this.gmtUpdate);
    map.put("gmtCreate",this.gmtCreate);
    return map;
  }

  public static Map<String, Object> buildMap(Product obj) {
    Map<String, Object> map = new LinkedHashMap<>();
    if (obj == null) return map;
    map.put("id", obj.getId());
    map.put("busmanId", obj.getBusmanId());
    map.put("price", obj.getPrice());
    map.put("name", obj.getName());
    map.put("stock", obj.getStock());
    map.put("sell", obj.getSell());
    map.put("imgPath", obj.getImgPath());
    map.put("details", obj.getDetails());
    map.put("gmtUpdate", obj.getGmtUpdate());
    map.put("gmtCreate", obj.getGmtCreate());
    return map;
  }

  public static Map<String, String> buildSerialMap(Product obj) {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("id", JSONObject.toJSONString(obj.getId()));
    map.put("busmanId", JSONObject.toJSONString(obj.getBusmanId()));
    map.put("price", JSONObject.toJSONString(obj.getPrice()));
    map.put("name", JSONObject.toJSONString(obj.getName()));
    map.put("stock", JSONObject.toJSONString(obj.getStock()));
    map.put("sell", JSONObject.toJSONString(obj.getSell()));
    map.put("imgPath", JSONObject.toJSONString(obj.getImgPath()));
    map.put("details", JSONObject.toJSONString(obj.getDetails()));
    map.put("gmtUpdate", JSONObject.toJSONString(obj.getGmtUpdate()));
    map.put("gmtCreate", JSONObject.toJSONString(obj.getGmtCreate()));
    return map;
  }

  public Product buildEntity() {
    Product obj = new Product();
    obj.setId(this.id);
    obj.setBusmanId(this.busmanId);
    obj.setPrice(this.price);
    obj.setName(this.name);
    obj.setStock(this.stock);
    obj.setSell(this.sell);
    obj.setImgPath(this.imgPath);
    obj.setDetails(this.details);
    obj.setGmtUpdate(this.gmtUpdate);
    obj.setGmtCreate(this.gmtCreate);
    return obj;
  }

  public static Product parseEntity(Map<String, Object> map) {
    Product obj = new Product();
    if (map == null) return obj;
    obj.setId((String) map.get("id"));
    obj.setBusmanId((String) map.get("busmanId"));
    obj.setPrice((BigDecimal) map.get("price"));
    obj.setName((String) map.get("name"));
    obj.setStock((Integer) map.get("stock"));
    obj.setSell((Integer) map.get("sell"));
    obj.setImgPath((String) map.get("imgPath"));
    obj.setDetails((String) map.get("details"));
    obj.setGmtUpdate((Date) map.get("gmtUpdate"));
    obj.setGmtCreate((Date) map.get("gmtCreate"));
    return obj;
  }

  public static Product parseSerialEntity(Map<String, String> map) {
    Product obj = new Product();
    if (map == null) return obj;
    obj.setId(JSONObject.parseObject(map.get("id"), String.class));
    obj.setBusmanId(JSONObject.parseObject(map.get("busmanId"), String.class));
    obj.setPrice(JSONObject.parseObject(map.get("price"), BigDecimal.class));
    obj.setName(JSONObject.parseObject(map.get("name"), String.class));
    obj.setStock(JSONObject.parseObject(map.get("stock"), Integer.class));
    obj.setSell(JSONObject.parseObject(map.get("sell"), Integer.class));
    obj.setImgPath(JSONObject.parseObject(map.get("imgPath"), String.class));
    obj.setDetails(JSONObject.parseObject(map.get("details"), String.class));
    obj.setGmtUpdate(JSONObject.parseObject(map.get("gmtUpdate"), Date.class));
    obj.setGmtCreate(JSONObject.parseObject(map.get("gmtCreate"), Date.class));
    return obj;
  }

  public static ProductTo convert(Product from, ProductTo to) {
    if (from == null) return to;
    if (to == null) to = new ProductTo();
    to.setId(from.getId());
    to.setBusmanId(from.getBusmanId());
    to.setPrice(from.getPrice());
    to.setName(from.getName());
    to.setStock(from.getStock());
    to.setSell(from.getSell());
    to.setImgPath(from.getImgPath());
    to.setDetails(from.getDetails());
    to.setGmtUpdate(from.getGmtUpdate());
    to.setGmtCreate(from.getGmtCreate());
    return to;
  }

  public static Product convert(ProductTo from, Product to) {
    if (from == null) return to;
    if (to == null) to = new Product();
    to.setId(from.getId());
    to.setBusmanId(from.getBusmanId());
    to.setPrice(from.getPrice());
    to.setName(from.getName());
    to.setStock(from.getStock());
    to.setSell(from.getSell());
    to.setImgPath(from.getImgPath());
    to.setDetails(from.getDetails());
    to.setGmtUpdate(from.getGmtUpdate());
    to.setGmtCreate(from.getGmtCreate());
    return to;
  }
}
