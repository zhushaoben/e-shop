package com.ldh.edu.maiyu.sys.service;

import com.ldh.edu.maiyu.sys.datainterface.CartDoMapper;
import com.ldh.edu.maiyu.sys.dataobject.CartDo;
import com.ldh.edu.maiyu.sys.dataobject.CartDoExample;
import com.ldh.edu.maiyu.sys.domain.CartTo;
import com.ldh.edu.maiyu.sys.model.Cart;
import com.ldh.edu.maiyu.sys.model.CartData;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CartService {

    @Autowired
    private CartDoMapper cartDoMapper;

    public Cart getByPK(String key){
        CartDo entity = cartDoMapper.selectByPrimaryKey(key);
        return CartData.convert(entity,new Cart());
    }

    public Map<String,Object> getSimpleMapByPK(String key){
        return CartData.buildMap(getByPK(key));
    }

    public Long getCountByExample(CartDoExample example){
        return cartDoMapper.countByExample(example);
    }

    public List<Cart> getListByExample(CartDoExample example){
        List<Cart> objects = new ArrayList<>();
        for(CartDo cartDo : cartDoMapper.selectByExample(example)){
            objects.add(CartData.convert(cartDo,new Cart()));
        }
        return objects;
    }

    /*
    * 可以优化，使用example进行条件查询，与数据库一次交互就可查询成功
    * */
    public List<Cart> getListByRelatedId(List<String> idList){
        List<Cart> carts = new ArrayList<>();
        List<CartDo> cartDos = new ArrayList<>();
        for(String cartDoKey : idList){
            cartDos.add(cartDoMapper.selectByPrimaryKey(cartDoKey));
        }
        for(CartDo cartDo : cartDos){
            carts.add(CartData.convert(cartDo,new Cart()));
        }
        return carts;
    }

    public List<Map<String,Object>> getMapListByMap(Map<String,Object> map){
        Long page=0l;
        Integer rows = 0;
        CartDoExample example = new CartDoExample();
        CartDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("userId")!=null){
            criteria.andUserIdEqualTo((String)map.get("userId"));
        }
        if(map.get("productId")!=null){
            criteria.andProductIdEqualTo((String)map.get("productId"));
        }
        if(map.get("number")!=null){
            criteria.andNumberEqualTo((Integer)map.get("number"));
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


    public List<Map<String,Object>> getMapListByExample(CartDoExample example){
        List<Cart> carts = getListByExample(example);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Cart cart : carts){
            maps.add(CartData.buildMap(cart));
        }
        return maps;
    }

    public List<Map<String,Object>> getMapListByKeySet(List<String> idList){
        List<Cart> carts = getListByRelatedId(idList);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Cart cart : carts){
            maps.add(CartData.buildMap(cart));
        }
        return maps;
    }



    public Cart post(Cart postEntity){
        postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
        try{
            cartDoMapper.insertSelective(CartData.convert(postEntity,new CartDo()));
        }catch (DuplicateKeyException e){
            System.err.println("主键重复错误");
        }
        return getByPK(postEntity.getId());
    }

    /*
    * 用map进行插入,返回map
    * */
    public Map<String,Object> post(Map<String,Object> map){
        Cart cart = CartData.parseEntity(map);
        return CartData.buildMap(post(cart));
    }

    public List<Map<String,Object>> postList(List<Cart> carts){
        List<Map<String,Object>> lists = new ArrayList<>();
        for(Cart cart : carts){
            lists.add(CartData.buildMap(post(cart)));
        }
        return lists;
    }

    public Cart update(Cart cart){
        Integer i = cartDoMapper.updateByPrimaryKeySelective(CartData.convert(cart,new CartDo()));
        if(i<1){
            System.err.println("不存在该项值");
        }
        return CartData.convert(cartDoMapper.selectByPrimaryKey(cart.getId()),new Cart());
    }

    public Map<String, Object> updateMapping(Map<String,Object> updateMapper) {
        Cart cart = CartData.parseEntity(updateMapper);
        cart = update(cart);
        return CartData.buildMap(cart);
    }
    public List<Map<String,Object>> updateList(List<Cart> carts){
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Cart cart : carts){
            maps.add(updateMapping(CartData.buildMap(cart)));
        }
        return maps;
    }

    public Map<String,Object> updateByMap(Map<String,Object> map){
        CartDoExample example = new CartDoExample();
        Cart cart = new Cart(null,null,null,null);
        CartDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
            cart.setId((String)map.get("id"));
        }
        if(map.get("userId")!=null){
            criteria.andUserIdEqualTo((String)map.get("userId"));
            cart.setUserId((String)map.get("userId"));
        }
        if(map.get("productId")!=null){
            criteria.andProductIdEqualTo((String)map.get("productId"));
            cart.setProductId((String)map.get("productId"));
        }
        if(map.get("number")!=null){
            criteria.andNumberEqualTo((Integer)map.get("number"));
            cart.setNumber((Integer)map.get("number"));
        }
        cartDoMapper.updateByExampleSelective(CartData.convert(cart,new CartDo()),example);
        return null;
    }

    /*
    * 根据example进行更新
    * */
    public Integer updateByExample(Cart cart,CartDoExample example){
        Integer i = cartDoMapper.updateByExampleSelective(CartData.convert(cart,new CartDo()),example);
        return i;
    }
    public Integer updateByExample(Map<String,Object> map,CartDoExample example){
        Cart cart = CartData.parseEntity(map);
        Integer i = cartDoMapper.updateByExampleSelective(CartData.convert(cart,new CartDo()),example);
        return i;
    }



//删除
    public Integer delete(String key){
        return cartDoMapper.deleteByPrimaryKey(key);
    }

    public Integer deleteList(List<String> keys){
        AtomicInteger count =new AtomicInteger();
        for(String key : keys){
            count.addAndGet(delete(key));
        }
        return count.get();
    }

    public Map<String,Object> deleteByMap(Map<String,Object> map){
        CartDoExample example = new CartDoExample();
        Cart cart = new Cart(null,null,null,null);
        CartDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
            cart.setId((String)map.get("id"));
        }
        if(map.get("userId")!=null){
            criteria.andUserIdEqualTo((String)map.get("userId"));
            cart.setUserId((String)map.get("userId"));
        }
        if(map.get("productId")!=null){
            criteria.andProductIdEqualTo((String)map.get("productId"));
            cart.setProductId((String)map.get("productId"));
        }
        if(map.get("number")!=null){
            criteria.andNumberEqualTo((Integer)map.get("number"));
            cart.setNumber((Integer)map.get("number"));
        }
        cartDoMapper.deleteByExample(example);
        return null;
    }

    //根据example删除
    public Integer deleteByExample(CartDoExample example){
        Integer i = cartDoMapper.deleteByExample(example);
        return i;
    }
}
