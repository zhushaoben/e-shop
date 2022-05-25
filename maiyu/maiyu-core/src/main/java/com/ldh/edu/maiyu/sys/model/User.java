package com.ldh.edu.maiyu.sys.model;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperConvert;
import com.ldh.edu.maiyu.sys.dataobject.CartDo;
import com.ldh.edu.maiyu.sys.dataobject.UserDo;
import com.ldh.edu.maiyu.sys.domain.CartTo;
import com.ldh.edu.maiyu.sys.domain.UserTo;
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
        name = {"UserData","UserDomain"}
)
@MapperConvert(
        value = {"data","domain"},type = {UserDo.class, UserTo.class}
)
public class User implements Serializable {

    private String id;

    private String username;

    private String name;

    private String password;

    private String address;

    private String phone;

    private String email;

    private Integer type;

    private BigDecimal money;

    private Date gmtCreate;

}
