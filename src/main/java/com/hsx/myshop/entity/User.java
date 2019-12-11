package com.hsx.myshop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Table(name = "tb_user")
@ApiModel(value="user对象",description="用户对象user")
public class User extends BaseEntity implements Serializable{

    @ApiModelProperty("编号Id")
    @NotBlank(message = "编号不能为空")
    @Id
    private Long id;//编号

    @Column(name = "`username`")
    private String username;//用户名
    private String password;//密码

}
