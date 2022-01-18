package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysAnnouncement;
import cn.soft.modules.system.mapper.SysAnnouncementMapper;
import cn.soft.modules.system.service.ISysAnnouncementService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统通告服务
 */
@Service
public class SysAnnouncementServiceImpl extends ServiceImpl<SysAnnouncementMapper, SysAnnouncement> implements ISysAnnouncementService {

    /**
     * 注入系统通告的mapper
     */
    private SysAnnouncementMapper announcementMapper;
    @Autowired
    public void setAnnouncementMapper(SysAnnouncementMapper announcementMapper) {
        this.announcementMapper = announcementMapper;
    }

    /**
     * 保存系统通告
     * @param announcement 通告
     */
    @Override
    public void saveAnnouncement(SysAnnouncement announcement) {

    }

    /**
     * 修改系统公告
     * @param announcement 公告
     * @return 修改成功/失败
     */
    @Override
    public boolean updateAnnouncement(SysAnnouncement announcement) {
        return false;
    }

    /**
     * 保存系统公告
     * @param title 标题
     * @param msgContent 内容
     */
    @Override
    public void saveSysAnnouncement(String title, String msgContent) {

    }

    /**
     * 根据用户查询起分页系统通告数据
     * @param page 分页对象
     * @param userId 用户ID
     * @param msgCategory 类别
     * @return /
     */
    @Override
    public Page<SysAnnouncement> querySysAnnouncementPageByUserId(Page<SysAnnouncement> page, String userId, String msgCategory) {
        if (page.getSize() == -1) {
            return page.setRecords(announcementMapper.querySysAnnouncementListByUserId(null, userId, msgCategory));
        } else {
            return page.setRecords(announcementMapper.querySysAnnouncementListByUserId(page, userId, msgCategory));
        }
    }
}
