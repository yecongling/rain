package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysUserRole;
import cn.soft.modules.system.mapper.SysUserRoleMapper;
import cn.soft.modules.system.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysUserRoleServiceImpl
 * @Description 用户角色表  服务实现类
 * @Author 叶丛林
 * @Date 2022/3/14 10:04 下午
 * @Version 1.0
 **/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
}
