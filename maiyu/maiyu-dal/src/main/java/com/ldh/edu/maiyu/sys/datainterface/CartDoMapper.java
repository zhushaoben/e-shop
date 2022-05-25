package com.ldh.edu.maiyu.sys.datainterface;

import com.ldh.edu.maiyu.sys.dataobject.CartDo;
import com.ldh.edu.maiyu.sys.dataobject.CartDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDoMapper {
    long countByExample(CartDoExample example);

    int deleteByExample(CartDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(CartDo record);

    int insertSelective(CartDo record);

    List<CartDo> selectByExample(CartDoExample example);

    CartDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CartDo record, @Param("example") CartDoExample example);

    int updateByExample(@Param("record") CartDo record, @Param("example") CartDoExample example);

    int updateByPrimaryKeySelective(CartDo record);

    int updateByPrimaryKey(CartDo record);
}