package com.ldh.edu.maiyu.sys.web;


import com.ldh.edu.maiyu.sys.model.Cart;
import com.ldh.edu.maiyu.sys.model.CartDomain;
import com.ldh.edu.maiyu.sys.service.CartService;
import com.ldh.edu.maiyu.sys.service.ProductService;
import com.ldh.edu.maiyu.sys.service.UserService;
import com.ldh.edu.maiyu.sys.statu.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.rmi.transport.ObjectTable;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(
        value="cart",
        tags = "购物车管理"
)
@RestController
@RequestMapping("/api/sys/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @Autowired
    @Qualifier("template")
    RedisTemplate<String,Object> redisTemplate;


    @ApiOperation(value="获得购物车实体",notes = "根据id获取信息")
    @RequestMapping(
            method= RequestMethod.GET,
            value = "single"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByPK(@RequestParam("id")String id){
        return Status.builder()
                .addDataValue(cartService.getSimpleMapByPK(id))
                .map();
    }

    @ApiOperation(value="获取cart集合",notes = "根据id集合查找获得")
    @RequestMapping(
            value="list",
            method = RequestMethod.GET
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByIdList(@RequestBody List<String> idList){
        return Status.builder()
                .addDataValue(cartService.getMapListByKeySet(idList))
                .map();
    }


    @ApiOperation(value="新增单个购物车",notes = "传入购物车实体，返回插入后带主键等信息的实体")
    @RequestMapping(
            value="",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> post(@RequestBody Cart cart){
        return Status.builder()
                .addDataValue(cartService.post(CartDomain.buildMap(cart)))
                .map();
    }

    @ApiOperation(value="批量新增购物车实体",notes="同时返回插入后带主键的信息")
    @RequestMapping(
            value="postBatch",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> postBatch(@RequestBody List<Cart> carts){
        return Status.builder()
                .addDataValue(cartService.postList(carts))
                .map();
    }

    @ApiOperation(value="更新购物车实体",notes = "同时返回插入后可能有改动的信息")
    @RequestMapping(
            method=RequestMethod.PATCH,
            value=""
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> patch(@RequestBody Cart cart){
        return Status.builder()
                .addDataValue(cartService.updateMapping(CartDomain.buildMap(cart)))
                .map();
    }

    @ApiOperation(value="删除实体",notes="根据主键id进行删除，返回删除数量")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value=""
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> delete(@RequestParam("id")String id){
        return Status.builder()
                .addDataValue(cartService.delete(id))
                .map();
    }

    @ApiOperation(value="批量删除",notes="根据主键id进行批量删除，返回删除数量")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value="deleteBatch"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> deleteBatch(@RequestBody List<String> idList){
        return Status.builder()
                .addDataValue(cartService.deleteList(idList))
                .map();
    }

    //购物车功能相关信息
    @ApiOperation(value="根据用户id得到购物车商品信息",notes = "传入用户id信息，返回值为商品信息" +
            "，加上各商品的数量与cartId")
    @RequestMapping(
            method=RequestMethod.GET,
            value="select/{userId}"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getCartByUserId(@PathVariable("userId")String userId){
        //首先根据userId得到所有商品ID与相应的数量number
        Map<String,Object> map1 = new HashMap<>();
        map1.put("userId",userId);
        List<Map<String,Object>> maps1 = cartService.getMapListByMap(map1);
        List<String> productIds = new ArrayList<>();
        for(Map map : maps1){
            productIds.add((String)map.get("productId"));
        }
        List<Map<String,Object>> maps2 = productService.getMapListByKeySet(productIds);
        for(int i=0;i<maps1.size();i++){
            maps2.get(i).put("cartId",maps1.get(i).get("id"));
            maps2.get(i).put("number",maps1.get(i).get("number"));
        }
        return Status.builder()
                .addDataValue(maps2)
                .map();
    }

    @ApiOperation(value="添加购物数量",notes = "传入用户id、购物车id、购物车所存该商品的数量" +
            "，如果数量为0，则自动新增物品，其他数量则number+1,返回true或false")
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "add/{userId}/{productId}/{number}"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addCart(@PathVariable("userId")String userId,
                                       @PathVariable("productId")String productId,
                                       @PathVariable("number")Integer number){

        if(number==0){
            Cart cart = new Cart(null,userId,productId,1);
            cartService.post(cart);
            return Status.builder()
                    .addDataValue("true")
                    .addMessage("初次添加，新建购物车条目")
                    .map();
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("productId",productId);
            map.put("number",number+1);
            cartService.updateByMap(map);
            return Status.builder()
                    .addDataValue("添加成功")
                    .map();
        }
    }

    @ApiOperation(value="添加购物数量",notes = "传入用户id、购物车id、购物车所存该商品的数量" +
            "，如果数量为0，则自动新增物品，其他数量则number+1,返回true或false")
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "reduce/{userId}/{productId}/{number}"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> reduceCart(@PathVariable("userId")String userId,
                                       @PathVariable("productId")String productId,
                                       @PathVariable("number")Integer number){

        if(number==1){
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("productId",productId);
            cartService.deleteByMap(map);
            return Status.builder()
                    .addDataValue("true")
                    .addMessage("删除购物车条目")
                    .map();
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("productId",productId);
            map.put("number",number-1);
            cartService.updateByMap(map);
            return Status.builder()
                    .addDataValue("减少成功")
                    .map();
        }
    }

//    @RequestMapping("test/redis/put")
//    public Object put(){
//        Cart cart = new Cart("111","111","111",20);
//        redisTemplate.opsForValue().set("test1",cart);
//        return redisTemplate.opsForValue().get("test1");
//    }



}
