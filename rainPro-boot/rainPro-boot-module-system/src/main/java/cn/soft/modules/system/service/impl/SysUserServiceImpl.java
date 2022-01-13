package cn.soft.modules.system.service.impl;

import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CacheConstant;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.base.service.BaseCommonService;
import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.entity.SysUserRole;
import cn.soft.modules.system.mapper.SysUserMapper;
import cn.soft.modules.system.mapper.SysUserRoleMapper;
import cn.soft.modules.system.service.ISysTenantService;
import cn.soft.modules.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户表服务实现类
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    /**
     * 用户mapper
     */
    private SysUserMapper userMapper;
    @Autowired
    public void setUserMapper(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 用户角色mapper
     */
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    public void setUserRoleMapper(SysUserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 基础服务实现
     */
    private BaseCommonService baseCommonService;
    @Autowired
    public void setBaseCommonService(BaseCommonService baseCommonService) {
        this.baseCommonService = baseCommonService;
    }

    /**
     * 租户实现类
     */
    private ISysTenantService sysTenantService;
    @Autowired
    public void setSysTenantService(ISysTenantService sysTenantService) {
        this.sysTenantService = sysTenantService;
    }

    /**
     * 验证用户是否有效
     * @param sysUser 用户
     * @return 返回验证结果
     */
    @Override
    public Result<?> checkUserIsEffective(SysUser sysUser) {
        Result<?> result = new Result<>();
        // 情况1：根据用户信息查询，该用户不存在
        if (sysUser == null) {
            result.error500("该用户不存在，请注册");
            baseCommonService.addLog("用户登录失败，用户不存在！", CommonConstant.LOG_TYPE_1, null);
            return result;
        }
        // 情况2：根据用后信息查询，该用户已注销
        if (CommonConstant.DEL_FLAG_1.equals(sysUser.getDelFlag())) {
            baseCommonService.addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已注销！", CommonConstant.LOG_TYPE_1, null);
            result.error500("该用户已注销");
            return result;
        }
        // 情况3：根据用户信息查询，该用户已冻结
        if (CommonConstant.USER_FREEZE.equals(sysUser.getStatus())) {
            baseCommonService.addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已冻结！", CommonConstant.LOG_TYPE_1, null);
            result.error500("该用户已冻结");
            return result;
        }
        return result;
    }

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return /
     */
    @Override
    public SysUser getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    /**
     * 添加用户角色关系
     * @param user 用户
     * @param roles 角色
     */
    @Override
    public void addUserWithRole(SysUser user, String roles) {
        this.save(user);
        if (ConvertUtils.isNotEmpty(roles)) {
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                SysUserRole userRole = new SysUserRole(user.getId(), roleId);
                userRoleMapper.insert(userRole);
            }
        }
    }

    /**
     * 根据用户ID设置部门ID
     * @param username 用户名
     * @param orgCode 部门
     */
    @Override
    @CacheEvict(value = {CacheConstant.SYS_USERS_CACHE}, key = "#username")
    public void updateUserDepart(String username, String orgCode) {
        baseMapper.updateUserDepart(username, orgCode);
    }

    /**
     * 获取用户的授权角色
     * @param username 用户
     * @return 角色
     */
    @Override
    public List<String> getRole(String username) {
        return userRoleMapper.getRoleByUserName(username);
    }
}
