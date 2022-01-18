package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysAnnouncementSend;
import cn.soft.modules.system.model.AnnouncementSendModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  用户通告阅读标记表
 */
@Repository
public interface SysAnnouncementSendMapper extends BaseMapper<SysAnnouncementSend> {

	/**
	 * 查询用户通告ID的集合
	 * @param userId 用户
	 * @return /
	 */
	public List<String> queryByUserId(@Param("userId") String userId);

	/**
	 * 获取我的消息
	 * @param announcementSendModel 系统通告
	 * @return
	 */
	public List<AnnouncementSendModel> getMyAnnouncementSendList(Page<AnnouncementSendModel> page, @Param("announcementSendModel") AnnouncementSendModel announcementSendModel);

}
