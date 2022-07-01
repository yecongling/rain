package cn.soft.modules.base.service.impl;

import cn.soft.common.api.dto.LogDTO;
import cn.soft.common.system.vo.LoginUser;
import cn.soft.common.util.IPUtils;
import cn.soft.common.util.SpringContextUtils;
import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.base.mapper.BaseCommonMapper;
import cn.soft.modules.base.service.BaseCommonService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 基础通用服务实现类
 */
@Service
@Slf4j
public class BaseCommonServiceImpl implements BaseCommonService {


    private BaseCommonMapper baseCommonMapper;
    @Autowired
    public void setBaseCommonMapper(BaseCommonMapper baseCommonMapper) {
        this.baseCommonMapper = baseCommonMapper;
    }

    /**
     * 保存系统日志
     *
     * @param logDTO 日志对象
     */
    @Override
    public void addLog(LogDTO logDTO) {
        if (ConvertUtils.isEmpty(logDTO.getId())) {
            logDTO.setId(String.valueOf(IdWorker.getId()));
        }
        // 保存日志（异常捕获处理，防止数据太大存储失败，导致业务失败）
        try {
            baseCommonMapper.saveLog(logDTO);
        } catch (Exception e) {
            log.warn(" LogContent length :" + logDTO.getLogContent().length());
            log.warn(e.getMessage());
        }
    }

    /**
     * 保存日志
     * @param logContent 日志内容
     * @param logType 日志类型
     * @param operateType 操作类型
     * @param user 用户
     */
    @Override
    public void addLog(String logContent, Integer logType, Integer operateType, LoginUser user) {
        LogDTO dto = new LogDTO();
        dto.setId(String.valueOf(IdWorker.getId()));
        // 注解上面的描述，操作日志内容
        dto.setLogContent(logContent);
        dto.setLogType(logType);
        dto.setOperateType(operateType);
        try {
            // 获取request
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            // 设置IP地址
            dto.setIp(IPUtils.getIpAddr(request));
        } catch (Exception e) {
            dto.setIp("127.0.0.1");
        }
        // 获取登录用户信息
        if (user == null) {
            try {
                user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            } catch (Exception e) {
                log.error("未获取到登录用户信息，异常信息为："  + e.getMessage());
            }
        }
        if (user != null) {
            dto.setUserid(user.getUsername());
            dto.setUsername(user.getRealname());
        }
        dto.setCreateTime(new Date());
        // 保存日志（异常捕获处理，防止数据太长存储失败，导致业务失败）
        try {
            baseCommonMapper.saveLog(dto);
        } catch (Exception e) {
            log.warn(" LogContent length :" + dto.getLogContent().length());
            log.warn(e.getMessage());
        }
    }

    /**
     * 添加日志
     * @param logContent 日志内容
     * @param logType 日志类型
     * @param operateType 操作类型
     */
    @Override
    public void addLog(String logContent, Integer logType, Integer operateType) {
        addLog(logContent, logType, operateType, null);
    }
}
