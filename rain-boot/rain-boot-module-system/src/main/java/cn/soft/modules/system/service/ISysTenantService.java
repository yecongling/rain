package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysTenant;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 租户表实现类
 */
public interface ISysTenantService extends IService<SysTenant> {

    /**
     * 查询有效的租户
     * @param idList ID集合
     * @return 返回有效租户集合
     */
    List<SysTenant> queryEffectiveTenant(Collection<String> idList);
}
