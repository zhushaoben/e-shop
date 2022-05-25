package com.ldh.edu.maiyu.sys.facade;

import com.ldh.edu.maiyu.sys.domain.ProductFacade;
import com.ldh.edu.maiyu.sys.domain.ProductTo;
import com.ldh.edu.maiyu.sys.model.Product;
import com.ldh.edu.maiyu.sys.model.ProductDomain;
import com.ldh.edu.maiyu.sys.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/facade/sys/product")
public class ProductFacadeImpl implements ProductFacade {
    @Autowired
    private ProductService productService;

    @RequestMapping(value="single",method= RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public ProductTo getSingleByPK(String id) {
        Product entity = productService.getByPK(id);
        return entity!=null? ProductDomain.convert(entity,new ProductTo()):null;
    }

    @RequestMapping(value="",method = RequestMethod.POST)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void post(ProductTo cartTo) {
        if(cartTo!=null){
            Product entity = ProductDomain.convert(cartTo,new Product());
            productService.post(entity);
        }else{
            System.err.println("cartTo对象不能为空!!!");
        }
    }

    @RequestMapping(value="",method=RequestMethod.PATCH)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void patch(ProductTo entityTo) {
        if(entityTo!=null){
            Product entity = ProductDomain.convert(entityTo,new Product());
            productService.update(entity);
        }else{
            System.err.println("cartTo对象不能为空！！！");
        }
    }

    @RequestMapping(value="",method=RequestMethod.DELETE)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void delete(String id) {
        productService.delete(id);
    }

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public List<ProductTo> getList(ArrayList<String> idList) {
        List<Product> entitys = productService.getListByRelatedId(idList);
        List<ProductTo> entityTos = new ArrayList<>();
        for(Product entity : entitys){
            entityTos.add(ProductDomain.convert(entity,new ProductTo()));
        }
        return entityTos;
    }
}
