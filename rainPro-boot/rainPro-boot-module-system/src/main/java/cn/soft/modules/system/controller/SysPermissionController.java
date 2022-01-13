package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.config.RainProBaseConfig;
import cn.soft.modules.system.service.ISysPermissionService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单权限  前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

    /**
     * 注入系统基础配置
     */
    private RainProBaseConfig rainProBaseConfig;
    @Autowired
    public void setRainProBaseConfig(RainProBaseConfig rainProBaseConfig) {
        this.rainProBaseConfig = rainProBaseConfig;
    }

    /**
     * 菜单权限业务实现
     */
    private ISysPermissionService permissionService;
    @Autowired
    public void setPermissionService(ISysPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 查询用户拥有的菜单权限和按钮权限
     * @return 返回菜单权限和按钮权限
     */
    @GetMapping("/getUserPermissionByToken")
    public Result<JSONObject> getUserPermissionByToken() {
        return null;
    }


}
