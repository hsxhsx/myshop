package com.hsx.myshop.dao;

import com.hsx.myshop.entity.BaseEntity;
import com.hsx.myshop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "userMapper")
public interface userMapper extends BaseMapper<User> {
    List<User> findAll();

}
