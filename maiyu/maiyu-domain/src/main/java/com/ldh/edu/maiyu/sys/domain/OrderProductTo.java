package com.ldh.edu.maiyu.sys.domain;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductTo {
    private String id;

    private String productId;

    private String orderId;

    private Integer number;

}
