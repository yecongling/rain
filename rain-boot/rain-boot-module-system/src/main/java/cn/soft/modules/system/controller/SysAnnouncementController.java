package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.system.vo.LoginUser;
import cn.soft.modules.system.entity.SysAnnouncement;
import cn.soft.modules.system.entity.SysAnnouncementSend;
import cn.soft.modules.system.service.ISysAnnouncementSendService;
import cn.soft.modules.system.service.ISysAnnouncementService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 注入用户通告已读标记表
     */
    private ISysAnnouncementSendService sysAnnouncementSendService;

    @Autowired
    public void setSysAnnouncementSendService(ISysAnnouncementSendService sysAnnouncementSendService) {
        this.sysAnnouncementSendService = sysAnnouncementSendService;
    }

    /**
     * 补充用户数据
     *
     * @param pageSize 分页
     * @return /
     */
    @RequestMapping(value = "/listByUser", method = RequestMethod.GET)
    public Result<Map<String, Object>> listByUser(@RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Result<Map<String, Object>> result = new Result<>();
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String userId = sysUser.getId();
        // 将系统消息补充到用户通告阅读标记表中
        LambdaQueryWrapper<SysAnnouncement> queryWrapper = new LambdaQueryWrapper<>();
        // 全部人员
        queryWrapper.eq(SysAnnouncement::getMsgType, CommonConstant.MSG_TYPE_ALL);
        // 未删除
        queryWrapper.eq(SysAnnouncement::getDelFlag, CommonConstant.DEL_FLAG_0.toString());
        // 已发布
        queryWrapper.eq(SysAnnouncement::getSendStatus, CommonConstant.HAS_SEND);
        // 新注册用户不看结束通知
        queryWrapper.eq(SysAnnouncement::getEndTime, sysUser.getCreateTime());
        queryWrapper.notInSql(SysAnnouncement::getId, "select annt_id from sys_announcement_send where user_id='" + userId + "'");
        List<SysAnnouncement> announcements = sysAnnouncementService.list(queryWrapper);
        if (announcements.size() > 0) {
            for (SysAnnouncement announcement : announcements) {
                LambdaQueryWrapper<SysAnnouncementSend> query = new LambdaQueryWrapper<>();
                query.eq(SysAnnouncementSend::getAnntId, announcement.getId());
                query.eq(SysAnnouncementSend::getUserId, userId);
                SysAnnouncementSend one = sysAnnouncementSendService.getOne(query);
                if (one == null) {
                    SysAnnouncementSend announcementSend = new SysAnnouncementSend();
                    announcementSend.setAnntId(announcement.getId());
                    announcementSend.setUserId(userId);
                    announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
                    sysAnnouncementSendService.save(announcementSend);
                }
            }
        }
        // 查询用户未读的系统消息
        Page<SysAnnouncement> anntMsgList = new Page<SysAnnouncement>(0, pageSize);
        // 通知公告消息
        anntMsgList = sysAnnouncementService.querySysAnnouncementPageByUserId(anntMsgList, userId, "1");
        Page<SysAnnouncement> sysMsgList = new Page<SysAnnouncement>(0, pageSize);
        // 系统消息
        sysMsgList = sysAnnouncementService.querySysAnnouncementPageByUserId(sysMsgList, userId, "2");
        Map<String, Object> sysMsgMap = new HashMap<String, Object>();
        sysMsgMap.put("sysMsgList", sysMsgList.getRecords());
        sysMsgMap.put("sysMsgTotal", sysMsgList.getTotal());
        sysMsgMap.put("anntMsgList", anntMsgList.getRecords());
        sysMsgMap.put("anntMsgTotal", anntMsgList.getTotal());
        result.setSuccess(true);
        result.setResult(sysMsgMap);
        return result;
    }
}
