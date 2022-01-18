package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysAnnouncementSend;
import cn.soft.modules.system.mapper.SysAnnouncementSendMapper;
import cn.soft.modules.system.model.AnnouncementSendModel;
import cn.soft.modules.system.service.ISysAnnouncementSendService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户通告阅读标记表
 */
@Service
public class SysAnnouncementSendServiceImpl extends ServiceImpl<SysAnnouncementSendMapper, SysAnnouncementSend> implements ISysAnnouncementSendService {

    private SysAnnouncementSendMapper sysAnnouncementSendMapper;
    @Autowired
    public void setSysAnnouncementSendMapper(SysAnnouncementSendMapper sysAnnouncementSendMapper) {
        this.sysAnnouncementSendMapper = sysAnnouncementSendMapper;
    }

    /**
     * 获取用户已读通告
     * @param userId 用户
     * @return /
     */
    @Override
    public List<String> queryByUserId(String userId) {
        return sysAnnouncementSendMapper.queryByUserId(userId);
    }

    /**
     * 获取消息
     * @param page 分页对象
     * @param announcementSendModel 已读通告
     * @return /
     */
    @Override
    public Page<AnnouncementSendModel> getMyAnnouncementSendPage(Page<AnnouncementSendModel> page, AnnouncementSendModel announcementSendModel) {
        return page.setRecords(sysAnnouncementSendMapper.getMyAnnouncementSendList(page, announcementSendModel));
    }
}
