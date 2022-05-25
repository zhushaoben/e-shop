package com.ldh.edu.maiyu.sys.model;

import com.alibaba.fastjson.JSONObject;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderProductDefaultMapper {
  public String id;

  public String productId;

  public String orderId;

  public Integer number;

  public OrderProductDefaultMapper() {
  }

  public OrderProductDefaultMapper(String id, String productId, String orderId, Integer number) {
    this.id=id;
    this.productId=productId;
    this.orderId=orderId;
    this.number=number;
  }

  public static OrderProductDefaultMapper buildMapper(OrderProduct obj) {
    OrderProductDefaultMapper mapper = new OrderProductDefaultMapper();
    mapper.id=obj.getId();
    mapper.productId=obj.getProductId();
    mapper.orderId=obj.getOrderId();
    mapper.number=obj.getNumber();
    return mapper;
  }

  public Map<String, Object> buildMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("id",this.id);
    map.put("productId",this.productId);
    map.put("orderId",this.orderId);
    map.put("number",this.number);
    return map;
  }

  public static Map<String, Object> buildMap(OrderProduct obj) {
    Map<String, Object> map = new LinkedHashMap<>();
    if (obj == null) return map;
    map.put("id", obj.getId());
    map.put("productId", obj.getProductId());
    map.put("orderId", obj.getOrderId());
    map.put("number", obj.getNumber());
    return map;
  }

  public static Map<String, String> buildSerialMap(OrderProduct obj) {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("id", JSONObject.toJSONString(obj.getId()));
    map.put("productId", JSONObject.toJSONString(obj.getProductId()));
    map.put("orderId", JSONObject.toJSONString(obj.getOrderId()));
    map.put("number", JSONObject.toJSONString(obj.getNumber()));
    return map;
  }

  public OrderProduct buildEntity() {
    OrderProduct obj = new OrderProduct();
    obj.setId(this.id);
    obj.setProductId(this.productId);
    obj.setOrderId(this.orderId);
    obj.setNumber(this.number);
    return obj;
  }

  public static OrderProduct parseEntity(Map<String, Object> map) {
    OrderProduct obj = new OrderProduct();
    if (map == null) return obj;
    obj.setId((String) map.get("id"));
    obj.setProductId((String) map.get("productId"));
    obj.setOrderId((String) map.get("orderId"));
    obj.setNumber((Integer) map.get("number"));
    return obj;
  }

  public static OrderProduct parseSerialEntity(Map<String, String> map) {
    OrderProduct obj = new OrderProduct();
    if (map == null) return obj;
    obj.setId(JSONObject.parseObject(map.get("id"), String.class));
    obj.setProductId(JSONObject.parseObject(map.get("productId"), String.class));
    obj.setOrderId(JSONObject.parseObject(map.get("orderId"), String.class));
    obj.setNumber(JSONObject.parseObject(map.get("number"), Integer.class));
    return obj;
  }
}
