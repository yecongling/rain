package cn.soft.modules.system.service;

import cn.soft.common.api.vo.Result;
import cn.soft.modules.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ISysRoleService
 * @Description 角色表服务接口
 * @Author ycl
 * @Date 2022/3/10 12:45
 * @Version 1.0
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 导入 excel ，检查 roleCode 的唯一性
     *
     * @param file   文件
     * @param params 导入参数
     * @return 返回结果
     * @throws Exception
     */
    Result importExcelCheckRoleCode(MultipartFile file, ImportParams params) throws Exception;

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 返回删除结果
     */
    boolean deleteRole(String roleId);

    /**
     * 批量删除角色
     *
     * @param roleIds 角色ID 集合
     * @return 返回删除结果
     */
    boolean deleteBatchRole(String[] roleIds);
}
