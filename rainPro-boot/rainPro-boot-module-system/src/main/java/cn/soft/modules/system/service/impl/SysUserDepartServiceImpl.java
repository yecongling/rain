package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysDepart;
import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.entity.SysUserDepart;
import cn.soft.modules.system.mapper.SysUserDepartMapper;
import cn.soft.modules.system.model.DepartIdModel;
import cn.soft.modules.system.service.ISysDepartService;
import cn.soft.modules.system.service.ISysUserDepartService;
import cn.soft.modules.system.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SysUserDepartServiceImpl
 * @Description 用户部门实现类
 * @Author 叶丛林
 * @Date 2022/4/12 21:39
 * @Version 1.0
 **/
@Service
public class SysUserDepartServiceImpl extends ServiceImpl<SysUserDepartMapper, SysUserDepart> implements ISysUserDepartService {

    /**
     * 注入部门服务
     */
    private ISysDepartService sysDepartService;

    @Autowired
    public void setSysDepartService(ISysDepartService sysDepartService) {
        this.sysDepartService = sysDepartService;
    }

    /**
     * 注入用户服务
     */
    private ISysUserService sysUserService;

    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 根据用户ID查询部门信息
     *
     * @param userId 用户ID
     * @return 返回部门信息
     */
    @Override
    public List<DepartIdModel> queryDepartIdsOfUser(String userId) {
        LambdaQueryWrapper<SysUserDepart> queryUserDepart = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SysDepart> queryDepart = new LambdaQueryWrapper<>();
        try {
            queryUserDepart.eq(SysUserDepart::getUserId, userId);
            List<String> depIdList = new ArrayList<>();
            List<DepartIdModel> departIdModelList = new ArrayList<>();
            // 查询指定用户的部门ID集合
            List<SysUserDepart> userDepartList = this.list(queryUserDepart);
            if (userDepartList != null && userDepartList.size() > 0) {
                // 获取部门的ID集合
                for (SysUserDepart userDepart : userDepartList) {
                    depIdList.add(userDepart.getDepId());
                }
                // 通过部门的ID集合查询部门的信息
                queryDepart.in(SysDepart::getId, depIdList);
                List<SysDepart> departList = sysDepartService.list(queryDepart);
                if (departList != null && departList.size() > 0) {
                    for (SysDepart depart : departList) {
                        departIdModelList.add(new DepartIdModel().convertByUserDepart(depart));
                    }
                }
                return departIdModelList;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    /**
     * 根据部门编号查询用户信息
     *
     * @param deptId 部门编号
     * @return 返回用户信息
     */
    @Override
    public List<SysUser> queryUserByDeptId(String deptId) {
        return null;
    }

    /**
     * 根据部门code，查询当前部门和下级部门的用户信息
     *
     * @param deptCode 部门code
     * @param realName 真实姓名
     * @return '
     */
    @Override
    public List<SysUser> queryUserByDepCode(String deptCode, String realName) {
        return null;
    }

    /**
     * 用户组件信息查询
     *
     * @param departId 部门ID
     * @param username 用户名
     * @param realName 真实名
     * @param pageSize 页大小
     * @param pageNo   页数
     * @return /
     */
    @Override
    public IPage<SysUser> queryDepartUserPageList(String departId, String username, String realName, int pageSize, int pageNo) {
        return null;
    }
}
