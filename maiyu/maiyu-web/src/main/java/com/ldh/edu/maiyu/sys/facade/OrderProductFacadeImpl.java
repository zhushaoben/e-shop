package com.ldh.edu.maiyu.sys.facade;

import com.ldh.edu.maiyu.sys.domain.OrderProductFacade;
import com.ldh.edu.maiyu.sys.domain.OrderProductTo;
import com.ldh.edu.maiyu.sys.model.OrderProduct;
import com.ldh.edu.maiyu.sys.model.OrderProductDomain;
import com.ldh.edu.maiyu.sys.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/facade/sys/orderProduct")
public class OrderProductFacadeImpl implements OrderProductFacade {
    @Autowired
    private OrderProductService orderProductService;

    @RequestMapping(value="single",method= RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public OrderProductTo getSingleByPK(String id) {
        OrderProduct entity = orderProductService.getByPK(id);
        return entity!=null? OrderProductDomain.convert(entity,new OrderProductTo()):null;
    }

    @RequestMapping(value="",method = RequestMethod.POST)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void post(OrderProductTo cartTo) {
        if(cartTo!=null){
            OrderProduct entity = OrderProductDomain.convert(cartTo,new OrderProduct());
            orderProductService.post(entity);
        }else{
            System.err.println("cartTo对象不能为空!!!");
        }
    }

    @RequestMapping(value="",method=RequestMethod.PATCH)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void patch(OrderProductTo entityTo) {
        if(entityTo!=null){
            OrderProduct entity = OrderProductDomain.convert(entityTo,new OrderProduct());
            orderProductService.update(entity);
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
        orderProductService.delete(id);
    }

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public List<OrderProductTo> getList(ArrayList<String> idList) {
        List<OrderProduct> entitys = orderProductService.getListByRelatedId(idList);
        List<OrderProductTo> entityTos = new ArrayList<>();
        for(OrderProduct entity : entitys){
            entityTos.add(OrderProductDomain.convert(entity,new OrderProductTo()));
        }
        return entityTos;
    }
}
