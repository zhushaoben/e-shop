package com.ldh.edu.maiyu.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import com.ldh.edu.maiyu.sys.dataobject.CartDo;
import com.ldh.edu.maiyu.sys.dataobject.ProductDo;
import com.ldh.edu.maiyu.sys.domain.CartTo;
import com.ldh.edu.maiyu.sys.domain.ProductTo;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Mapper(
        value={"data","domain"},
        name = {"ProductData","ProductDomain"}
)
@MapperConvert(
        value = {"data","domain"},type = {ProductDo.class, ProductTo.class}
)
public class Product implements Serializable {

    private String id;

    private String busmanId;

    private BigDecimal price;

    private String name;

    private Integer stock;

    private Integer sell;

    private String imgPath;

    private String details;

    private Date gmtUpdate;

    private Date gmtCreate;

}
