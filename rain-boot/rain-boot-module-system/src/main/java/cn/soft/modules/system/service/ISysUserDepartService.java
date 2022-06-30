package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.entity.SysUserDepart;
import cn.soft.modules.system.model.DepartIdModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ClassName ISysUserDepartService
 * @Description 用户组织机构服务
 * @Author 叶丛林
 * @Date 2022/3/14 10:20 下午
 * @Version 1.0
 **/
public interface ISysUserDepartService extends IService<SysUserDepart> {

    /**
     * 根据用户ID查询部门信息
     *
     * @param userId 用户ID
     * @return 返回部门信息
     */
    List<DepartIdModel> queryDepartIdsOfUser(String userId);

    /**
     * 根据部门编号查询用户信息
     *
     * @param deptId 部门编号
     * @return 返回用户信息
     */
    List<SysUser> queryUserByDeptId(String deptId);

    /**
     * 根据部门code，查询当前部门和下级部门的用户信息
     *
     * @param deptCode 部门code
     * @param realName 真实姓名
     * @return '
     */
    List<SysUser> queryUserByDepCode(String deptCode, String realName);

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
    IPage<SysUser> queryDepartUserPageList(String departId, String username, String realName, int pageSize, int pageNo);
}
