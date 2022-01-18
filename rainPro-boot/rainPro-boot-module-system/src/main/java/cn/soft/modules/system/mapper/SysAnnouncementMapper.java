package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysAnnouncement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统通告服务mapper
 */
@Repository
public interface SysAnnouncementMapper extends BaseMapper<SysAnnouncement> {

    /**
     * 根据用户查询对应的通告数据
     * @param page 分页数据
     * @param userId 用户ID
     * @param msgCategory 内容
     * @return /
     */
    List<SysAnnouncement> querySysAnnouncementListByUserId(Page<SysAnnouncement> page, @Param("userId")String userId, @Param("msgCategory")String msgCategory);

}
