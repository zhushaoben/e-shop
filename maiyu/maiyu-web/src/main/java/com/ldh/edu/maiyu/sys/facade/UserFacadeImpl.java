package com.ldh.edu.maiyu.sys.facade;


import com.ldh.edu.maiyu.sys.domain.UserFacade;
import com.ldh.edu.maiyu.sys.domain.UserTo;
import com.ldh.edu.maiyu.sys.model.User;
import com.ldh.edu.maiyu.sys.model.UserDomain;
import com.ldh.edu.maiyu.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/facade/sys/user")
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserService userService;

    @RequestMapping(value="single",method= RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public UserTo getSingleByPK(String id) {
        User entity = userService.getByPK(id);
        return entity!=null? UserDomain.convert(entity,new UserTo()):null;
    }

    @RequestMapping(value="",method = RequestMethod.POST)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void post(UserTo cartTo) {
        if(cartTo!=null){
            User entity = UserDomain.convert(cartTo,new User());
            userService.post(entity);
        }else{
            System.err.println("cartTo对象不能为空!!!");
        }
    }

    @RequestMapping(value="",method=RequestMethod.PATCH)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public void patch(UserTo entityTo) {
        if(entityTo!=null){
            User entity = UserDomain.convert(entityTo,new User());
            userService.update(entity);
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
        userService.delete(id);
    }

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @Transactional(
            rollbackFor = Exception.class
    )
    @Override
    public List<UserTo> getList(ArrayList<String> idList) {
        List<User> entitys = userService.getListByRelatedId(idList);
        List<UserTo> entityTos = new ArrayList<>();
        for(User entity : entitys){
            entityTos.add(UserDomain.convert(entity,new UserTo()));
        }
        return entityTos;
    }
}
