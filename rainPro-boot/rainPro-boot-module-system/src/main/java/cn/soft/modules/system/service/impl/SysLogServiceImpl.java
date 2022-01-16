package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysLog;
import cn.soft.modules.system.mapper.SysLogMapper;
import cn.soft.modules.system.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统日志表  服务实现类
 */
@Service
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    /**
     * 清空所有日志
     */
    @Override
    public void removeAll() {

    }

    /**
     * 获取系统访问次数
     * @return 返回系统的访问次数
     */
    @Override
    public Long findTotalVisitCount() {
        return null;
    }

    /**
     * 获取系统今日访问次数
     * @param dayStart 开始时间
     * @param dayEnd 结束时间
     * @return 返回今天时间内的系统访问次数
     */
    @Override
    public Long findTodayVisitCount(Date dayStart, Date dayEnd) {
        return null;
    }

    /**
     * 获取系统今日访问IP数
     * @param dayStart 开始时间
     * @param dayEnd 结束时间
     * @return /
     */
    @Override
    public Long findTodayIp(Date dayStart, Date dayEnd) {
        return null;
    }

    /**
     * 首页：根据时间统计访问数量/IP数量
     * @param dayStart 开始时间
     * @param dayEnd 结束时间
     * @return /
     */
    @Override
    public List<Map<String, Object>> findVisitCount(Date dayStart, Date dayEnd) {
        return null;
    }
}
