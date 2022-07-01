package cn.soft.modules.system.service.impl;

import cn.soft.common.api.dto.OnlineAuthDTO;
import cn.soft.common.api.dto.message.*;
import cn.soft.common.constant.CacheConstant;
import cn.soft.common.system.vo.*;
import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.system.entity.SysPermission;
import cn.soft.modules.system.entity.SysUser;
import cn.soft.modules.system.mapper.SysPermissionMapper;
import cn.soft.modules.system.mapper.SysUserMapper;
import cn.soft.modules.system.mapper.SysUserRoleMapper;
import cn.soft.modules.system.service.ISysBaseAPI;
import cn.soft.modules.system.service.ISysDictService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 底层共通业务API，提供其他独立模块调用
 */
@Slf4j
@Service
public class SysBaseApiImpl implements ISysBaseAPI {

    private SysUserRoleMapper userRoleMapper;

    @Autowired
    public void setUserRoleMapper(SysUserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    private SysPermissionMapper permissionMapper;

    @Autowired
    public void setPermissionMapper(SysPermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    private SysUserMapper userMapper;
    @Autowired
    public void setUserMapper(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private ISysDictService sysDictService;
    @Autowired
    public void setSysDictService(ISysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }

    /**
     * 查询用户角色信息
     *
     * @param username /
     * @return /
     */
    @Override
    public Set<String> queryUserRoles(String username) {
        return getUserRoleSet(username);
    }

    /**
     * 查询用户拥有的权限集合 common api 里面的接口实现
     *
     * @param username 用户
     * @return /
     */
    @Override
    public Set<String> queryUserAuths(String username) {
        return getUserPermissionSet(username);
    }

    @Override
    public DynamicDataSourceModel getDynamicDbSourceById(String dbSourceId) {
        return null;
    }

    @Override
    public DynamicDataSourceModel getDynamicDbSourceByCode(String dbSourceCode) {
        return null;
    }

    /**
     * 查询用户
     *
     * @param username /
     * @return /
     */
    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_USERS_CACHE, key = "#username")
    public LoginUser getUserByName(String username) {
        if (ConvertUtils.isEmpty(username)) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        SysUser sysUser = userMapper.getUserByName(username);
        if (sysUser == null) {
            return null;
        }
        BeanUtils.copyProperties(sysUser, loginUser);
        return loginUser;
    }

    @Override
    public String translateDictFromTable(String table, String text, String code, String key) {
        return null;
    }

    @Override
    public String translateDict(String code, String key) {
        return null;
    }

    @Override
    public List<SysPermissionDataRuleModel> queryPermissionDataRule(String component, String requestPath, String username) {
        return null;
    }

    @Override
    public SysUserCacheInfo getCacheUser(String username) {
        return null;
    }

    @Override
    public List<DictModel> queryDictItemsByCode(String code) {
        return null;
    }

    @Override
    public List<DictModel> queryEnableDictItemsByCode(String code) {
        return null;
    }

    @Override
    public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
        return null;
    }

    /**
     * 14 普通字典的翻译，根据多个dictCode和多条数据，多个以逗号分割
     *
     * @param dictCodes 例如：user_status,sex
     * @param keys      例如：1,2,0
     * @return /
     */
    @Override
    public Map<String, List<DictModel>> translateManyDict(String dictCodes, String keys) {
        List<String> dictCodeList = Arrays.asList(dictCodes.split(","));
        List<String> values = Arrays.asList(keys.split(","));
        return sysDictService.queryManyDictByKeys(dictCodeList, values);
    }

    /**
     * 15 字典表的 翻译，可批量
     *
     * @param table /
     * @param text /
     * @param code /
     * @param keys  多个用逗号分割
     * @return
     */
    @Override
    public List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys) {
        return sysDictService.queryTableDictTextByKeys(table, text, code, Arrays.asList(keys.split(",")));
    }

    @Override
    public void sendSysAnnouncement(MessageDTO messageDTO) {

    }

    @Override
    public void sendBusAnnouncement(BusMessageDTO message) {

    }

    @Override
    public void sendTemplateAnnouncement(TemplateMessageDTO message) {

    }

    @Override
    public void sendBusTemplateAnnouncement(BusTemplateMessageDTO message) {

    }

    @Override
    public String parseTemplateByCode(TemplateDTO templateDTO) {
        return null;
    }

