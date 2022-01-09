package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.mapper.SysUserMapper;
import cn.soft.modules.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户表服务实现类
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
}
