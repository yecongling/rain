package cn.soft.modules.system.controller;

import cn.soft.modules.system.service.ISysAnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统通告controller
 */
@RestController
@RequestMapping("/sys/announcement")
@Slf4j
public class SysAnnouncementController {

    /**
     * 注入系统通告服务实现类
     */
    private ISysAnnouncementService sysAnnouncementService;
    @Autowired
    public void setSysAnnouncementService(ISysAnnouncementService sysAnnouncementService) {
        this.sysAnnouncementService = sysAnnouncementService;
    }
}
