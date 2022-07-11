package cn.soft.modules.system.service;

import cn.soft.common.api.vo.Result;
import cn.soft.common.exception.RainBootException;
import cn.soft.modules.system.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单权限表  服务类
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 通过用户名查询该用户的菜单权限
     * @param username 用户名
     * @return 返回菜单权限集合
     */
    public List<SysPermission> queryByUser(String username);

    /**
     * 通过用户名查询用户的授权角色
     * @param username 用户名
     * @return 返回授权角色集合
     */
    public List<String> getRole(String username);

    /**
     * 编辑菜单
     *
     * @param permission 菜单对象
     */
    public Result<SysPermission> editPermission(SysPermission permission) throws RainBootException;
}
