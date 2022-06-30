package cn.soft.common.api.dto;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 文件下载
 * cloud api 用到的接口传输对象
 */
@Data
public class FileDownDTO implements Serializable {

    private static final long serialVersionUID = 6749126258686446019L;

    private String filePath;
    private String uploadPath;
    private String uploadType;
    private HttpServletResponse response;

    public FileDownDTO(){}

    public FileDownDTO(String filePath, String uploadPath, String uploadType, HttpServletResponse response){
        this.filePath = filePath;
        this.uploadPath = uploadPath;
        this.uploadType = uploadType;
        this.response = response;
    }
}
