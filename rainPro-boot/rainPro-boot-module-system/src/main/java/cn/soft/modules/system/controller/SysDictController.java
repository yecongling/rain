package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.system.vo.DictModel;
import cn.soft.modules.system.service.ISysDictItemService;
import cn.soft.modules.system.service.ISysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            log.debug(result.toString());
        } catch (Exception e) {
            result.error500("操作失败");
            return result;
        }
        return result;
    }
}
