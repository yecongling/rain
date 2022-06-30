package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.system.query.QueryGenerator;
import cn.soft.modules.system.entity.SysDictItem;
import cn.soft.modules.system.service.ISysDictItemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 字典条目前端控制器 （字典详细数据）
 */
@RestController
@RequestMapping("/sys/dictItem")
@Slf4j
public class SysDictItemController {

    private ISysDictItemService sysDictItemService;

    @Autowired
    public void setSysDictItemService(ISysDictItemService sysDictItemService) {
        this.sysDictItemService = sysDictItemService;
    }

    /**
     * 分页查询字典数据
     *
     * @param sysDictItem 字典条目对象
     * @param pageNo      页数
     * @param pageSize    每页条数
     * @param req         请求
     * @return /
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<SysDictItem>> queryPageList(SysDictItem sysDictItem, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<IPage<SysDictItem>> result = new Result<>();
        QueryWrapper<SysDictItem> queryWrapper = QueryGenerator.initQueryWrapper(sysDictItem, req.getParameterMap());
        queryWrapper.orderByAsc("sort_order");
        Page<SysDictItem> page = new Page<>(pageNo, pageSize);
        IPage<SysDictItem> pageList = sysDictItemService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
}
