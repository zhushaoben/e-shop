package com.ldh.edu.maiyu.sys.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductTo {
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
