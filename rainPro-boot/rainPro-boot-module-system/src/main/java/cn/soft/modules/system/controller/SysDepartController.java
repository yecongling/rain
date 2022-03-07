package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.system.model.SysDepartTreeModel;
import cn.soft.modules.system.service.ISysDepartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SysDepartController
 * @Description 部门表 前端控制器
 * @Author 叶丛林
 * @Date 2022/3/7 9:21 下午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/sys/sysDepart")
@Slf4j
public class SysDepartController {

    private ISysDepartService sysDepartService;

    @Autowired
    public void setSysDepartService(ISysDepartService sysDepartService) {
        this.sysDepartService = sysDepartService;
    }

    /**
     * 查询部门数据 并以树型结构数据返回给前端
     *
     * @param ids 部门ID
     * @return 返回部门数据
     */
    @RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
    public Result<List<SysDepartTreeModel>> queryTreeList(@RequestParam(name = "ids", required = false) String ids) {
        Result<List<SysDepartTreeModel>> result = new Result<>();
        try {
            if (ConvertUtils.isNotEmpty(ids)) {
                List<SysDepartTreeModel> departList = sysDepartService.queryTreeList(ids);
                result.setResult(departList);
            } else {
                List<SysDepartTreeModel> list = sysDepartService.queryTreeList();
                result.setResult(list);
            }
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
}
