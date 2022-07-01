package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统日志服务类
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 清空所有日志
     */
    public void removeAll();

    /**
     * 获取系统访问次数
     * @return 返回系统的访问次数
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     * @param dayStart 开始时间
     * @param dayEnd 结束时间
     * @return 返回今天时间内的系统访问次数
     */
    Long findTodayVisitCount(Date dayStart, Date dayEnd);

    /**
     * 获取系统今日访问IP数
     * @param dayStart 开始时间
     * @param dayEnd 结束时间
     * @return /
     */
    Long findTodayIp(Date dayStart, Date dayEnd);

    /**
     * 首页：根据时间统计访问数量/IP数量
     * @param dayStart 开始时间
     * @param dayEnd 结束时间
     * @return /
     */
    List<Map<String, Object>> findVisitCount(Date dayStart, Date dayEnd);
}
