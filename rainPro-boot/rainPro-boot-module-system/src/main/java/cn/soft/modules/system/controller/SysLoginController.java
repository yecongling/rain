package cn.soft.modules.system.controller;

import cn.soft.common.util.RedisUtil;
import cn.soft.modules.base.service.BaseCommonService;
import cn.soft.modules.system.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录controller
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "用户登录")
@Slf4j
public class SysLoginController {

    private ISysUserService sysUserService;
    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    private ISysBaseAPI sysBaseAPI;
    @Autowired
    public void setSysBaseAPI(ISysBaseAPI sysBaseAPI) {
        this.sysBaseAPI = sysBaseAPI;
    }

    private ISysLogService sysLogService;
    @Autowired
    public void setSysLogService(ISysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private ISysDepartService sysDepartService;
    @Autowired
    public void setSysDepartService(ISysDepartService sysDepartService) {
        this.sysDepartService = sysDepartService;
    }

    private ISysTenantService sysTenantService;
    @Autowired
    public void setSysTenantService(ISysTenantService sysTenantService) {
        this.sysTenantService = sysTenantService;
    }

    private ISysDictService sysDictService;
    @Autowired
    public void setSysDictService(ISysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }

    private BaseCommonService baseCommonService;
    @Autowired
    public void setBaseCommonService(BaseCommonService baseCommonService) {
        this.baseCommonService = baseCommonService;
    }
}
