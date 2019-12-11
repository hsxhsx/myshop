package com.hsx.myshop.dto;

import com.hsx.myshop.entity.BaseEntity;
import lombok.Data;

@Data
public class UserDto extends BaseEntity {
    private String username;//用户名
    private String password;//密码

}
