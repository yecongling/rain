package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统日志表mapper接口
 */
@Repository
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 清空所有日志记录
     */
    public void removeAll();

    /**
     * 获取系统总访问次数
     * @return 返回系统访问次数
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    Long findTodayVisitCount(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    Long findTodayIp(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);

    /**
     *   首页：根据时间统计访问数量/ip数量
     * @param dayStart 开始时间
     * @param dayEnd 结束时间
     * @return /
     */
    List<Map<String,Object>> findVisitCount(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd, @Param("dbType") String dbType);
}
