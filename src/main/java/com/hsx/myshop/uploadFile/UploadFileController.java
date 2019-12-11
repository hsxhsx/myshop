package com.hsx.myshop.uploadFile;

import com.hsx.myshop.response.Response;
import com.hsx.myshop.response.ResponseCode;
import com.hsx.myshop.response.ResponseMsg;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class UploadFileController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/file/uploadImage")
    @ResponseBody
    public Response uploadImage(@RequestParam(value = "image") MultipartFile file) {
        // 返回的 JSON 对象，这种类可自己封装
        Response response = new Response();
        String fileName = file.getOriginalFilename();
        try {
            if (file.isEmpty()) {
                response.setData("请选择一张图片");
                response.setCode(ResponseCode.FAILURE.toString());
                response.setMsg(ResponseMsg.FAILURE.toString());
                return response;
            }
            UploadFile uploadFile = new UploadFile();
            uploadFile.setName(fileName);
            uploadFile.setCreatedTime(new Date());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());

            UploadFile savedFile = mongoTemplate.save(uploadFile);
            String url = "http://localhost:8081/file/image/" + savedFile.getId();

            response.setData(url);
            response.setCode(ResponseCode.SUCCESS.toString());
            response.setMsg(ResponseMsg.SUCCESS.toString());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.setData(e.toString());
            response.setCode(ResponseCode.FAILURE.toString());
            response.setMsg(ResponseMsg.FAILURE.toString());
            return response;
        }
    }

    @GetMapping(value = "/file/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public Response image(@PathVariable String id){
        byte[] data = null;
        Query query = new Query(Criteria.where("id").is("id"));
        // 查询一条满足条件的数据
        UploadFile file = mongoTemplate.findOne(query, UploadFile.class);
        if(file != null){
            data = file.getContent().getData();
        }
        Response response = new Response();
        response.setData(data);
        response.setCode(ResponseCode.SUCCESS.toString());
        response.setMsg(ResponseMsg.SUCCESS.toString());
        return response;
    }


}





