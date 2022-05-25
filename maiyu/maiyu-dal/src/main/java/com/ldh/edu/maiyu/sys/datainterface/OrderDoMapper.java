package com.ldh.edu.maiyu.sys.datainterface;

import com.ldh.edu.maiyu.sys.dataobject.OrderDo;
import com.ldh.edu.maiyu.sys.dataobject.OrderDoExample;
import java.util.List;
import java.util.Map;

import com.ldh.edu.maiyu.sys.dataobject.OrderUserDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDoMapper {
    long countByExample(OrderDoExample example);

    int deleteByExample(OrderDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderDo record);

    int insertSelective(OrderDo record);

    List<OrderDo> selectByExample(OrderDoExample example);

    OrderDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderDo record, @Param("example") OrderDoExample example);

    int updateByExample(@Param("record") OrderDo record, @Param("example") OrderDoExample example);

    int updateByPrimaryKeySelective(OrderDo record);

    int updateByPrimaryKey(OrderDo record);

//    List<Map<String,Object>> getCartBusManByUserId(String userId);
    List<OrderUserDo> getOrderUserByUserId(String userId);

}