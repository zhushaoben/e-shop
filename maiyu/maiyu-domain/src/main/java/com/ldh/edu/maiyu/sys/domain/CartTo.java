package com.ldh.edu.maiyu.sys.domain;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartTo {
    private String id;

    private String userId;

    private String productId;

    private Integer number;
}
