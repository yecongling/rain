package cn.soft.modules.system.service;

import cn.soft.common.api.vo.Result;
import cn.soft.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户表服务类
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 校验用户是否有效
     * @param sysUser 用户
     * @return /
     */
    Result checkUserIsEffective(SysUser sysUser);

}
