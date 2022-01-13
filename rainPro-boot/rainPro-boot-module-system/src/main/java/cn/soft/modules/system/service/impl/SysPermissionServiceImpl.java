package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysPermission;
import cn.soft.modules.system.mapper.SysPermissionMapper;
import cn.soft.modules.system.service.ISysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单权限表  服务实现类
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    private SysPermissionMapper permissionMapper;
    @Autowired
    public void setPermissionMapper(SysPermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * 通过用户名查询菜单权限集合
     * @param username 用户名
     * @return 返回菜单权限集合
     */
    @Override
    public List<SysPermission> queryByUser(String username) {
        return null;
    }

    /**
     * 通过用户名获取用户角色权限
     * @param username 用户名
     * @return 返回角色权限集合
     */
    @Override
    public List<String> getRole(String username) {
        return null;
    }
}
