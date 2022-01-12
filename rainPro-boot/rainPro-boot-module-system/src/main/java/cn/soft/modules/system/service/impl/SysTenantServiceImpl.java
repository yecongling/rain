package cn.soft.modules.system.service.impl;

import cn.soft.common.constant.CommonConstant;
import cn.soft.modules.system.entity.SysTenant;
import cn.soft.modules.system.mapper.SysTenantMapper;
import cn.soft.modules.system.service.ISysTenantService;
import cn.soft.modules.system.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 租户服务实现类
 */
@Service("SysTenantServiceImpl")
@Slf4j
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

    /**
     * 用户服务实现
     */
    private ISysUserService userService;
    @Autowired
    public void setUserService(ISysUserService userService) {
        this.userService = userService;
    }

    /**
     * 查询有效的租户
     * @param idList ID集合
     * @return 返回有效租户集合
     */
    @Override
    public List<SysTenant> queryEffectiveTenant(Collection<String> idList) {
        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysTenant::getId, idList);
        queryWrapper.eq(SysTenant::getStatus, CommonConstant.STATUS_1);
        // 忽略时间条件
        return super.list(queryWrapper);
    }
}