    @Override
    public LoginUser getUserById(String id) {
        return null;
    }

    @Override
    public List<String> getRolesByUsername(String username) {
        return null;
    }

    @Override
    public List<String> getDepartIdsByUsername(String username) {
        return null;
    }

    @Override
    public List<String> getDepartNamesByUsername(String username) {
        return null;
    }

    @Override
    public List<DictModel> queryAllDic() {
        return null;
    }

    @Override
    public List<SysCategoryModel> queryAllDSysCategory() {
        return null;
    }

    @Override
    public List<DictModel> queryAllDepartBackDictModel() {
        return null;
    }

    @Override
    public void updateSysAnnounceReadFlag(String busType, String busId) {

    }

    @Override
    public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql) {
        return null;
    }

    @Override
    public List<String> queryTableDictByKeys(String table, String text, String code, String[] keyArray) {
        return null;
    }

    @Override
    public List<ComboModel> queryAllUserBackCombo() {
        return null;
    }

    @Override
    public JSONObject queryAllUser(String userIds, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public List<ComboModel> queryAllRole() {
        return null;
    }

    @Override
    public List<ComboModel> queryAllRole(String[] roleIds) {
        return null;
    }

    @Override
    public List<String> getRoleIdsByUsername(String username) {
        return null;
    }

    @Override
    public String getDepartIdsByOrgCode(String orgCode) {
        return null;
    }

    @Override
    public List<SysDepartModel> getAllSysDepart() {
        return null;
    }

    @Override
    public DictModel getParentDepartId(String departId) {
        return null;
    }

    @Override
    public List<LoginUser> queryAllUserByIds(String[] userIds) {
        return null;
    }

    @Override
    public void meetingSignWebsocket(String userId) {

    }

    @Override
    public List<LoginUser> queryUserByNames(String[] userNames) {
        return null;
    }

    /**
     * 查询用户拥有的角色集合
     *
     * @param username 用户账号
     * @return /
     */
    @Override
    public Set<String> getUserRoleSet(String username) {
        // 查询用户拥有的角色集合
        List<String> roles = userRoleMapper.getRoleByUserName(username);
        log.info("-------通过数据库读取用户拥有的角色Rules------username： " + username + ",Roles size: " + (roles == null ? 0 : roles.size()));
        return new HashSet<>(roles);
    }

    /**
     * 查询用户拥有的权限集合
     *
     * @param username 用户
     * @return /
     */
    @Override
    public Set<String> getUserPermissionSet(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<SysPermission> permissionList = permissionMapper.queryByUser(username);
        for (SysPermission po : permissionList) {
            if (ConvertUtils.isNotEmpty(po.getPerms())) {
                permissionSet.add(po.getPerms());
            }
        }
        log.info("-------通过数据库读取用户拥有的权限Perms------username： " + username + ",Perms size: " + permissionSet.size());
        return permissionSet;
    }

    @Override
    public boolean hasOnlineAuth(OnlineAuthDTO onlineAuthDTO) {
        return false;
    }

    @Override
    public SysDepartModel selectAllById(String id) {
        return null;
    }

    @Override
    public List<String> queryDeptUsersByUserId(String userId) {
        return null;
    }

    @Override
    public List<JSONObject> queryUsersByUsernames(String usernames) {
        return null;
    }

    @Override
    public List<JSONObject> queryUsersByIds(String ids) {
        return null;
    }

    @Override
    public List<JSONObject> queryDepartsByOrgCodes(String orgCodes) {
        return null;
    }

    @Override
    public List<JSONObject> queryDepartsByIds(String ids) {
        return null;
    }

    @Override
    public void sendEmailMsg(String email, String title, String content) {

    }

    @Override
    public List<Map> getDeptUserByOrgCode(String orgCode) {
        return null;
    }

    @Override
    public List<String> loadCategoryDictItem(String ids) {
        return null;
    }

    @Override
    public List<String> loadDictItem(String dictCode, String keys) {
        return null;
    }

    @Override
    public List<DictModel> getDictItems(String dictCode) {
        return null;
    }

    @Override
    public Map<String, List<DictModel>> getManyDictItems(List<String> dictCodeList) {
        return null;
    }

    @Override
    public List<DictModel> loadDictItemByKeyword(String dictCode, String keyword, Integer pageSize) {
        return null;
    }
}
