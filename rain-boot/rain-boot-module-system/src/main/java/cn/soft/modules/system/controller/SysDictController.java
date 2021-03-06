package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CacheConstant;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.system.query.QueryGenerator;
import cn.soft.common.system.vo.DictModel;
import cn.soft.modules.system.entity.SysDict;
import cn.soft.modules.system.service.ISysDictItemService;
import cn.soft.modules.system.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @ClassName SysDictController
 * @Description 字典表前端控制器
 * @Author yeconglin
 * @Date 2022/2/22 9:42 下午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/sys/dict")
@Slf4j
public class SysDictController {

    private ISysDictService sysDictService;

    @Autowired
    public void setSysDictService(ISysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }

    private ISysDictItemService sysDictItemService;

    @Autowired
    public void setSysDictItemService(ISysDictItemService sysDictItemService) {
        this.sysDictItemService = sysDictItemService;
    }


    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 分页查询字典列表数据
     *
     * @param sysDict  字典对象
     * @param pageNo   页数
     * @param pageSize 每页条数
     * @param request  请求
     * @return /
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<SysDict>> queryPageList(SysDict sysDict, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
        Result<IPage<SysDict>> result = new Result<>();
        QueryWrapper<SysDict> queryWrapper = QueryGenerator.initQueryWrapper(sysDict, request.getParameterMap());
        Page<SysDict> page = new Page<>(pageNo, pageSize);
        Page<SysDict> pageList = sysDictService.page(page, queryWrapper);
        log.debug("查询当前页：" + pageList.getCurrent());
        log.debug("查询当前页数量：" + pageList.getSize());
        log.debug("查询结果数量：" + pageList.getRecords().size());
        log.debug("数据总数：" + pageList.getTotal());
        result.setResult(pageList);
        result.setSuccess(true);
        return result;
    }

    /**
     * 获取字典数据
     *
     * @param dictCode 字典编码
     * @param sign     签名
     * @param request  请求
     * @return /
     */
    @RequestMapping(value = "/getDictItems/{dictCode}", method = RequestMethod.GET)
    public Result<List<DictModel>> getDictItems(@PathVariable String dictCode, @RequestParam(value = "sign", required = false) String sign, HttpServletRequest request) {
        Result<List<DictModel>> result = new Result<>();
        try {
            List<DictModel> list = sysDictService.getDictItems(dictCode);
            if (list == null) {
                result.error500("字典Code格式不正确！");
                return result;
            }
            result.setSuccess(true);
            result.setResult(list);
        } catch (Exception e) {
            result.error500("操作失败");
            return result;
        }
        return result;
    }

    /**
     * 新增字典
     *
     * @param sysDict 前台传过来的封装的字典对象
     * @return /
     */
    @PostMapping("/add")
    public Result<SysDict> add(@RequestBody SysDict sysDict) {
        Result<SysDict> result = new Result<>();
        try {
            sysDict.setCreateTime(new Date());
            sysDict.setDelFlag(CommonConstant.DEL_FLAG_0);
            sysDictService.save(sysDict);
            result.success("操作成功");
        } catch (Exception e) {
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑字典
     *
     * @param sysDict 字典对象
     * @return /
     */
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<SysDict> edit(@RequestBody SysDict sysDict) {
        Result<SysDict> result = new Result<>();
        SysDict dict = sysDictService.getById(sysDict.getId());
        if (dict == null) {
            result.error500("未找到对应的字典数据");
        } else {
            sysDict.setUpdateTime(new Date());
            boolean ok = sysDictService.updateById(sysDict);
            if (ok) {
                result.success("编辑成功！");
            } else {
                result.error500("编辑失败！");
            }
        }
        return result;
    }

    /**
     * 删除
     *
     * @param id 字典id
     * @return /
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @CacheEvict(value = {CacheConstant.SYS_DICT_CACHE, CacheConstant.SYS_ENABLE_DICT_CACHE}, allEntries = true)
    public Result<SysDict> delete(@RequestParam(name = "id", required = true) String id) {
        Result<SysDict> result = new Result<>();
        boolean ok = sysDictService.removeById(id);
        if (ok) {
            result.success("删除成功!");
        } else {
            result.error500("删除失败!");
        }
        return result;
    }

    /**
     * 获取被删除的字典
     *
     * @return /
     */
    @RequestMapping(value = "/deleteList", method = RequestMethod.GET)
    public Result<List<SysDict>> deleteList() {
        Result<List<SysDict>> result = new Result<>();
        List<SysDict> list = sysDictService.queryDeleteList();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 刷新缓存
     *
     * @return /
     */
    @RequestMapping("/refreshCache")
    public Result<?> refreshCache() {
        Result<?> result = new Result<>();
        // 清空字典缓存
        Set<String> keys = redisTemplate.keys(CacheConstant.SYS_DICT_CACHE + "*");
        Set<String> keys1 = redisTemplate.keys(CacheConstant.SYS_ENABLE_DICT_CACHE + "*");
        Set<String> keys2 = redisTemplate.keys(CacheConstant.SYS_DICT_TABLE_CACHE + "*");
        Set<String> keys3 = redisTemplate.keys(CacheConstant.SYS_DICT_TABLE_BY_KEYS_CACHE + "*");
        Set<String> keys4 = redisTemplate.keys(CacheConstant.SYS_DEPARTS_CACHE + "*");
        Set<String> keys5 = redisTemplate.keys(CacheConstant.SYS_DEPART_IDS_CACHE + "*");
        redisTemplate.delete(keys);
        redisTemplate.delete(keys1);
        redisTemplate.delete(keys2);
        redisTemplate.delete(keys3);
        redisTemplate.delete(keys4);
        redisTemplate.delete(keys5);
        return result;
    }

}
