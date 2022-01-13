package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色表Mapper接口
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 获取用户授权角色
     * @param username 用户
     * @return 授权角色
     */
    @Select("select role_code from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleByUserName(@Param("username") String username);
}
