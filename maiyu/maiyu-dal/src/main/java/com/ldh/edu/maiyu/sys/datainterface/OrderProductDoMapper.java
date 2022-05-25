package com.ldh.edu.maiyu.sys.datainterface;

import com.ldh.edu.maiyu.sys.dataobject.OrderProductDo;
import com.ldh.edu.maiyu.sys.dataobject.OrderProductDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductDoMapper {
    long countByExample(OrderProductDoExample example);

    int deleteByExample(OrderProductDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderProductDo record);

    int insertSelective(OrderProductDo record);

    List<OrderProductDo> selectByExample(OrderProductDoExample example);

    OrderProductDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderProductDo record, @Param("example") OrderProductDoExample example);

    int updateByExample(@Param("record") OrderProductDo record, @Param("example") OrderProductDoExample example);

    int updateByPrimaryKeySelective(OrderProductDo record);

    int updateByPrimaryKey(OrderProductDo record);
}