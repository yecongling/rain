package cn.soft.modules.system.service;

import cn.soft.common.api.vo.Result;
import cn.soft.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 用户表服务类
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 校验用户是否有效
     *
     * @param sysUser 用户
     * @return /
     */
    Result checkUserIsEffective(SysUser sysUser);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 返回用户
     */
    public SysUser getUserByName(String username);

    /**
     * 添加用户和用户角色关系
     *
     * @param user  用户
     * @param roles 角色
     */
    public void addUserWithRole(SysUser user, String roles);

    /**
     * 根据用户ID设置部门ID
     *
     * @param username 用户名
     * @param orgCode  部门
     */
    void updateUserDepart(String username, String orgCode);

    /**
     * 获取用户的授权角色
     *
     * @param username 用户
     * @return 角色
     */
    public List<String> getRole(String username);

    /**
     * 根据 userIds查询，查询用户所属部门的名称（多个部门名逗号隔开）
     *
     * @param
     * @return
     */
    public Map<String, String> getDepNamesByUserIds(List<String> userIds);

}
