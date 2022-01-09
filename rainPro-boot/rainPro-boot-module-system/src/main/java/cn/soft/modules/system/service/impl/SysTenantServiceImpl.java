package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysTenant;
import cn.soft.modules.system.mapper.SysTenantMapper;
import cn.soft.modules.system.service.ISysTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 租户服务实现类
 */
@Service("SysTenantServiceImpl")
@Slf4j
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {
}
