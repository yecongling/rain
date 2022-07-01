package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.aspect.annotation.AutoLog;
import cn.soft.common.system.query.QueryGenerator;
import cn.soft.modules.system.entity.SysPosition;
import cn.soft.modules.system.service.ISysPositionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName SysPositionController
 * @Description 职务前端控制器
 * @Author 叶丛林
 * @Date 2022/3/7 8:56 下午
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/sys/position")
public class SysPositionController {

    private ISysPositionService sysPositionService;
    @Autowired
    public void setSysPositionService(ISysPositionService sysPositionService) {
        this.sysPositionService = sysPositionService;
    }

    @AutoLog(value = "职务表-分页列表查询")
    @ApiOperation(value = "职务表-分页列表查询", notes = "职务表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<SysPosition>> queryPageList(SysPosition position, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
        Result<IPage<SysPosition>> result = new Result<>();
        QueryWrapper<SysPosition> queryWrapper = QueryGenerator.initQueryWrapper(position, request.getParameterMap());
        Page<SysPosition> page = new Page<>(pageNo, pageSize);
        Page<SysPosition> pageList = sysPositionService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

}
