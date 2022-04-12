package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.entity.SysUserDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户部门mapper接口
 */
@Repository
public interface SysUserDepartMapper extends BaseMapper<SysUserDepart> {

    /**
     * 根据用户ID查询部门信息
     *
     * @param userId 用户ID
     * @return 部门信息集合
     */
    List<SysUserDepart> getUserDepartByUid(@Param("userId") String userId);

    /**
     * 查询指定部门下的用户 并且支持用户真实姓名模糊查询
     *
     * @param orgCode  部门编码
     * @param realname 真实姓名
     * @return 用户集合
     */
    List<SysUser> queryDepartUserList(@Param("orgCode") String orgCode, @Param("realname") String realname);

    /**
     * 根据部门查询部门用户
     *
     * @param page     页对象
     * @param orgCode  部门编码
     * @param username 用户名
     * @param realname 真实名
     * @return 用户集合
     */
    IPage<SysUser> queryDepartUserPageList(Page<SysUser> page, @Param("orgCode") String orgCode, @Param("username") String username, @Param("realname") String realname);
}
