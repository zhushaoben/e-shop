package com.ldh.edu.maiyu.sys.datainterface;

import com.ldh.edu.maiyu.sys.dataobject.ProductDo;
import com.ldh.edu.maiyu.sys.dataobject.ProductDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDoMapper {
    long countByExample(ProductDoExample example);

    int deleteByExample(ProductDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProductDo record);

    int insertSelective(ProductDo record);

    List<ProductDo> selectByExample(ProductDoExample example);

    ProductDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProductDo record, @Param("example") ProductDoExample example);

    int updateByExample(@Param("record") ProductDo record, @Param("example") ProductDoExample example);

    int updateByPrimaryKeySelective(ProductDo record);

    int updateByPrimaryKey(ProductDo record);
}