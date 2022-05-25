package com.ldh.edu.maiyu.sys.service;


import com.ldh.edu.maiyu.sys.datainterface.OrderProductDoMapper;
import com.ldh.edu.maiyu.sys.dataobject.OrderProductDo;
import com.ldh.edu.maiyu.sys.dataobject.OrderProductDoExample;
import com.ldh.edu.maiyu.sys.model.OrderProduct;
import com.ldh.edu.maiyu.sys.model.OrderProductData;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderProductService {

    @Autowired
    OrderProductDoMapper orderProductDoMapper;

    public OrderProduct getByPK(String key){
        OrderProductDo entity = orderProductDoMapper.selectByPrimaryKey(key);
        return OrderProductData.convert(entity,new OrderProduct());
    }

    public Map<String,Object> getSimpleMapByPK(String key){
        return OrderProductData.buildMap(getByPK(key));
    }

    public Long getCountByExample(OrderProductDoExample example){
        return orderProductDoMapper.countByExample(example);
    }

    public List<OrderProduct> getListByExample(OrderProductDoExample example){
        List<OrderProduct> objects = new ArrayList<>();
        for(OrderProductDo cartDo : orderProductDoMapper.selectByExample(example)){
            objects.add(OrderProductData.convert(cartDo,new OrderProduct()));
        }
        return objects;
    }

    /*
     * 可以优化，使用example进行条件查询，与数据库一次交互就可查询成功
     * */
    public List<OrderProduct> getListByRelatedId(List<String> idList){
        List<OrderProduct> carts = new ArrayList<>();
        for(String id : idList){
            carts.add(getByPK(id));
        }
        return carts;
    }

    public List<Map<String,Object>> getMapListByExample(OrderProductDoExample example){
        List<OrderProduct> orderProducts = getListByExample(example);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(OrderProduct product : orderProducts){
            maps.add(OrderProductData.buildMap(product));
        }
        return maps;
    }

    public List<Map<String,Object>> getMapListByKeySet(List<String> idList){
        List<OrderProduct> orderProducts = getListByRelatedId(idList);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(OrderProduct orderProduct:orderProducts){
            maps.add(OrderProductData.buildMap(orderProduct));
        }
        return maps;
    }


    public OrderProduct post(OrderProduct postEntity){
        postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
        try{
            orderProductDoMapper.insertSelective(OrderProductData.convert(postEntity,new OrderProductDo()));
        }catch (DuplicateKeyException e){
            System.err.println("主键重复错误");
        }
        return getByPK(postEntity.getId());
    }

    /*
     * 用map进行插入,返回map
     * */
    public Map<String,Object> post(Map<String,Object> map){
        OrderProduct orderProduct = OrderProductData.parseEntity(map);
        return OrderProductData.buildMap(post(orderProduct));
    }

    public List<Map<String,Object>> postList(List<OrderProduct> orderProducts){
        List<Map<String,Object>> lists = new ArrayList<>();
        for(OrderProduct orderProduct : orderProducts){
            lists.add(OrderProductData.buildMap(post(orderProduct)));
        }
        return lists;
    }

    public OrderProduct update(OrderProduct orderProduct){
        Integer i = orderProductDoMapper.updateByPrimaryKeySelective(OrderProductData.convert(orderProduct,new OrderProductDo()));
        if(i<1){
            System.err.println("不存在该项值");
        }
        return OrderProductData.convert(orderProductDoMapper.selectByPrimaryKey(orderProduct.getId()),new OrderProduct());
    }

    public Map<String, Object> updateMapping(Map<String,Object> updateMapper) {
        OrderProduct orderProduct = OrderProductData.parseEntity(updateMapper);
        orderProduct = update(orderProduct);
        return OrderProductData.buildMap(orderProduct);
    }
    public List<Map<String,Object>> updateList(List<OrderProduct> orderProducts){
        List<Map<String,Object>> maps = new ArrayList<>();
        for(OrderProduct orderProduct : orderProducts){
            maps.add(updateMapping(OrderProductData.buildMap(orderProduct)));
        }
        return maps;
    }
    /*
     * 根据example进行更新
     * */
    public Integer updateByExample(OrderProduct orderProduct,OrderProductDoExample example){
        Integer i = orderProductDoMapper.updateByExampleSelective(OrderProductData.convert(orderProduct,new OrderProductDo()),example);
        return i;
    }
    public Integer updateByExample(Map<String,Object> map,OrderProductDoExample example){
        OrderProduct orderProduct= OrderProductData.parseEntity(map);
        Integer i = orderProductDoMapper.updateByExampleSelective(OrderProductData.convert(orderProduct,new OrderProductDo()),example);
        return i;
    }

    public Map<String,Object> updateByMap(OrderProduct order,Map<String,Object> map){
        OrderProductDoExample example = new OrderProductDoExample();
        OrderProductDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("productId")!=null){
            criteria.andProductIdEqualTo((String)map.get("productId"));
        }
        if(map.get("orderId")!=null){
            criteria.andOrderIdEqualTo((String)map.get("orderId"));
        }
        if(map.get("number")!=null){
            criteria.andNumberEqualTo((Integer)map.get("number"));
        }
        orderProductDoMapper.updateByExampleSelective(OrderProductData.convert(order,new OrderProductDo()),example);
        return null;
    }

    public List<Map<String,Object>> getMapListByMap(Map<String,Object> map){
        Long page=0l;
        Integer rows = 0;
        OrderProductDoExample example = new OrderProductDoExample();
        OrderProductDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("productId")!=null){
            criteria.andProductIdEqualTo((String)map.get("productId"));
        }
        if(map.get("orderId")!=null){
            criteria.andOrderIdEqualTo((String)map.get("orderId"));
        }
        if(map.get("number")!=null) {
            criteria.andNumberEqualTo((Integer) map.get("number"));
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

    //删除
    public Integer delete(String key){
        return orderProductDoMapper.deleteByPrimaryKey(key);
    }

    public Integer deleteList(List<String> keys){
        AtomicInteger count =new AtomicInteger();
        for(String key : keys){
            count.addAndGet(delete(key));
        }
        return count.get();
    }
    //根据example删除
    public Integer deleteByExample(OrderProductDoExample example){
        Integer i = orderProductDoMapper.deleteByExample(example);
        return i;
    }

}
