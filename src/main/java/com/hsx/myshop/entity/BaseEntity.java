package com.hsx.myshop.entity;

import lombok.Data;

@Data
public class BaseEntity {

    private int page = 1;

    private int pageSize = 10;
}
