package cn.soft.modules.system.service.impl;

import cn.soft.common.constant.CommonConstant;
import cn.soft.common.exception.RainBootException;
import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.system.entity.SysPermission;
import cn.soft.modules.system.mapper.SysPermissionMapper;
import cn.soft.modules.system.service.ISysPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
     *
     * @param username 用户名
     * @return 返回菜单权限集合
     */
    @Override
    public List<SysPermission> queryByUser(String username) {
        return permissionMapper.queryByUser(username);
    }

    /**
     * 通过用户名获取用户角色权限
     *
     * @param username 用户名
     * @return 返回角色权限集合
     */
    @Override
    public List<String> getRole(String username) {
        return null;
    }

    /**
     * 编辑菜单
     *
     * @param permission 菜单对象
     */
    @Override
    public void editPermission(SysPermission permission) throws RainBootException {
        SysPermission sysPermission = this.getById(permission.getId());
        // 判断是否存在该节点
        if (sysPermission == null) {
            throw new RainBootException("未找到对应的菜单信息");
        }
        permission.setUpdateTime(new Date());
        // 判断是否是一级菜单，是的画清空父菜单id
        if (CommonConstant.MENU_TYPE_0.equals(permission.getMenuType())) {
            permission.setParentId("");
        }
        // 判断菜单是否还有下级菜单，无则设置为叶子节点
        int count = this.count(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getParentId, permission.getId()));
        if (count == 0) {
            permission.setLeaf(true);
        }
        this.updateById(permission);
        // 如果当前菜单的父菜单变了，则需要修改新父菜单和老父菜单，叶子节点状态
        String parentId = permission.getParentId();
        if ((ConvertUtils.isNotEmpty(parentId) && !parentId.equals(sysPermission.getParentId())) || ConvertUtils.isEmpty(parentId) && ConvertUtils.isNotEmpty(sysPermission.getParentId())) {
            // 设置新的父菜单不为叶子节点
            this.permissionMapper.setMenuLeaf(parentId, 0);
            // 判断老的菜单下是否还有其他子菜单，没有的话则设置为叶子节点
            int count1 = this.count(new QueryWrapper<SysPermission>().lambda().eq(SysPermission::getParentId, sysPermission.getParentId()));
            if (count1 == 0) {
                if (ConvertUtils.isNotEmpty(sysPermission.getParentId())) {
                    this.permissionMapper.setMenuLeaf(sysPermission.getParentId(), 1);
                }
            }
        }
    }
}
