package com.ldh.edu.maiyu.sys.model;

import com.alibaba.fastjson.JSONObject;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderDefaultMapper {
  public String id;

  public String customerId;

  public String busmanId;

  public String address;

  public BigDecimal totalPrice;

  public Date gmtCreate;

  public Integer state;

  public OrderDefaultMapper() {
  }

  public OrderDefaultMapper(String id, String customerId, String busmanId, String address,
      BigDecimal totalPrice, Date gmtCreate, Integer state) {
    this.id=id;
    this.customerId=customerId;
    this.busmanId=busmanId;
    this.address=address;
    this.totalPrice=totalPrice;
    this.gmtCreate=gmtCreate;
    this.state=state;
  }

  public static OrderDefaultMapper buildMapper(Order obj) {
    OrderDefaultMapper mapper = new OrderDefaultMapper();
    mapper.id=obj.getId();
    mapper.customerId=obj.getCustomerId();
    mapper.busmanId=obj.getBusmanId();
    mapper.address=obj.getAddress();
    mapper.totalPrice=obj.getTotalPrice();
    mapper.gmtCreate=obj.getGmtCreate();
    mapper.state=obj.getState();
    return mapper;
  }

  public Map<String, Object> buildMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("id",this.id);
    map.put("customerId",this.customerId);
    map.put("busmanId",this.busmanId);
    map.put("address",this.address);
    map.put("totalPrice",this.totalPrice);
    map.put("gmtCreate",this.gmtCreate);
    map.put("state",this.state);
    return map;
  }

  public static Map<String, Object> buildMap(Order obj) {
    Map<String, Object> map = new LinkedHashMap<>();
    if (obj == null) return map;
    map.put("id", obj.getId());
    map.put("customerId", obj.getCustomerId());
    map.put("busmanId", obj.getBusmanId());
    map.put("address", obj.getAddress());
    map.put("totalPrice", obj.getTotalPrice());
    map.put("gmtCreate", obj.getGmtCreate());
    map.put("state", obj.getState());
    return map;
  }

  public static Map<String, String> buildSerialMap(Order obj) {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("id", JSONObject.toJSONString(obj.getId()));
    map.put("customerId", JSONObject.toJSONString(obj.getCustomerId()));
    map.put("busmanId", JSONObject.toJSONString(obj.getBusmanId()));
    map.put("address", JSONObject.toJSONString(obj.getAddress()));
    map.put("totalPrice", JSONObject.toJSONString(obj.getTotalPrice()));
    map.put("gmtCreate", JSONObject.toJSONString(obj.getGmtCreate()));
    map.put("state", JSONObject.toJSONString(obj.getState()));
    return map;
  }

  public Order buildEntity() {
    Order obj = new Order();
    obj.setId(this.id);
    obj.setCustomerId(this.customerId);
    obj.setBusmanId(this.busmanId);
    obj.setAddress(this.address);
    obj.setTotalPrice(this.totalPrice);
    obj.setGmtCreate(this.gmtCreate);
    obj.setState(this.state);
    return obj;
  }

  public static Order parseEntity(Map<String, Object> map) {
    Order obj = new Order();
    if (map == null) return obj;
    obj.setId((String) map.get("id"));
    obj.setCustomerId((String) map.get("customerId"));
    obj.setBusmanId((String) map.get("busmanId"));
    obj.setAddress((String) map.get("address"));
    obj.setTotalPrice((BigDecimal) map.get("totalPrice"));
    obj.setGmtCreate((Date) map.get("gmtCreate"));
    obj.setState((Integer) map.get("state"));
    return obj;
  }

  public static Order parseSerialEntity(Map<String, String> map) {
    Order obj = new Order();
    if (map == null) return obj;
    obj.setId(JSONObject.parseObject(map.get("id"), String.class));
    obj.setCustomerId(JSONObject.parseObject(map.get("customerId"), String.class));
    obj.setBusmanId(JSONObject.parseObject(map.get("busmanId"), String.class));
    obj.setAddress(JSONObject.parseObject(map.get("address"), String.class));
    obj.setTotalPrice(JSONObject.parseObject(map.get("totalPrice"), BigDecimal.class));
    obj.setGmtCreate(JSONObject.parseObject(map.get("gmtCreate"), Date.class));
    obj.setState(JSONObject.parseObject(map.get("state"), Integer.class));
    return obj;
  }
}
