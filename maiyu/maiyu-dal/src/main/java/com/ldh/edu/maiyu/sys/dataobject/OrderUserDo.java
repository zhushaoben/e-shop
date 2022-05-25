package com.ldh.edu.maiyu.sys.dataobject;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderUserDo {
    private String cardId;
    private String productId;
    private BigDecimal price;
    private Integer number;
    private String busmanId;
}
