package cn.soft.modules.system.controller;

import cn.hutool.core.util.RandomUtil;
import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.system.vo.LoginUser;
import cn.soft.common.util.MD5Util;
import cn.soft.common.util.PasswordUtil;
import cn.soft.common.util.RedisUtil;
import cn.soft.modules.base.service.BaseCommonService;
import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.model.SysLoginModel;
import cn.soft.modules.system.service.*;
import cn.soft.modules.system.util.RandImageUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录controller
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "用户登录")
@Slf4j
public class SysLoginController {

    /**
     * 用户service
     */
    private ISysUserService sysUserService;
    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 基础API
     */
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    public void setSysBaseAPI(ISysBaseAPI sysBaseAPI) {
        this.sysBaseAPI = sysBaseAPI;
    }

    /**
     * 系统日志服务实现
     */
    private ISysLogService sysLogService;
    @Autowired
    public void setSysLogService(ISysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * Redis工具类
     */
    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 部门服务实现类
     */
    private ISysDepartService sysDepartService;
    @Autowired
    public void setSysDepartService(ISysDepartService sysDepartService) {
        this.sysDepartService = sysDepartService;
    }

    /**
     * 租户服务实现类
     */
    private ISysTenantService sysTenantService;
    @Autowired
    public void setSysTenantService(ISysTenantService sysTenantService) {
        this.sysTenantService = sysTenantService;
    }

    /**
     * 字典服务实现类
     */
    private ISysDictService sysDictService;
    @Autowired
    public void setSysDictService(ISysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }

    /**
     * 基础通用服务实现
     */
    private BaseCommonService baseCommonService;
    @Autowired
    public void setBaseCommonService(BaseCommonService baseCommonService) {
        this.baseCommonService = baseCommonService;
    }

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    /**
     * 后台生成图形验证码
     * @param key 时间key
     * @return 返回结果
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage/{key}")
    public Result<String> getRandomImage(@PathVariable String key) {
        Result<String> result = new Result<>();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            String lowerCase = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCase + key, "utf-8");
            redisUtil.set(realKey, lowerCase, 60);
            String base64 = RandImageUtil.generate(code);
            result.setSuccess(true);
            result.setResult(base64);
        } catch (Exception e) {
            result.error500("获取验证码出错" + e.getMessage());
            log.error("获取验证码出错，原因：" + e.getMessage());
        }
        return result;
    }

    /**
     * 登录入口
     * @param sysLoginModel 登录数据对象
     * @return 返回登录成功或失败
     */
    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel){
        Result<JSONObject> result = new Result<>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();

        // 验证码
        String captcha = sysLoginModel.getCaptcha();
        if (captcha == null) {
            return result.error500("验证码无效");
        }
        String lowerCase = captcha.toLowerCase();
        String realKey = MD5Util.MD5Encode(lowerCase + sysLoginModel.getCheckKey(), "utf-8");
        Object checkCode = redisUtil.get(realKey);
        if (checkCode == null || !checkCode.toString().equals(lowerCase)) {
            return result.error500("验证码错误");
        }
        // 校验用户是否有效
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        // 根据用户名查询出用户
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }
        // 校验用户名或密码是否正确
        String userPassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
        String sysUserPassword = sysUser.getPassword();
        if (!sysUserPassword.equals(userPassword)) {
            return result.error500("用户名或密码错误");
        }
        // 用户登记信息
        userInfo(sysUser, result);
        // 登录成功删除Redis中的验证码
        redisUtil.del(realKey);
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);
        baseCommonService.addLog("用户名：" + username + "，登录成功！", CommonConstant.LOG_TYPE_1, null, loginUser);
        return null;
    }

    /**
     * 加载对应的用户信息
     * @param sysUser 登录用后
     * @param result 结果
     * @return /
     */
    private Result<JSONObject> userInfo(SysUser sysUser, Result<JSONObject> result) {
        return result;
    }
}
