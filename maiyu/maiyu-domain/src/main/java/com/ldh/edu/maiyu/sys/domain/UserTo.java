package com.ldh.edu.maiyu.sys.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserTo {
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
