package com.ldh.edu.maiyu.sys.web;

import com.ldh.edu.maiyu.sys.model.Order;
import com.ldh.edu.maiyu.sys.model.Product;
import com.ldh.edu.maiyu.sys.model.User;
import com.ldh.edu.maiyu.sys.model.UserDomain;
import com.ldh.edu.maiyu.sys.service.OrderService;
import com.ldh.edu.maiyu.sys.service.ProductService;
import com.ldh.edu.maiyu.sys.service.UserService;
import com.ldh.edu.maiyu.sys.statu.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api(
        value="user",
        tags = "用户管理"
)
@RestController
@RequestMapping("/api/sys/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;
    @Autowired
    @Qualifier("template")
    RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value="获得用户实体",notes = "根据id获取信息")
    @RequestMapping(
            method= RequestMethod.GET,
            value = "single"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByPK(@RequestParam("id")String id){
        return Status.builder()
                .addDataValue(userService.getSimpleMapByPK(id))
                .map();
    }

    @ApiOperation(value="获取user集合",notes = "根据id集合查找获得")
    @RequestMapping(
            value="list",
            method = RequestMethod.GET
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByIdList(@RequestBody List<String> idList){
        return Status.builder()
                .addDataValue(userService.getMapListByKeySet(idList))
                .map();
    }


    @ApiOperation(value="新增单个用户",notes = "传入用户实体，返回插入后带主键等信息的实体")
    @RequestMapping(
            value="",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> post(@RequestBody User user){
        return Status.builder()
                .addDataValue(userService.post(UserDomain.buildMap(user)))
                .map();
    }

    @ApiOperation(value="批量新增用户实体",notes="同时返回插入后带主键的信息")
    @RequestMapping(
            value="postBatch",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> postBatch(@RequestBody List<User> users){
        return Status.builder()
                .addDataValue(userService.postList(users))
                .map();
    }

    @ApiOperation(value="更新用户实体",notes = "同时返回插入后可能有改动的信息")
    @RequestMapping(
            method=RequestMethod.PATCH,
            value=""
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> patch(@RequestBody User user){
        return Status.builder()
                .addDataValue(userService.updateMapping(UserDomain.buildMap(user)))
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
                .addDataValue(userService.delete(id))
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
                .addDataValue(userService.deleteList(idList))
                .map();
    }

    //具体功能实现考虑

    @ApiOperation(value="得到用户集合",notes = "根据类型获取")
    @RequestMapping(
            method=RequestMethod.GET,
            value="selectAll/{type}"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> selectAllWithType(@PathVariable("type")Integer type,
                                                @RequestParam(value = "page",required = false)Long page,
                                                @RequestParam(value = "rows",required = false)Integer rows){
        Map<String,Object> map = new HashMap<>();
        if(type<=2) {
            map.put("type", type);
            if(page!= null){
                map.put("page",page);
            }
            if(rows!=null){
                map.put("rows",rows);
            }
            return Status.builder()
                    .addDataValue(userService.getMapListByMap(map))
                    .map();
        }else{
            log.error("参数type传递错误,type={}",type);
            return Status.builder()
                    .addStatus(500)
                    .addMessage("类型值不存在")
                    .map();
        }
    }

    @ApiOperation(value="所用用户",notes = "不区分类型，全部返回,传入分页参数可以进行分页")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "selectAll"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> selectAllWithType(@RequestParam(value = "page",required = false)Long page,
                                                @RequestParam(value = "rows",required = false)Integer rows){
        Map<String,Object> map = new HashMap<>();
        if(page!= null){
            map.put("page",page);
        }
        if(rows!=null){
            map.put("rows",rows);
        }
        return Status.builder()
                .addDataValue(userService.getMapListByMap(map))
                .map();
    }

    @ApiOperation(value="登录",notes = "传入用户名，密码之后验证，返回登录信息，成功为true，失败为false")
    @RequestMapping(
            value="login/{username}/{password}",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    public Map<String,Object> login(@PathVariable("username") String username,
                                    @PathVariable("password")String password,
                                    HttpServletResponse response,
                                    HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        log.info("1111");
        List<Map<String,Object>> list = userService.getMapListByMap(map);
        log.info("222");
        if(list!=null&&list.size()==0){
            Map<String,String> map2 = new HashMap<>();
            map2.put("result","false");
            return Status.builder()
                    .addDataValue(map2)
                    .addStatus(300)
                    .addMessage("用户名或者密码错误")
                    .map();
        }else{
            User user = UserDomain.parseEntity(list.get(0));
            //登录成功对登录信息的记录逻辑
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Allow-Origin","*");
            String cookId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32);
            redisTemplate.opsForValue().set(cookId,user,3600,TimeUnit.SECONDS);
            Cookie cookie = new Cookie("token",cookId);
            cookie.setPath(request.getContextPath()+"/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            Map<String,String> map2 = new HashMap<>();
            map2.put("token",cookId);
            map2.put("result","true");
            return Status.builder()
                    .addDataValue(map2)
                    .map();
        }
    }

    @RequestMapping(
            value = "search/{{type}/{name}",
            method=RequestMethod.GET
    )
    public Map<String,Object> search(@PathVariable("type")Integer type,
                                     @PathVariable("name")String name){

        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("name",name);
        return Status.builder()
                .addDataValue(userService.getMapListByMap(map))
                .map();
    }

    @ApiOperation(value="根据商品id找商家",notes = "传入商品id进行查询，返回商家信息")
    @RequestMapping(value="busman/{productId}",method=RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getBusManByProductId(@PathVariable("productId")String productId){
        Product product = productService.getByPK(productId);
        return Status.builder()
                .addDataValue(userService.getSimpleMapByPK(product.getBusmanId()))
                .map();
    }

    @ApiOperation(value="根据商品名查找所有商家",notes="传入商品的名称，将进行模糊查询得到相应商家")
    @RequestMapping(value="busman/name/{productName}",method=RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getBusMansByProductName(@PathVariable("productName")String productName){
        Map<String,Object> map = new HashMap<>();
        map.put("name",productName);
        List<Map<String,Object>> maps = productService.getMapListByMap(map);
        List<String> idList = new ArrayList<>();
        for(Map map1 : maps){
            if(!idList.contains((String)map1.get("busmanId")))
                idList.add((String)map1.get("busmanId"));
        }
        return Status.builder()
                .addDataValue(userService.getMapListByKeySet(idList))
                .map();
    }

    @ApiOperation(value="根据订单id找到用户信息",notes = "传入订单id值即可")
    @RequestMapping(value="customer/{orderId}",method=RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getCustomerByOrderId(@PathVariable("orderId")String orderId){
        Order order = orderService.getByPK(orderId);
        return Status.builder()
                .addDataValue(userService.getSimpleMapByPK(order.getCustomerId()))
                .map();
    }

    @ApiOperation(value="注册功能",notes="传入信息封装为User对象,传递json字符串" +
            ",根据数据库的设计不要让特定信息为null值,返回信息，false为注册失败，true为注册成功")
    @RequestMapping(value="register",method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> register(@RequestBody User user,HttpServletResponse response,
                                       HttpServletRequest request){
        String username = user.getUsername();
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        List<Map<String,Object>> maps = userService.getMapListByMap(map);
        if(map!=null&&maps.size()!=0){
            Map<String,String> map2 = new HashMap<>();
            map2.put("result","false");
            return Status.builder()
                    .addDataValue(map2)
                    .addMessage("用户名已存在")
                    .addStatus(301)
                    .map();
        }else{
            //插入实体用户
            User user2 = userService.post(user);
            //登录功能相同：
            String cookId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32);
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Allow-Origin","*");
            redisTemplate.opsForValue().set(cookId,user2,3600, TimeUnit.SECONDS);
            Cookie cookie = new Cookie("token",cookId);
            cookie.setPath(request.getContextPath()+"/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            Map<String,String> map2 = new HashMap<>();
            map2.put("token",cookId);
            map2.put("result","true");
            return Status.builder()
                    .addDataValue(map2)
                    .map();
        }
    }

    @ApiOperation(value="logout功能",notes = "使用此api后，服务器记录的登录信息将全部销毁，需要重新登录")
    @RequestMapping(value="logout",method=RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> register(HttpServletResponse response,
                                       @CookieValue("token")String token){
        //登出逻辑
        redisTemplate.delete(token);
        Cookie cookie = new Cookie(token,"k");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return Status.builder()
                .addDataValue("true")
                .map();
    }

    @ApiOperation(value = "获取登录存储的用户信息")
    @RequestMapping(value="login/getUser",method = RequestMethod.GET)
    public Map<String,Object> getLoginUser(@CookieValue("token")String token){
        User user = (User)redisTemplate.opsForValue().get(token);
        return Status.builder()
                .addDataValue(UserDomain.buildMap(user))
                .map();
    }




}
