package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysDepart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 部门表服务实现类
 */
public interface ISysDepartService extends IService<SysDepart> {

    /**
     * 查询用户的部门集合
     * @param userId 用户ID
     * @return 返回部门集合
     */
    public List<SysDepart> queryUserDeparts(String userId);
}
