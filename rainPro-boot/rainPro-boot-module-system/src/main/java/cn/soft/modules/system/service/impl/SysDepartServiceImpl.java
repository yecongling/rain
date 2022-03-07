package cn.soft.modules.system.service.impl;

import cn.soft.common.constant.CacheConstant;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.system.entity.SysDepart;
import cn.soft.modules.system.mapper.SysDepartMapper;
import cn.soft.modules.system.model.SysDepartTreeModel;
import cn.soft.modules.system.service.ISysDepartService;
import cn.soft.modules.system.util.FindsDepartsChildrenUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门表 服务实现类
 */
@Service
public class SysDepartServiceImpl extends ServiceImpl<SysDepartMapper, SysDepart> implements ISysDepartService {

    /**
     * 查询用户的部门集合
     *
     * @param userId 用户ID
     * @return 部门集合
     */
    @Override
    public List<SysDepart> queryUserDeparts(String userId) {
        return baseMapper.queryUserDeparts(userId);
    }

    /**
     * 查询所有部门信息,并分节点进行显示
     *
     * @return 显示所有部门
     */
    @Override
    @Cacheable(value = CacheConstant.SYS_DEPARTS_CACHE)
    public List<SysDepartTreeModel> queryTreeList() {
        LambdaQueryWrapper<SysDepart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDepart::getDelFlag, CommonConstant.DEL_FLAG_0.toString());
        queryWrapper.orderByAsc(SysDepart::getDepartOrder);
        List<SysDepart> list = this.list(queryWrapper);
        // 生成树状数据
        return FindsDepartsChildrenUtil.wrapTreeDataToTreeList(list);
    }

    /**
     * 查询所有部门信息,并分节点进行显示
     *
     * @param ids 部门ids
     * @return 部门数据
     */
    @Override
    public List<SysDepartTreeModel> queryTreeList(String ids) {
        List<SysDepartTreeModel> listResult=new ArrayList<>();
        LambdaQueryWrapper<SysDepart> query = new LambdaQueryWrapper<SysDepart>();
        query.eq(SysDepart::getDelFlag, CommonConstant.DEL_FLAG_0.toString());
        if(ConvertUtils.isNotEmpty(ids)){
            query.in(true,SysDepart::getId, ids.split(","));
        }
        query.orderByAsc(SysDepart::getDepartOrder);
        List<SysDepart> list= this.list(query);
        for (SysDepart depart : list) {
            listResult.add(new SysDepartTreeModel(depart));
        }
        return  listResult;
    }
}
