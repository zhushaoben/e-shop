package com.ldh.edu.maiyu.sys.model;


import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import com.ldh.edu.maiyu.sys.dataobject.CartDo;
import com.ldh.edu.maiyu.sys.domain.CartTo;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Mapper(
    value={"data","domain"},
        name = {"CartData","CartDomain"}
)
@MapperConvert(
        value = {"data","domain"},type = {CartDo.class, CartTo.class}
)
public class Cart implements Serializable {

    private String id;

    private String userId;

    private String productId;

    private Integer number;

}
