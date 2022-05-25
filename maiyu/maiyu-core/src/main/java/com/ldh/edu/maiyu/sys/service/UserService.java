package com.ldh.edu.maiyu.sys.service;

import com.ldh.edu.maiyu.sys.datainterface.UserDoMapper;
import com.ldh.edu.maiyu.sys.dataobject.UserDo;
import com.ldh.edu.maiyu.sys.dataobject.UserDoExample;
import com.ldh.edu.maiyu.sys.model.Product;
import com.ldh.edu.maiyu.sys.model.User;
import com.ldh.edu.maiyu.sys.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserService {
    @Autowired
    private UserDoMapper userDoMapper;

    public User getByPK(String key){
        UserDo entity = userDoMapper.selectByPrimaryKey(key);
        return UserData.convert(entity,new User());
    }

    public Map<String,Object> getSimpleMapByPK(String key){
        return UserData.buildMap(getByPK(key));
    }

    public Long getCountByExample(UserDoExample example){
        return userDoMapper.countByExample(example);
    }

    public List<User> getListByExample(UserDoExample example){
        List<User> objects = new ArrayList<>();
        for(UserDo userDo : userDoMapper.selectByExample(example)){
            objects.add(UserData.convert(userDo,new User()));
        }
        return objects;
    }

    /*
     * 可以优化，使用example进行条件查询，与数据库一次交互就可查询成功
     * */
    public List<User> getListByRelatedId(List<String> idList){
        List<User> users = new ArrayList<>();
        List<UserDo> userDos = new ArrayList<>();
        for(String userDoKey : idList){
            userDos.add(userDoMapper.selectByPrimaryKey(userDoKey));
        }
        for(UserDo userDo : userDos){
            users.add(UserData.convert(userDo,new User()));
        }
        return users;
    }

    public List<Map<String,Object>> getMapListByExample(UserDoExample example){
        List<User> users = getListByExample(example);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(User user : users){
            maps.add(UserData.buildMap(user));
        }
        return maps;
    }

    public List<Map<String,Object>> getMapListByKeySet(List<String> idList){
        List<User> users = getListByRelatedId(idList);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(User user : users){
            maps.add(UserData.buildMap(user));
        }
        return maps;
    }


    public List<Map<String,Object>> getMapListByMap(Map<String,Object> map){
        Long page=0l;
        Integer rows = 0;
        UserDoExample example = new UserDoExample();
        UserDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("username")!=null){
            criteria.andUsernameEqualTo((String)map.get("username"));
        }
        if(map.get("password")!=null){
            criteria.andPasswordEqualTo((String)map.get("password"));
        }
        if(map.get("address")!=null){
            criteria.andAddressEqualTo((String)map.get("address"));
        }
        if(map.get("phone")!=null){
            criteria.andPhoneEqualTo((String)map.get("phone"));
        }
        if(map.get("email")!=null){
            criteria.andEmailEqualTo((String)map.get("email"));
        }
        if(map.get("type")!=null){
            criteria.andTypeEqualTo((Integer)map.get("type"));
        }
        if(map.get("name")!=null){
            criteria.andNameLike("%"+(String)map.get("name")+"%");
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


    public User post(User postEntity){
        postEntity.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
        try{
            userDoMapper.insertSelective(UserData.convert(postEntity,new UserDo()));
        }catch (DuplicateKeyException e){
            System.err.println("主键重复错误");
        }
        return getByPK(postEntity.getId());
    }

    /*
     * 用map进行插入,返回map
     * */
    public Map<String,Object> post(Map<String,Object> map){
        User user = UserData.parseEntity(map);
        return UserData.buildMap(post(user));
    }

    public List<Map<String,Object>> postList(List<User> users){
        List<Map<String,Object>> lists = new ArrayList<>();
        for(User user : users){
            lists.add(UserData.buildMap(post(user)));
        }
        return lists;
    }

    public User update(User user){
        Integer i = userDoMapper.updateByPrimaryKeySelective(UserData.convert(user,new UserDo()));
        if(i<1){
            System.err.println("不存在该项值");
        }
        return UserData.convert(userDoMapper.selectByPrimaryKey(user.getId()),new User());
    }

    public Map<String, Object> updateMapping(Map<String,Object> updateMapper) {
        User user = UserData.parseEntity(updateMapper);
        user = update(user);
        return UserData.buildMap(user);
    }
    public List<Map<String,Object>> updateList(List<User> users){
        List<Map<String,Object>> maps = new ArrayList<>();
        for(User user : users){
            maps.add(updateMapping(UserData.buildMap(user)));
        }
        return maps;
    }
    /*
     * 根据example进行更新
     * */
    public Integer updateByExample(User user,UserDoExample example){
        Integer i = userDoMapper.updateByExampleSelective(UserData.convert(user,new UserDo()),example);
        return i;
    }
    public Integer updateByExample(Map<String,Object> map,UserDoExample example){
        User user = UserData.parseEntity(map);
        Integer i = userDoMapper.updateByExampleSelective(UserData.convert(user,new UserDo()),example);
        return i;
    }

    public Map<String,Object> updateByMap(User user, Map<String,Object> map){
        UserDoExample example = new UserDoExample();
        UserDoExample.Criteria criteria = example.or();
        if(map.get("id")!=null){
            criteria.andIdEqualTo((String)map.get("id"));
        }
        if(map.get("username")!=null){
            criteria.andUsernameEqualTo((String)map.get("username"));
        }
        if(map.get("password")!=null){
            criteria.andPasswordEqualTo((String)map.get("password"));
        }
        if(map.get("address")!=null){
            criteria.andAddressEqualTo((String)map.get("address"));
        }
        if(map.get("phone")!=null){
            criteria.andPhoneEqualTo((String)map.get("phone"));
        }
        if(map.get("email")!=null){
            criteria.andEmailEqualTo((String)map.get("email"));
        }
        if(map.get("type")!=null){
            criteria.andTypeEqualTo((Integer)map.get("type"));
        }
        if(map.get("name")!=null){
            criteria.andNameLike("%"+(String)map.get("name")+"%");
        }
        userDoMapper.updateByExampleSelective(UserData.convert(user,new UserDo()),example);
        return null;
    }

    //删除
    public Integer delete(String key){
        return userDoMapper.deleteByPrimaryKey(key);
    }

    public Integer deleteList(List<String> keys){
        AtomicInteger count =new AtomicInteger();
        for(String key : keys){
            count.addAndGet(delete(key));
        }
        return count.get();
    }
    //根据example删除
    public Integer deleteByExample(UserDoExample example){
        Integer i = userDoMapper.deleteByExample(example);
        return i;
    }
}
