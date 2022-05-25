package com.ldh.edu.maiyu.sys.model;

import com.alibaba.fastjson.JSONObject;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserDefaultMapper {
  public String id;

  public String username;

  public String name;

  public String password;

  public String address;

  public String phone;

  public String email;

  public Integer type;

  public BigDecimal money;

  public Date gmtCreate;

  public UserDefaultMapper() {
  }

  public UserDefaultMapper(String id, String username, String name, String password, String address,
      String phone, String email, Integer type, BigDecimal money, Date gmtCreate) {
    this.id=id;
    this.username=username;
    this.name=name;
    this.password=password;
    this.address=address;
    this.phone=phone;
    this.email=email;
    this.type=type;
    this.money=money;
    this.gmtCreate=gmtCreate;
  }

  public static UserDefaultMapper buildMapper(User obj) {
    UserDefaultMapper mapper = new UserDefaultMapper();
    mapper.id=obj.getId();
    mapper.username=obj.getUsername();
    mapper.name=obj.getName();
    mapper.password=obj.getPassword();
    mapper.address=obj.getAddress();
    mapper.phone=obj.getPhone();
    mapper.email=obj.getEmail();
    mapper.type=obj.getType();
    mapper.money=obj.getMoney();
    mapper.gmtCreate=obj.getGmtCreate();
    return mapper;
  }

  public Map<String, Object> buildMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("id",this.id);
    map.put("username",this.username);
    map.put("name",this.name);
    map.put("password",this.password);
    map.put("address",this.address);
    map.put("phone",this.phone);
    map.put("email",this.email);
    map.put("type",this.type);
    map.put("money",this.money);
    map.put("gmtCreate",this.gmtCreate);
    return map;
  }

  public static Map<String, Object> buildMap(User obj) {
    Map<String, Object> map = new LinkedHashMap<>();
    if (obj == null) return map;
    map.put("id", obj.getId());
    map.put("username", obj.getUsername());
    map.put("name", obj.getName());
    map.put("password", obj.getPassword());
    map.put("address", obj.getAddress());
    map.put("phone", obj.getPhone());
    map.put("email", obj.getEmail());
    map.put("type", obj.getType());
    map.put("money", obj.getMoney());
    map.put("gmtCreate", obj.getGmtCreate());
    return map;
  }

  public static Map<String, String> buildSerialMap(User obj) {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("id", JSONObject.toJSONString(obj.getId()));
    map.put("username", JSONObject.toJSONString(obj.getUsername()));
    map.put("name", JSONObject.toJSONString(obj.getName()));
    map.put("password", JSONObject.toJSONString(obj.getPassword()));
    map.put("address", JSONObject.toJSONString(obj.getAddress()));
    map.put("phone", JSONObject.toJSONString(obj.getPhone()));
    map.put("email", JSONObject.toJSONString(obj.getEmail()));
    map.put("type", JSONObject.toJSONString(obj.getType()));
    map.put("money", JSONObject.toJSONString(obj.getMoney()));
    map.put("gmtCreate", JSONObject.toJSONString(obj.getGmtCreate()));
    return map;
  }

  public User buildEntity() {
    User obj = new User();
    obj.setId(this.id);
    obj.setUsername(this.username);
    obj.setName(this.name);
    obj.setPassword(this.password);
    obj.setAddress(this.address);
    obj.setPhone(this.phone);
    obj.setEmail(this.email);
    obj.setType(this.type);
    obj.setMoney(this.money);
    obj.setGmtCreate(this.gmtCreate);
    return obj;
  }

  public static User parseEntity(Map<String, Object> map) {
    User obj = new User();
    if (map == null) return obj;
    obj.setId((String) map.get("id"));
    obj.setUsername((String) map.get("username"));
    obj.setName((String) map.get("name"));
    obj.setPassword((String) map.get("password"));
    obj.setAddress((String) map.get("address"));
    obj.setPhone((String) map.get("phone"));
    obj.setEmail((String) map.get("email"));
    obj.setType((Integer) map.get("type"));
    obj.setMoney((BigDecimal) map.get("money"));
    obj.setGmtCreate((Date) map.get("gmtCreate"));
    return obj;
  }

  public static User parseSerialEntity(Map<String, String> map) {
    User obj = new User();
    if (map == null) return obj;
    obj.setId(JSONObject.parseObject(map.get("id"), String.class));
    obj.setUsername(JSONObject.parseObject(map.get("username"), String.class));
    obj.setName(JSONObject.parseObject(map.get("name"), String.class));
    obj.setPassword(JSONObject.parseObject(map.get("password"), String.class));
    obj.setAddress(JSONObject.parseObject(map.get("address"), String.class));
    obj.setPhone(JSONObject.parseObject(map.get("phone"), String.class));
    obj.setEmail(JSONObject.parseObject(map.get("email"), String.class));
    obj.setType(JSONObject.parseObject(map.get("type"), Integer.class));
    obj.setMoney(JSONObject.parseObject(map.get("money"), BigDecimal.class));
    obj.setGmtCreate(JSONObject.parseObject(map.get("gmtCreate"), Date.class));
    return obj;
  }
}
