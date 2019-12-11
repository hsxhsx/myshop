package com.hsx.myshop.controller;

import com.hsx.myshop.controller.webSocketServer.WebSocketServer;
import com.hsx.myshop.response.Response;
import com.hsx.myshop.response.ResponseCode;
import com.hsx.myshop.response.ResponseMsg;
import com.hsx.myshop.entity.LoginDto;
import com.hsx.myshop.config.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(value = "登录接口")
@Log4j2
@RestController
public class LoginController {

    @Autowired
    private WebSocketServer webSocketServer;

    @ApiOperation(value = "登录", notes = "用户名密码登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET )
    public Response login(@RequestParam(value = "name") String name, @RequestParam(value = "psw") String psw) {

        String token = JwtUtil.createJWT(name, psw);
        System.out.println(token);
        Claims claims = JwtUtil.parseJWT(token);
        claims.setSubject(token);
        LoginDto loginDto = new LoginDto();
        loginDto.setName(name);
        loginDto.setPsw(psw);
        loginDto.setExp(claims.getExpiration());
        loginDto.setToken(token);
        Response response = new Response();
        response.setCode(ResponseCode.SUCCESS.toString());
        response.setData(loginDto);
        response.setMsg(ResponseMsg.SUCCESS.toString());
        return response;
    }


}
