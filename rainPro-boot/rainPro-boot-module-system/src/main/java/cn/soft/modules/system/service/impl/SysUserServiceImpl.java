package cn.soft.modules.system.service.impl;

import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CacheConstant;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.base.service.BaseCommonService;
import cn.soft.modules.system.entity.*;
import cn.soft.modules.system.mapper.*;
import cn.soft.modules.system.service.ISysTenantService;
import cn.soft.modules.system.service.ISysUserService;
import cn.soft.modules.system.vo.SysUserDepVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
     * 用户部门mapper
     */
    private SysUserDepartMapper sysUserDepartMapper;
    @Autowired
    public void setSysUserDepartMapper(SysUserDepartMapper sysUserDepartMapper) {
        this.sysUserDepartMapper = sysUserDepartMapper;
    }

    /**
     * 部门角色mapper
     */
    public SysDepartRoleMapper sysDepartRoleMapper;
    @Autowired
    public void setSysDepartRoleMapper(SysDepartRoleMapper sysDepartRoleMapper) {
        this.sysDepartRoleMapper = sysDepartRoleMapper;
    }

    /**
     * 部门角色用户mapper
     */
    public SysDepartRoleUserMapper departRoleUserMapper;
    @Autowired
    public void setDepartRoleUserMapper(SysDepartRoleUserMapper departRoleUserMapper) {
        this.departRoleUserMapper = departRoleUserMapper;
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
     *
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
     *
     * @param username 用户名
     * @return /
     */
    @Override
    public SysUser getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    /**
     * 添加用户角色关系
     *
     * @param user  用户
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
     *
     * @param username 用户名
     * @param orgCode  部门
     */
    @Override
    @CacheEvict(value = {CacheConstant.SYS_USERS_CACHE}, key = "#username")
    public void updateUserDepart(String username, String orgCode) {
        baseMapper.updateUserDepart(username, orgCode);
    }

    /**
     * 获取用户的授权角色
     *
     * @param username 用户
     * @return 角色
     */
    @Override
    public List<String> getRole(String username) {
        return userRoleMapper.getRoleByUserName(username);
    }

    /**
     * 根据 userIds查询，查询用户所属部门的名称（多个部门名逗号隔开）
     *
     * @param
     * @return
     */
    @Override
    public Map<String, String> getDepNamesByUserIds(List<String> userIds) {
        List<SysUserDepVo> list = this.baseMapper.getDepNamesByUserIds(userIds);

        Map<String, String> res = new HashMap<String, String>();
        list.forEach(item -> {
                    res.merge(item.getUserId(), item.getDepartName(), (a, b) -> a + "," + b);
                }
        );
        return res;
    }

    /**
     * 编辑用户信息
     *
     * @param user    用户对象
     * @param roles   角色信息
     * @param departs 部门信息
     */
    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.SYS_USERS_CACHE}, allEntries = true)
    public void editUser(SysUser user, String roles, String departs) {
        // 1、修改用户的基础信息
        this.updateById(user);
        // 2、修改用户角色信息  采用先删除后添加的方法
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, user.getId()));
        if (ConvertUtils.isNotEmpty(roles)) {
            // 依次插入角色信息
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                SysUserRole userRole = new SysUserRole(user.getId(), roleId);
                userRoleMapper.insert(userRole);
            }
        }
        // 3、修改部门
        String[] arr = {};
        if (ConvertUtils.isNotEmpty(departs)) {
            arr = departs.split(",");
        }
        // 查询已关联的部门
        List<SysUserDepart> userDepartList = sysUserDepartMapper.selectList(new QueryWrapper<SysUserDepart>().lambda().eq(SysUserDepart::getUserId, user.getId()));
        if (userDepartList != null && userDepartList.size() > 0) {
            for (SysUserDepart depart : userDepartList) {
                // 修改已关联部门删除部门用户角色关系
                if (!Arrays.asList(arr).contains(depart.getDepId())) {
                    List<SysDepartRole> sysDepartRoleList = sysDepartRoleMapper.selectList(new QueryWrapper<SysDepartRole>().lambda().eq(SysDepartRole::getDepartId, depart.getDepId()));
                    List<String> roleIds = sysDepartRoleList.stream().map(SysDepartRole::getId).collect(Collectors.toList());
                    if (roleIds.size() > 0) {
                        departRoleUserMapper.delete(new QueryWrapper<SysDepartRoleUser>().lambda().eq(SysDepartRoleUser::getId, user.getId()).in(SysDepartRoleUser::getDroleId, roleIds));
                    }
                }
            }
        }
        // 先删后加
        sysUserDepartMapper.delete(new QueryWrapper<SysUserDepart>().lambda().eq(SysUserDepart::getUserId, user.getId()));
        if (ConvertUtils.isNotEmpty(departs)) {
            for (String departId : arr) {
                SysUserDepart sysUserDepart = new SysUserDepart(user.getId(), departId);
                sysUserDepartMapper.insert(sysUserDepart);
            }
        }
    }
}
