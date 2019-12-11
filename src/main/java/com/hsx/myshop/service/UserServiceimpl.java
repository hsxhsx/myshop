package com.hsx.myshop.service;

import com.hsx.myshop.dto.UserDto;
import com.hsx.myshop.entity.User;
import com.hsx.myshop.response.Response;
import com.hsx.myshop.response.ResponseCode;
import com.hsx.myshop.response.ResponseMsg;
import com.hsx.myshop.util.uuid.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@CacheConfig(cacheNames="User") // 本类内方法指定使用缓存时，默认的名称就是userCach
@Transactional
public class UserServiceimpl implements UserService {

    @Autowired
    private com.hsx.myshop.dao.userMapper userMapper;

    @Override
    @Cacheable(key = "getMethodName()")
    public List<User> findAll() {
        try {
            List<User>  list = userMapper.selectAll();
            return list;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @CachePut(value = "#user",key = "#userDto.username")
    public Response  addUser(UserDto userDto) {
        Response response = new Response();
        try {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            long id = SnowFlake.nextId();
            user.setId(id);
            userMapper.insert(user);
            response.setCode(ResponseCode.SUCCESS.toString());
            response.setMsg(ResponseMsg.SUCCESS.toString());
        } catch (Exception e) {
            response.setCode(ResponseCode.FAILURE.toString());
            response.setMsg(ResponseMsg.FAILURE.toString());
            response.setData(e.toString());
        }
        return response;

    }

}
