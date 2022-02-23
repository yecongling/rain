package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.util.SqlInjectionUtil;
import cn.soft.modules.system.mapper.SysDictMapper;
import cn.soft.modules.system.model.DuplicateCheckVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DuplicateCheckController
 * @Description 重复校验工具
 * @Author 叶丛林
 * @Date 2022/2/23 9:52 下午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/sys/duplicate")
@Slf4j
@Api(tags = "重复校验")
public class DuplicateCheckController {

    private SysDictMapper sysDictMapper;
    @Autowired
    public void setSysDictMapper(SysDictMapper sysDictMapper) {
        this.sysDictMapper = sysDictMapper;
    }

    /**
     * 校验数据是否在系统中存在
     *
     * @return /
     */
    @GetMapping("/check")
    @ApiOperation("重复校验接口")
    public Result<Object> doDuplicateCheck(DuplicateCheckVo duplicateCheckVo) {
        Long num;
        final String[] sqlInjCheck = {duplicateCheckVo.getTableName(),duplicateCheckVo.getFieldName()};
        SqlInjectionUtil.filterContent(sqlInjCheck);
        if (StringUtils.isNotBlank(duplicateCheckVo.getDataId())) {
            // 传入数据ID则为编辑页面校验
            num = sysDictMapper.duplicateCheckCountSql(duplicateCheckVo);
        } else {
            num = sysDictMapper.duplicateCheckCountSqlNoDataId(duplicateCheckVo);
        }
        if (num == null || num == 0) {
            return Result.OK("该值可用");
        } else {
            return Result.error("该值不可用，系统中已存在！");
        }
    }
}
