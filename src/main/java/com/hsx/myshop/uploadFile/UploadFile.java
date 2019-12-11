package com.hsx.myshop.uploadFile;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document
@Data
public class UploadFile {
    @Id
    private String id;
    private String name; // 文件名
    private Date createdTime; // 上传时间
    private Binary content; // 文件内容
    private String contentType; // 文件类型
    private long size; // 文件大小

}
