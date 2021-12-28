package cn.soft.common.api.dto;

import cn.soft.common.system.vo.LoginUser;
import org.omg.CORBA.INTERNAL;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志对象
 */
public class LogDTO implements Serializable {

    private static final long serialVersionUID = -443260975545114535L;

    /**
     * 日志内容
     */
    private String logContent;

    /**
     * 日志类型   1、操作日志  2、登录日志  3、定时任务
     */
    private Integer logType;

    /**
     * 操作类型  1、添加 2、修改  3、删除
     */
    private Integer operateType;
    /**
     * 登录用户
     */
    private LoginUser loginUser;

    private String id;
    private String createBy;
    private Date createTime;
    private Long costTime;
    private String ip;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 操作人用户名称
     */
    private String username;

    /**
     * 操作人用户账户
     */
    private String userid;

}
