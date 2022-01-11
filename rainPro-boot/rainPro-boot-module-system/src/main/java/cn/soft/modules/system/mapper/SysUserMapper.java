package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

}
