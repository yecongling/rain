package cn.soft.modules.base.service.impl;

import cn.soft.common.api.dto.LogDTO;
import cn.soft.common.system.vo.LoginUser;
import cn.soft.modules.base.mapper.BaseCommonMapper;
import cn.soft.modules.base.service.BaseCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 基础通用服务实现类
 */
@Service
@Slf4j
public class BaseCommonServiceImpl implements BaseCommonService {

    @Resource
    private BaseCommonMapper baseCommonMapper;

    @Override
    public void addLog(LogDTO logDTO) {

    }

    @Override
    public void addLog(String LogContent, Integer logType, Integer operateType, LoginUser user) {

    }

    @Override
    public void addLog(String LogContent, Integer logType, Integer operateType) {

    }
}
