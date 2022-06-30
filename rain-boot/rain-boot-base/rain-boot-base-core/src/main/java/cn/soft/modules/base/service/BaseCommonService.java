package cn.soft.modules.base.service;

import cn.soft.common.api.dto.LogDTO;
import cn.soft.common.system.vo.LoginUser;

/**
 * 基础通用服务接口
 */
public interface BaseCommonService {

    /**
     * 保存日志
     *
     * @param logDTO
     */
    void addLog(LogDTO logDTO);

    /**
     * 保存日志
     *
     * @param logContent
     * @param logType
     * @param operateType
     * @param user
     */
    void addLog(String logContent, Integer logType, Integer operateType, LoginUser user);

    /**
     * 保存日志
     *
     * @param logContent
     * @param logType
     * @param operateType
     */
    void addLog(String logContent, Integer logType, Integer operateType);
}
