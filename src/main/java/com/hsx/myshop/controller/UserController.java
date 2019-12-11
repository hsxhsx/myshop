package com.hsx.myshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsx.myshop.dto.UserDto;
import com.hsx.myshop.entity.User;
import com.hsx.myshop.response.Response;
import com.hsx.myshop.response.ResponseCode;
import com.hsx.myshop.response.ResponseMsg;
import com.hsx.myshop.service.UserServiceimpl;
import com.hsx.myshop.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "用户接口")
@Log4j2
@RestController
public class UserController {

    @Autowired
    private UserServiceimpl userService;

    @ApiOperation(value = "获取用户", notes = "根据id查询用户信息")
    @ApiImplicitParam(name = "name", value = "用户name", required=true, dataType = "String")
    @RequestMapping(value = "/order", method = RequestMethod.GET )
    public Response login(@RequestBody UserDto user) {
        if(!Validator.isMobile(user.getUsername())) {
            Response response = new Response();
            response.setMsg(ResponseMsg.FAILURE.toString());
            response.setCode(ResponseCode.FAILURE.toString());
            response.setData("用户名是非法手机号");
            return response;
        }
        Response response = new Response();
        response.setMsg("success");
        response.setCode("200");
        PageHelper.startPage(user.getPage(), user.getPageSize());
        List<User> list = userService.findAll();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        response.setData(pageInfo);
        return response;
    }

    @ApiOperation(value = "获取用户", notes = "根据id查询用户信息")
    @ApiImplicitParam(name = "name", value = "用户name", required=true, dataType = "String")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST )
    public Response addUser(@RequestBody UserDto user) {
        if(!Validator.isMobile(user.getUsername())) {
            Response response = new Response();
            response.setMsg(ResponseMsg.FAILURE.toString());
            response.setCode(ResponseCode.FAILURE.toString());
            response.setData("用户名是非法手机号");
            return response;
        }
        return userService.addUser(user);
    }
}
