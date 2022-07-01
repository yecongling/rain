package cn.soft.modules.system.service.impl;

import cn.soft.common.api.vo.Result;
import cn.soft.modules.system.entity.SysRole;
import cn.soft.modules.system.mapper.SysRoleMapper;
import cn.soft.modules.system.mapper.SysUserMapper;
import cn.soft.modules.system.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName SysRoleServiceImpl
 * @Description TODO
 * @Author ycl
 * @Date 2022/3/10 12:49
 * @Version 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private SysRoleMapper sysRoleMapper;
    @Autowired
    public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    private SysUserMapper sysUserMapper;
    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 导入 excel ，检查 roleCode 的唯一性
     *
     * @param file   文件
     * @param params 导入参数
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public Result importExcelCheckRoleCode(MultipartFile file, ImportParams params) throws Exception {
        return null;
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 返回删除结果
     */
    @Override
    public boolean deleteRole(String roleId) {
        return false;
    }

    /**
     * 批量删除角色
     *
     * @param roleIds 角色ID 集合
     * @return 返回删除结果
     */
    @Override
    public boolean deleteBatchRole(String[] roleIds) {
        return false;
    }
}
