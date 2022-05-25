package com.ldh.edu.maiyu.sys.web;

import com.ldh.edu.maiyu.sys.dataobject.OrderUserDo;
import com.ldh.edu.maiyu.sys.model.Order;
import com.ldh.edu.maiyu.sys.model.OrderDomain;
import com.ldh.edu.maiyu.sys.model.OrderProduct;
import com.ldh.edu.maiyu.sys.model.User;
import com.ldh.edu.maiyu.sys.service.CartService;
import com.ldh.edu.maiyu.sys.service.OrderProductService;
import com.ldh.edu.maiyu.sys.service.OrderService;
import com.ldh.edu.maiyu.sys.service.UserService;
import com.ldh.edu.maiyu.sys.statu.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="order",tags="订单管理")
@RestController
@RequestMapping(value = "/api/sys/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    OrderProductService orderProductService;
    @Autowired
    @Qualifier("template")
    RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value="获得订单实体",notes = "根据id获取信息")
    @RequestMapping(
            method= RequestMethod.GET,
            value = "single"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByPK(@RequestParam("id")String id){
        return Status.builder()
                .addDataValue(orderService.getSimpleMapByPK(id))
                .map();
    }

    @ApiOperation(value="获取order集合",notes = "根据id集合查找获得")
    @RequestMapping(
            value="list",
            method = RequestMethod.GET
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByIdList(@RequestBody List<String> idList){
        return Status.builder()
                .addDataValue(orderService.getMapListByKeySet(idList))
                .map();
    }


    @ApiOperation(value="新增单个订单",notes = "传入订单实体，返回插入后带主键等信息的实体")
    @RequestMapping(
            value="",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> post(@RequestBody Order order){
        return Status.builder()
                .addDataValue(orderService.post(OrderDomain.buildMap(order)))
                .map();
    }

    @ApiOperation(value="批量新增订单实体",notes="同时返回插入后带主键的信息")
    @RequestMapping(
            value="postBatch",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> postBatch(@RequestBody List<Order> orders){
        return Status.builder()
                .addDataValue(orderService.postList(orders))
                .map();
    }

    @ApiOperation(value="更新订单实体",notes = "同时返回插入后可能有改动的信息")
    @RequestMapping(
            method=RequestMethod.PATCH,
            value=""
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> patch(@RequestBody Order order){
        return Status.builder()
                .addDataValue(orderService.updateMapping(OrderDomain.buildMap(order)))
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
                .addDataValue(orderService.delete(id))
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
                .addDataValue(orderService.deleteList(idList))
                .map();
    }

    //功能实现部分

    @ApiOperation(value="根据顾客id查找",notes = "根据用户id查找，可以传参进行分页，不穿就不分")
    @RequestMapping(method=RequestMethod.GET,value = "selectAll/{customerId}")
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByUserId(@RequestParam(value = "page",required = false)Long page,
                                          @RequestParam(value = "rows",required = false)Integer rows,
                                          @PathVariable("customerId")String costumerId){
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",rows);
        map.put("customerId",costumerId);
        return Status.builder()
                .addDataValue(orderService.getMapListByMap(map))
                .map();
    }

    @ApiOperation(value="根据商家id查找",notes = "根据商家id查找，可以传参进行分页，不穿就不分")
    @RequestMapping(method=RequestMethod.GET,value = "selectAll/busman/{busmanId}")
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByBusManId(@RequestParam(value = "page",required = false)Long page,
                                          @RequestParam(value = "rows",required = false)Integer rows,
                                          @PathVariable("busmanId")String busmanId){
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",rows);
        map.put("busmanId",busmanId);
        return Status.builder()
                .addDataValue(orderService.getMapListByMap(map))
                .map();
    }

    @ApiOperation(value="生成订单/清空购物车",notes = "实现结算功能,金额是否足够前端判断实现" +
            "，以json格式传入订单的相关信息,同时以参数形式传递用户的余额，为了满足订单退订功能,每个商家都会生成一个订单")
    @RequestMapping(method=RequestMethod.POST,value="cleanCart")
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> produceOrder(@RequestParam("userId") String userId,
                                           @RequestParam("address")String address,
                                           @RequestParam("remainMoney")BigDecimal money,
                                           @CookieValue("token")String token){
        //更新用户金额模块
        User user = new User();
        user.setMoney(money);
        Map<String,Object> map = new HashMap<>();
        map.put("id",userId);
        User user2 = (User)redisTemplate.opsForValue().get(token);
        user2.setMoney(money);
        redisTemplate.opsForValue().set(token,user2);
        userService.updateByMap(user,map);
        //插入订单模块
        //根据购物车插入订单-product模块
        //删除购物车相应条目
        /*
            按商户id返回的，每个商户都会生成一个应的订单，根据购物车查到了要购物的所有商品，根据数量与单价
            计算每个订单的总金额，插入
             C.id cartId,
		     C.product_id productId,
		     C.number number,
		     P.busman_id busmanId
		     P.price price
        *  */
        List<OrderUserDo> maps2 = orderService.getOrderMessageByUserId(userId);
//        System.out.println(maps2);
        String busmanId = "slkdjsd",orderId="heheda";
        List<String> cartIds = new ArrayList<>();
        BigDecimal decimal = BigDecimal.ZERO;
        for(OrderUserDo orderUserDo : maps2){
            if(!busmanId.equals(orderUserDo.getBusmanId())){
                if(!orderId.equals("heheda"))
                    orderService.update(new Order(orderId,null,null,null,decimal,null,null));
                busmanId = (String)orderUserDo.getBusmanId();
                Order order = new Order(null,userId,busmanId,address, BigDecimal.ZERO,null,0);
                order = orderService.post(order);
                orderId=order.getId();
                decimal = BigDecimal.ZERO;
            }
            decimal = decimal.add(orderUserDo.getPrice().multiply(new BigDecimal(orderUserDo.getNumber())));
            cartIds.add(orderUserDo.getCardId());
            OrderProduct orderProduct = new OrderProduct(null,orderUserDo.getProductId(),orderId,orderUserDo.getNumber());
            orderProductService.post(orderProduct);
        }
//        System.out.println(decimal);
        orderService.update(new Order(orderId,null,null,null,decimal,null,null));
        cartService.deleteList(cartIds);
        return Status.builder()
                .addDataValue("true")
                .map();
    }

    @ApiOperation(value = "用户退订",notes = "传入订单id")
    @RequestMapping(value = "cancelByCos/{orderId}",method = RequestMethod.PATCH)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> cancelByCos(@PathVariable("orderId")String orderId){
        Order order = new Order();
        order.setState(1);
        Map<String,Object> map = new HashMap<>();
        map.put("id",orderId);
        orderService.updateByMap(order,map);
        return Status.builder()
                .addDataValue("success")
                .map();
    }

    @ApiOperation(value = "商家承诺退订",notes = "传入订单id,同时给我退订后用户所剩的总金额")
    @RequestMapping(value = "cancelByBus/{orderId}",method = RequestMethod.PATCH)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> cancelByBus(@PathVariable("orderId")String orderId,
                                          @RequestParam("money")BigDecimal money,
                                          @CookieValue("token")String token){
        Order order = new Order();
        order.setState(2);
        Map<String,Object> map = new HashMap<>();
        map.put("id",orderId);
        orderService.updateByMap(order,map);
        User user2 = (User)redisTemplate.opsForValue().get(token);
        user2.setMoney(money);
        redisTemplate.opsForValue().set(token,user2);
        return Status.builder()
                .addDataValue("success")
                .map();
    }
    @ApiOperation(value = "商家不退订",notes = "传入订单id")
    @RequestMapping(value = "noCancelByBus/{orderId}",method = RequestMethod.PATCH)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> noCancelByBus(@PathVariable("orderId")String orderId){
        Order order = new Order();
        order.setState(3);
        Map<String,Object> map = new HashMap<>();
        map.put("id",orderId);
        orderService.updateByMap(order,map);
        return Status.builder()
                .addDataValue("success")
                .map();
    }

}
