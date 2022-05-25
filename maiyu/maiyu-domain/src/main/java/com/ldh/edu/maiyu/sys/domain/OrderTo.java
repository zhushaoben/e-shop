package com.ldh.edu.maiyu.sys.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderTo {

    private String id;

    private String customerId;

    private String busmanId;

    private String address;

    private BigDecimal totalPrice;

    private Date gmtCreate;

    private Integer state;

}
