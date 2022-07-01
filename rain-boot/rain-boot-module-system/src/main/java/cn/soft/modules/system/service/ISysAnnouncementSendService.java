package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysAnnouncementSend;
import cn.soft.modules.system.model.AnnouncementSendModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户通告阅读标记表服务
 */
public interface ISysAnnouncementSendService extends IService<SysAnnouncementSend> {

    /**
     * 查询用户已读通告
     * @param userId 用户
     * @return
     */
    public List<String> queryByUserId(String userId);

    /**
     * 获取我的消息
     * @param announcementSendModel 已读通告
     * @return /
     */
    public Page<AnnouncementSendModel> getMyAnnouncementSendPage(Page<AnnouncementSendModel> page, AnnouncementSendModel announcementSendModel);
}
