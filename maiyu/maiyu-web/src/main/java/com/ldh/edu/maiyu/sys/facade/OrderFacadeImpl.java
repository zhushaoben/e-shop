package com.ldh.edu.maiyu.sys.facade;

import com.ldh.edu.maiyu.sys.domain.OrderFacade;
import com.ldh.edu.maiyu.sys.domain.OrderTo;
import com.ldh.edu.maiyu.sys.model.Order;
import com.ldh.edu.maiyu.sys.model.OrderDomain;
import com.ldh.edu.maiyu.sys.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/facade/sys/order")
public class OrderFacadeImpl implements OrderFacade {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value="single",method= RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public OrderTo getSingleByPK(String id) {
        Order entity = orderService.getByPK(id);
        return entity!=null? OrderDomain.convert(entity,new OrderTo()):null;
    }

    @RequestMapping(value="",method = RequestMethod.POST)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void post(OrderTo cartTo) {
        if(cartTo!=null){
            Order entity = OrderDomain.convert(cartTo,new Order());
            orderService.post(entity);
        }else{
            System.err.println("cartTo对象不能为空!!!");
        }
    }

    @RequestMapping(value="",method=RequestMethod.PATCH)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void patch(OrderTo entityTo) {
        if(entityTo!=null){
            Order entity = OrderDomain.convert(entityTo,new Order());
            orderService.update(entity);
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
        orderService.delete(id);
    }

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public List<OrderTo> getList(ArrayList<String> idList) {
        List<Order> entitys = orderService.getListByRelatedId(idList);
        List<OrderTo> entityTos = new ArrayList<>();
        for(Order entity : entitys){
            entityTos.add(OrderDomain.convert(entity,new OrderTo()));
        }
        return entityTos;
    }

}
