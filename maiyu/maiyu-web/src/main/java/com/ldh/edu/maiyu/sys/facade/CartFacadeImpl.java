package com.ldh.edu.maiyu.sys.facade;

import com.ldh.edu.maiyu.sys.domain.CartFacade;
import com.ldh.edu.maiyu.sys.domain.CartTo;
import com.ldh.edu.maiyu.sys.model.Cart;
import com.ldh.edu.maiyu.sys.model.CartDomain;
import com.ldh.edu.maiyu.sys.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/facade/sys/cart")
public class CartFacadeImpl implements CartFacade {

    @Autowired
    private CartService cartService;

    @RequestMapping(value="single",method= RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public CartTo getSingleByPK(String id) {
        Cart entity = cartService.getByPK(id);
        return entity!=null? CartDomain.convert(entity,new CartTo()):null;
    }

    @RequestMapping(value="",method = RequestMethod.POST)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void post(CartTo cartTo) {
        if(cartTo!=null){
            Cart entity = CartDomain.convert(cartTo,new Cart());
            cartService.post(entity);
        }else{
            System.err.println("cartTo对象不能为空!!!");
        }
    }

    @RequestMapping(value="",method=RequestMethod.PATCH)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void patch(CartTo entityTo) {
        if(entityTo!=null){
            Cart entity = CartDomain.convert(entityTo,new Cart());
            cartService.update(entity);
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
        cartService.delete(id);
    }

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public List<CartTo> getList(ArrayList<String> idList) {
        List<Cart> entitys = cartService.getListByRelatedId(idList);
        List<CartTo> entityTos = new ArrayList<>();
        for(Cart entity : entitys){
            entityTos.add(CartDomain.convert(entity,new CartTo()));
        }
        return entityTos;
    }
}
