package com.ldh.edu.maiyu.sys.model;

import com.alibaba.fastjson.JSONObject;
import com.ldh.edu.maiyu.sys.dataobject.CartDo;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartData {
  public String id;

  public String userId;

  public String productId;

  public Integer number;

  public CartData() {
  }

  public CartData(String id, String userId, String productId, Integer number) {
    this.id=id;
    this.userId=userId;
    this.productId=productId;
    this.number=number;
  }

  public static CartData buildMapper(Cart obj) {
    CartData mapper = new CartData();
    mapper.id=obj.getId();
    mapper.userId=obj.getUserId();
    mapper.productId=obj.getProductId();
    mapper.number=obj.getNumber();
    return mapper;
  }

  public Map<String, Object> buildMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("id",this.id);
    map.put("userId",this.userId);
    map.put("productId",this.productId);
    map.put("number",this.number);
    return map;
  }

  public static Map<String, Object> buildMap(Cart obj) {
    Map<String, Object> map = new LinkedHashMap<>();
    if (obj == null) return map;
    map.put("id", obj.getId());
    map.put("userId", obj.getUserId());
    map.put("productId", obj.getProductId());
    map.put("number", obj.getNumber());
    return map;
  }

  public static Map<String, String> buildSerialMap(Cart obj) {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("id", JSONObject.toJSONString(obj.getId()));
    map.put("userId", JSONObject.toJSONString(obj.getUserId()));
    map.put("productId", JSONObject.toJSONString(obj.getProductId()));
    map.put("number", JSONObject.toJSONString(obj.getNumber()));
    return map;
  }

  public Cart buildEntity() {
    Cart obj = new Cart();
    obj.setId(this.id);
    obj.setUserId(this.userId);
    obj.setProductId(this.productId);
    obj.setNumber(this.number);
    return obj;
  }

  public static Cart parseEntity(Map<String, Object> map) {
    Cart obj = new Cart();
    if (map == null) return obj;
    obj.setId((String) map.get("id"));
    obj.setUserId((String) map.get("userId"));
    obj.setProductId((String) map.get("productId"));
    obj.setNumber((Integer) map.get("number"));
    return obj;
  }

  public static Cart parseSerialEntity(Map<String, String> map) {
    Cart obj = new Cart();
    if (map == null) return obj;
    obj.setId(JSONObject.parseObject(map.get("id"), String.class));
    obj.setUserId(JSONObject.parseObject(map.get("userId"), String.class));
    obj.setProductId(JSONObject.parseObject(map.get("productId"), String.class));
    obj.setNumber(JSONObject.parseObject(map.get("number"), Integer.class));
    return obj;
  }

  public static CartDo convert(Cart from, CartDo to) {
    if (from == null) return to;
    if (to == null) to = new CartDo();
    to.setId(from.getId());
    to.setUserId(from.getUserId());
    to.setProductId(from.getProductId());
    to.setNumber(from.getNumber());
    return to;
  }

  public static Cart convert(CartDo from, Cart to) {
    if (from == null) return to;
    if (to == null) to = new Cart();
    to.setId(from.getId());
    to.setUserId(from.getUserId());
    to.setProductId(from.getProductId());
    to.setNumber(from.getNumber());
    return to;
  }
}
