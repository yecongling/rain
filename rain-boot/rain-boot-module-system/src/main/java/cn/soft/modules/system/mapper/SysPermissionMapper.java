package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单权限表mapper接口
 */
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据用户查询用户的菜单权限
     * @param username 用户名
     * @return 返回其菜单权限集合
     */
    public List<SysPermission> queryByUser(@Param("username") String username);

    /**
     * 修改菜单状态字段 是否为子节点
     *
     * @param id 菜单ID
     * @param leaf 叶子节点
     * @return int
     */
    @Update("update sys_permission set is_leaf=#{leaf} where id = #{id}")
    public int setMenuLeaf(@Param("id") String id, @Param("leaf") int leaf);
}
