package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.util.PasswordUtil;
import cn.soft.common.util.RedisUtil;
import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.base.service.BaseCommonService;
import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.service.ISysLogService;
import cn.soft.modules.system.service.ISysUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private ISysUserService userService;
    @Autowired
    public void setUserService(ISysUserService userService) {
        this.userService = userService;
    }

    /**
     * 注入日志服务实现类
     */
    private ISysLogService logService;
    @Autowired
    public void setLogService(ISysLogService logService) {
        this.logService = logService;
    }

    /**
     * 基础通用服务实现
     */
    private BaseCommonService baseCommonService;
    @Autowired
    public void setBaseCommonService(BaseCommonService baseCommonService) {
        this.baseCommonService = baseCommonService;
    }

    /**
     * 检测用户账号是否唯一
     * @param sysUser 用户信息
     * @return 返回是否唯一
     */
    @RequestMapping(value = "/checkOnlyUser", method = RequestMethod.GET)
    public Result<Boolean> checkOnlyUser(SysUser sysUser) {
        Result<Boolean> result = new Result<>();
        // 默认设置唯一
        result.setResult(true);
        try {
            // 通过传入信息查询用户信息
            sysUser.setPassword(null);
            SysUser user = userService.getOne(new QueryWrapper<>(sysUser));
            if (user != null) {
                result.setSuccess(false);
                result.setMessage("用户账号已存在，请重新输入账号");
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    /**
     * 用户注册接口
     * @param jsonObject 参数
     * @param user 用户信息
     * @return 返回注册结果
     */
    @PostMapping("/register")
    public Result<JSONObject> userRegister(@RequestBody JSONObject jsonObject, SysUser user) {
        Result<JSONObject> result = new Result<>();
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        SysUser sysUser = userService.getUserByName(username);
        if (sysUser != null) {
            result.setMessage("用户名已注册，请输入其他用户名");
            result.setSuccess(false);
            return result;
        }
        // 创建用户信息
        try {
            // 设置创建时间
            user.setCreateTime(new Date());
            String salt = ConvertUtils.randomGen(8);
            String encrypt = PasswordUtil.encrypt(username, password, salt);
            user.setSalt(salt);
            user.setUsername(username);
            user.setRealName(username);
            user.setPassword(encrypt);
            user.setStatus(CommonConstant.USER_UNFREEZE);
            user.setDelFlag(CommonConstant.DEL_FLAG_0);
            user.setActivitySync(CommonConstant.ACT_SYNC_0);
            // 默认临时角色
            userService.addUserWithRole(user, "ee8626f80f7c2619917b6236f3a7f02b");
        } catch (Exception e) {
            result.error500("注册失败，原因：" + e.getMessage());
            log.error("用户注册失败，原因：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取访问量
     * @return 返回数据
     */
    @GetMapping("/logInfo")
    public Result<JSONObject> logInfo() {
        Result<JSONObject> result = new Result<>();
        return result;
    }

    /**
     * 获取访问量
     * @return 返回数据
     */
    public Result<List<Map<String, Object>>> visitInfo() {
        Result<List<Map<String, Object>>> result = new Result<>();
        return result;
    }
}
