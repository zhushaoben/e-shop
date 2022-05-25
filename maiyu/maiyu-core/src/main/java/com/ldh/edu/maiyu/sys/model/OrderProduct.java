package com.ldh.edu.maiyu.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import com.ldh.edu.maiyu.sys.dataobject.CartDo;
import com.ldh.edu.maiyu.sys.dataobject.OrderProductDo;
import com.ldh.edu.maiyu.sys.domain.CartTo;
import com.ldh.edu.maiyu.sys.domain.OrderProductTo;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Mapper(
        value={"data","domain"},
        name = {"OrderProductData","OrderProductDomain"}
)
@MapperConvert(
        value = {"data","domain"},type = {OrderProductDo.class, OrderProductTo.class}
)
public class OrderProduct implements Serializable {

    private String id;

    private String productId;

    private String orderId;

    private Integer number;

}
