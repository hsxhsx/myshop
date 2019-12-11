package com.hsx.myshop.controller.webSocketServer;

import com.hsx.myshop.dto.ScoketDto;
import com.hsx.myshop.response.Response;
import com.hsx.myshop.response.ResponseCode;
import com.hsx.myshop.response.ResponseMsg;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/checkcenter")
public class CheckCenterController {
    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public Response pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            Response response = new Response();
            response.setCode(ResponseCode.FAILURE.toString());
            response.setMsg(ResponseMsg.FAILURE.toString());
            return response;
        }
        Response response = new Response();
        response.setCode(ResponseCode.SUCCESS.toString());
        response.setMsg(ResponseMsg.SUCCESS.toString());
        ScoketDto scoketDto = new ScoketDto();
        scoketDto.setMessage(message);
        scoketDto.setCid(cid);
        response.setData(scoketDto);
        return response;
    }

}
