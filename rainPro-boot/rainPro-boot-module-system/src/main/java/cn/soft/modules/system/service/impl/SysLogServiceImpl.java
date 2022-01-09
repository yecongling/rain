package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysLog;
import cn.soft.modules.system.mapper.SysLogMapper;
import cn.soft.modules.system.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统日志表  服务实现类
 */
@Service
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {
}
