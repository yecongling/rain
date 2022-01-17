package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysAnnouncement;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统通告服务
 */
public interface ISysAnnouncementService extends IService<SysAnnouncement> {

    /**
     * 保存系统通告
     * @param announcement 通告
     */
    public void saveAnnouncement(SysAnnouncement announcement);

    /**
     * 修改系统公告
     * @param announcement 公告
     * @return 修改成功/失败
     */
    public boolean updateAnnouncement(SysAnnouncement announcement);

    /**
     * 保存系统公告
     * @param title 标题
     * @param msgContent 内容
     */
    public void saveSysAnnouncement(String title, String msgContent);

    /**
     * 根据用户查询起分页系统通告数据
     * @param page 分页对象
     * @param userId 用户ID
     * @param msgContent 内容
     * @return /
     */
    public Page<SysAnnouncement> querySysAnnouncementPageByUserId(Page<SysAnnouncement> page, String userId, String msgContent);
}
