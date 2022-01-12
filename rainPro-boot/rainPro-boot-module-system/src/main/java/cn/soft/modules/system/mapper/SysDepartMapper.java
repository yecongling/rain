package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门mapper接口
 */
@Repository
public interface SysDepartMapper extends BaseMapper<SysDepart> {

    /**
     * 根据用户ID查询部门集合
     * @param userId 用户ID
     * @return 返回部门集合
     */
    public List<SysDepart> queryUserDeparts(@Param("userId") String userId);
}
