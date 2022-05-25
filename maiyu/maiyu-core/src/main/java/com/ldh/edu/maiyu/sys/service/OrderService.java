package com.ldh.edu.maiyu.sys.service;

import com.ldh.edu.maiyu.sys.datainterface.OrderDoMapper;
import com.ldh.edu.maiyu.sys.dataobject.OrderDo;
import com.ldh.edu.maiyu.sys.dataobject.OrderDoExample;
import com.ldh.edu.maiyu.sys.dataobject.OrderUserDo;
import com.ldh.edu.maiyu.sys.model.Order;
import com.ldh.edu.maiyu.sys.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {
    @Autowired
    private OrderDoMapper orderDoMapper;

    public Order getByPK(String key){
        OrderDo entity = orderDoMapper.selectByPrimaryKey(key);
        return OrderData.convert(entity,new Order());
    }

    public Map<String,Object> getSimpleMapByPK(String key){
        return OrderData.buildMap(getByPK(key));
    }

    public Long getCountByExample(OrderDoExample example){
        return orderDoMapper.countByExample(example);
    }

    public List<Order> getListByExample(OrderDoExample example){
        List<Order> objects = new ArrayList<>();
        for(OrderDo orderDo : orderDoMapper.selectByExample(example)){
            objects.add(OrderData.convert(orderDo,new Order()));
        }
        return objects;
    }

    /*
     * 可以优化，使用example进行条件查询，与数据库一次交互就可查询成功
     * */
    public List<Order> getListByRelatedId(List<String> idList){
        List<Order> orders = new ArrayList<>();
        List<OrderDo> orderDos = new ArrayList<>();
        for(String orderDoKey : idList){
            orderDos.add(orderDoMapper.selectByPrimaryKey(orderDoKey));
        }
        for(OrderDo orderDo : orderDos){
            orders.add(OrderData.convert(orderDo,new Order()));
        }
        return orders;
    }

    public List<Map<String,Object>> getMapListByExample(OrderDoExample example){
        List<Order> orders = getListByExample(example);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Order order : orders){
            maps.add(OrderData.buildMap(order));
        }
        return maps;
    }

    public List<Map<String,Object>> getMapListByKeySet(List<String> idList){
        List<Order> orders = getListByRelatedId(idList);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Order order : orders){
            maps.add(OrderData.buildMap(order));
        }
        return maps;
    }

    public List<Map<String,Object>> getMapListByMap(Map<String,Object> map){
        Long page=0l;
        Integer rows = 0;
        OrderDoExample example = new OrderDoExample();
        OrderDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("customerId")!=null){
            criteria.andCustomerIdEqualTo((String)map.get("customerId"));
        }
        if(map.get("busmanId")!=null){
            criteria.andBusmanIdEqualTo((String)map.get("busmanId"));
        }
        if(map.get("address")!=null){
            criteria.andAddressEqualTo((String)map.get("address"));
        }
        if(map.get("totalPrice")!=null){
            criteria.andTotalPriceEqualTo((BigDecimal) map.get("totalPrice"));
        }
        if(map.get("state")!=null){
            criteria.andStateEqualTo((Integer)map.get("state"));
        }

        if(map.get("page")!=null && map.get("rows")!=null){
            page = (Long)map.get("page");
            rows = (Integer)map.get("rows");
        }
        if(page>0){
            page = (page-1)*rows;
            example.setOffset(page);
            example.setLimit(rows);
        }
        return getMapListByExample(example);
    }



    public Order post(Order postEntity){
        postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
        try{
            orderDoMapper.insertSelective(OrderData.convert(postEntity,new OrderDo()));
        }catch (DuplicateKeyException e){
            System.err.println("主键重复错误");
        }
        return getByPK(postEntity.getId());
    }

    /*
     * 用map进行插入,返回map
     * */
    public Map<String,Object> post(Map<String,Object> map){
        Order order = OrderData.parseEntity(map);
        return OrderData.buildMap(post(order));
    }

    public List<Map<String,Object>> postList(List<Order> orders){
        List<Map<String,Object>> lists = new ArrayList<>();
        for(Order order : orders){
            lists.add(OrderData.buildMap(post(order)));
        }
        return lists;
    }

    public Order update(Order order){
        Integer i = orderDoMapper.updateByPrimaryKeySelective(OrderData.convert(order,new OrderDo()));
        if(i<1){
            System.err.println("不存在该项值");
        }
        return OrderData.convert(orderDoMapper.selectByPrimaryKey(order.getId()),new Order());
    }

    public Map<String, Object> updateMapping(Map<String,Object> updateMapper) {
        Order order = OrderData.parseEntity(updateMapper);
        order = update(order);
        return OrderData.buildMap(order);
    }
    public List<Map<String,Object>> updateList(List<Order> orders){
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Order order : orders){
            maps.add(updateMapping(OrderData.buildMap(order)));
        }
        return maps;
    }

    public Map<String,Object> updateByMap(Order order,Map<String,Object> map){
        Long page=0l;
        Integer rows = 0;
        OrderDoExample example = new OrderDoExample();
        OrderDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("customerId")!=null){
            criteria.andCustomerIdEqualTo((String)map.get("customerId"));
        }
        if(map.get("busmanId")!=null){
            criteria.andBusmanIdEqualTo((String)map.get("busmanId"));
        }
        if(map.get("address")!=null){
            criteria.andAddressEqualTo((String)map.get("address"));
        }
        if(map.get("totalPrice")!=null){
            criteria.andTotalPriceEqualTo((BigDecimal) map.get("totalPrice"));
        }
        if(map.get("state")!=null){
            criteria.andStateEqualTo((Integer)map.get("state"));
        }
        orderDoMapper.updateByExampleSelective(OrderData.convert(order,new OrderDo()),example);
        return null;
    }


    /*
     * 根据example进行更新
     * */
    public Integer updateByExample(Order order,OrderDoExample example){
        Integer i = orderDoMapper.updateByExampleSelective(OrderData.convert(order,new OrderDo()),example);
        return i;
    }
    public Integer updateByExample(Map<String,Object> map,OrderDoExample example){
        Order order = OrderData.parseEntity(map);
        Integer i = orderDoMapper.updateByExampleSelective(OrderData.convert(order,new OrderDo()),example);
        return i;
    }

    //删除
    public Integer delete(String key){
        return orderDoMapper.deleteByPrimaryKey(key);
    }

    public Integer deleteList(List<String> keys){
        AtomicInteger count =new AtomicInteger();
        for(String key : keys){
            count.addAndGet(delete(key));
        }
        return count.get();
    }
    //根据example删除
    public Integer deleteByExample(OrderDoExample example){
        Integer i = orderDoMapper.deleteByExample(example);
        return i;
    }

    public List<OrderUserDo> getOrderMessageByUserId(String userId){
        return orderDoMapper.getOrderUserByUserId(userId);
    }

}
