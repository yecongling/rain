package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.vo.SysUserDepVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表mapper接口
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户账号查询用户信息
     * @param username
     * @return
     */
    public SysUser getUserByName(@Param("username") String username);

    /**
     * 根据用户名设置部门ID
     * @param username 用户名
     * @param orgCode 部门ID
     */
    void updateUserDepart(@Param("username") String username, @Param("orgCode") String orgCode);

    /**
     *  根据用户Ids,查询用户所属部门名称信息
     * @param userIds
     * @return
     */
    List<SysUserDepVo> getDepNamesByUserIds(@Param("userIds")List<String> userIds);

}
