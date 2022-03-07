package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysDepart;
import cn.soft.modules.system.model.SysDepartTreeModel;
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
    List<SysDepart> queryUserDeparts(String userId);

    /**
     * 查询所有部门信息,并分节点进行显示
     * @return 显示所有部门
     */
    List<SysDepartTreeModel> queryTreeList();


    /**
     * 查询部分部门信息,并分节点进行显示
     * @return 显示部门数据
     */
    List<SysDepartTreeModel> queryTreeList(String ids);
}
