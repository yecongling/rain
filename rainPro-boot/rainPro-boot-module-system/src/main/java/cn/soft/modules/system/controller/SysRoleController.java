package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.system.query.QueryGenerator;
import cn.soft.modules.system.entity.SysRole;
import cn.soft.modules.system.service.ISysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName SysRoleController
 * @Description 角色表前端控制器
 * @Author ycl
 * @Date 2022/3/10 12:44
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/sys/role")
public class SysRoleController {

    private ISysRoleService sysRoleService;

    @Autowired
    public void setSysRoleService(ISysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 分页查询系统角色
     *
     * @param role     角色对象
     * @param pageNo   页码
     * @param pageSize 页数据
     * @param request  请求
     * @return 返回分页数据
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<SysRole>> queryPageList(SysRole role, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
        Result<IPage<SysRole>> result = new Result<>();
        QueryWrapper<SysRole> queryWrapper = QueryGenerator.initQueryWrapper(role, request.getParameterMap());
        Page<SysRole> page = new Page<>(pageNo, pageSize);
        Page<SysRole> rolePage = sysRoleService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(rolePage);
        return result;
    }

    /**
     * 查询所有角色
     *
     * @return 角色集合
     */
    @GetMapping("/queryAllRole")
    public Result<List<SysRole>> queryAll() {
        Result<List<SysRole>> result = new Result<>();
        List<SysRole> list = sysRoleService.list();
        if (list == null || list.size() == 0) {
            result.error500("未查询到对应的角色信息");
        } else {
            result.setResult(list);
            result.setSuccess(true);
        }
        return result;
    }
}
