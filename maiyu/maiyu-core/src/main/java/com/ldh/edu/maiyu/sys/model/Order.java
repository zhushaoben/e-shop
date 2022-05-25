package com.ldh.edu.maiyu.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import com.ldh.edu.maiyu.sys.dataobject.CartDo;
import com.ldh.edu.maiyu.sys.dataobject.OrderDo;
import com.ldh.edu.maiyu.sys.domain.CartTo;
import com.ldh.edu.maiyu.sys.domain.OrderTo;
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
        name = {"OrderData","OrderDomain"}
)
@MapperConvert(
        value = {"data","domain"},type = {OrderDo.class, OrderTo.class}
)
public class Order implements Serializable {

    private String id;

    private String customerId;

    private String busmanId;

    private String address;

    private BigDecimal totalPrice;

    private Date gmtCreate;

    private Integer state;


}
