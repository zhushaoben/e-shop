package com.ldh.edu.maiyu.sys.web;

import com.ldh.edu.maiyu.sys.model.OrderProduct;
import com.ldh.edu.maiyu.sys.model.OrderProductDomain;
import com.ldh.edu.maiyu.sys.service.OrderProductService;
import com.ldh.edu.maiyu.sys.service.ProductService;
import com.ldh.edu.maiyu.sys.statu.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(
        value="orderProduct",
        tags = "订单-商品管理"
)
@RestController
@RequestMapping("/api/sys/orderProduct")
public class OrderProductController {
    @Autowired
    OrderProductService orderProductService;

    @Autowired
    ProductService productService;
    @ApiOperation(value="获得订单-商品实体",notes = "根据id获取信息")
    @RequestMapping(
            method= RequestMethod.GET,
            value = "single"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByPK(@RequestParam("id")String id){
        return Status.builder()
                .addDataValue(orderProductService.getSimpleMapByPK(id))
                .map();
    }

    @ApiOperation(value="获取orderProduct集合",notes = "根据id集合查找获得")
    @RequestMapping(
            value="list",
            method = RequestMethod.GET
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByIdList(@RequestBody List<String> idList){
        return Status.builder()
                .addDataValue(orderProductService.getMapListByKeySet(idList))
                .map();
    }


    @ApiOperation(value="新增单个订单-商品",notes = "传入订单-商品实体，返回插入后带主键等信息的实体")
    @RequestMapping(
            value="",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> post(@RequestBody OrderProduct orderProduct){
        return Status.builder()
                .addDataValue(orderProductService.post(OrderProductDomain.buildMap(orderProduct)))
                .map();
    }

    @ApiOperation(value="批量新增订单-商品实体",notes="同时返回插入后带主键的信息")
    @RequestMapping(
            value="postBatch",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> postBatch(@RequestBody List<OrderProduct> orderProducts){
        return Status.builder()
                .addDataValue(orderProductService.postList(orderProducts))
                .map();
    }

    @ApiOperation(value="更新订单-商品实体",notes = "同时返回插入后可能有改动的信息")
    @RequestMapping(
            method=RequestMethod.PATCH,
            value=""
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> patch(@RequestBody OrderProduct orderProduct){
        return Status.builder()
                .addDataValue(orderProductService.updateMapping(OrderProductDomain.buildMap(orderProduct)))
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
                .addDataValue(orderProductService.delete(id))
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
                .addDataValue(orderProductService.deleteList(idList))
                .map();
    }

    //功能实现部分
    @ApiOperation(value="订单详情功能",notes="根据顶单id查找该订单包含的所有商品信息，返回数据包括商品信息、" +
            "订单id、商品数量,可以传入分页参数，不传全返回")
    @RequestMapping(value = "selectAll/{orderId}",method = RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String,Object> selectAllByOrderId(@PathVariable("orderId")String orderId,
                                                 @RequestParam(value = "page",required = false)Long page,
                                                 @RequestParam(value = "rows",required = false)Integer rows){
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        System.out.println(map);
        if(page!=null){
            map.put("page",page);
            map.put("rows",rows);
        }
        List<Map<String,Object>> maps1 = orderProductService.getMapListByMap(map);
        System.out.println(maps1);
        List<String> list = new ArrayList<>();
        for(Map maps : maps1){
            list.add((String)maps.get("productId"));
        }
        System.out.println(list);
        //根据查到的商品键集合查找所有商品信息
        List<Map<String,Object>> products = productService.getMapListByKeySet(list);
        System.out.println(products);
        for(int i=0;i<maps1.size();i++){
            products.get(i).put("orderId",maps1.get(i).get("orderId"));
            products.get(i).put("number",maps1.get(i).get("number"));
        }
        return Status.builder()
                .addDataValue(products)
                .map();
    }
}
