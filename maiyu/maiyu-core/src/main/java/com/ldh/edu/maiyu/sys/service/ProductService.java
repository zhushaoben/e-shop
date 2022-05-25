package com.ldh.edu.maiyu.sys.service;

import com.ldh.edu.maiyu.sys.datainterface.ProductDoMapper;
import com.ldh.edu.maiyu.sys.dataobject.OrderDo;
import com.ldh.edu.maiyu.sys.dataobject.ProductDo;
import com.ldh.edu.maiyu.sys.dataobject.ProductDoExample;
import com.ldh.edu.maiyu.sys.model.OrderProduct;
import com.ldh.edu.maiyu.sys.model.Product;
import com.ldh.edu.maiyu.sys.model.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService {
    @Autowired
    private ProductDoMapper productDoMapper;

    public Product getByPK(String key){
        ProductDo entity = productDoMapper.selectByPrimaryKey(key);
        return ProductData.convert(entity,new Product());
    }

    public Map<String,Object> getSimpleMapByPK(String key){
        return ProductData.buildMap(getByPK(key));
    }

    public Long getCountByExample(ProductDoExample example){
        return productDoMapper.countByExample(example);
    }

    public List<Product> getListByExample(ProductDoExample example){
        List<Product> objects = new ArrayList<>();
        for(ProductDo productDo : productDoMapper.selectByExample(example)){
            objects.add(ProductData.convert(productDo,new Product()));
        }
        return objects;
    }

    /*
     * 可以优化，使用example进行条件查询，与数据库一次交互就可查询成功
     * */
    public List<Product> getListByRelatedId(List<String> idList){
        List<Product> products = new ArrayList<>();
        List<ProductDo> productDos = new ArrayList<>();
        for(String productDoKey : idList){
            productDos.add(productDoMapper.selectByPrimaryKey(productDoKey));
        }
        for(ProductDo productDo : productDos){
            products.add(ProductData.convert(productDo,new Product()));
        }
        return products;
    }

    public List<Map<String,Object>> getMapListByExample(ProductDoExample example){
        List<Product> products = getListByExample(example);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Product product : products){
            maps.add(ProductData.buildMap(product));
        }
        return maps;
    }

    public List<Map<String,Object>> getMapListByKeySet(List<String> idList){
        List<Product> products = getListByRelatedId(idList);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Product product : products){
            maps.add(ProductData.buildMap(product));
        }
        return maps;
    }

    public List<Map<String,Object>> getMapListByMap(Map<String,Object> map){
        Long page=0l;
        Integer rows = 0;
        ProductDoExample example = new ProductDoExample();
        ProductDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("busmanId")!=null){
            criteria.andBusmanIdEqualTo((String)map.get("busmanId"));
        }
        if(map.get("name")!=null){
            criteria.andNameLike("%" + (String)map.get("name")+"%");
        }
        if(map.get("stock")!=null){
            criteria.andStockEqualTo((Integer) map.get("stock"));
        }
        if(map.get("sell")!=null){
            criteria.andSellEqualTo((Integer) map.get("sell"));
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



    public Product post(Product postEntity){
        postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
        try{
            productDoMapper.insertSelective(ProductData.convert(postEntity,new ProductDo()));
        }catch (DuplicateKeyException e){
            System.err.println("主键重复错误");
        }
        return getByPK(postEntity.getId());
    }

    /*
     * 用map进行插入,返回map
     * */
    public Map<String,Object> post(Map<String,Object> map){
        Product product = ProductData.parseEntity(map);
        return ProductData.buildMap(post(product));
    }

    public List<Map<String,Object>> postList(List<Product> products){
        List<Map<String,Object>> lists = new ArrayList<>();
        for(Product product : products){
            lists.add(ProductData.buildMap(post(product)));
        }
        return lists;
    }

    public Product update(Product product){
        Integer i = productDoMapper.updateByPrimaryKeySelective(ProductData.convert(product,new ProductDo()));
        if(i<1){
            System.err.println("不存在该项值");
        }
        return ProductData.convert(productDoMapper.selectByPrimaryKey(product.getId()),new Product());
    }

    public Map<String, Object> updateMapping(Map<String,Object> updateMapper) {
        Product product = ProductData.parseEntity(updateMapper);
        product = update(product);
        return ProductData.buildMap(product);
    }
    public List<Map<String,Object>> updateList(List<Product> products){
        List<Map<String,Object>> maps = new ArrayList<>();
        for(Product product : products){
            maps.add(updateMapping(ProductData.buildMap(product)));
        }
        return maps;
    }
    /*
     * 根据example进行更新
     * */
    public Integer updateByExample(Product product,ProductDoExample example){
        Integer i = productDoMapper.updateByExampleSelective(ProductData.convert(product,new ProductDo()),example);
        return i;
    }
    public Integer updateByExample(Map<String,Object> map,ProductDoExample example){
        Product product = ProductData.parseEntity(map);
        Integer i = productDoMapper.updateByExampleSelective(ProductData.convert(product,new ProductDo()),example);
        return i;
    }
    public Map<String,Object> updateByMap(Product product, Map<String,Object> map){

        ProductDoExample example = new ProductDoExample();
        ProductDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("busmanId")!=null){
            criteria.andBusmanIdEqualTo((String)map.get("busmanId"));
        }
        if(map.get("name")!=null){
            criteria.andNameLike("%" + (String)map.get("name")+"%");
        }
        if(map.get("stock")!=null){
            criteria.andStockEqualTo((Integer) map.get("stock"));
        }
        if(map.get("sell")!=null){
            criteria.andSellEqualTo((Integer) map.get("sell"));
        }
        productDoMapper.updateByExampleSelective(ProductData.convert(product,new ProductDo()),example);
        return null;
    }



    //删除
    public Integer delete(String key){
        return productDoMapper.deleteByPrimaryKey(key);
    }

    public Integer deleteList(List<String> keys){
        AtomicInteger count =new AtomicInteger();
        for(String key : keys){
            count.addAndGet(delete(key));
        }
        return count.get();
    }
    //根据example删除
    public Integer deleteByExample(ProductDoExample example){
        Integer i = productDoMapper.deleteByExample(example);
        return i;
    }
}
