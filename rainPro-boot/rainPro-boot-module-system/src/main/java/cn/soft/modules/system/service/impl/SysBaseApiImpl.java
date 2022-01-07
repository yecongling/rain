package cn.soft.modules.system.service.impl;

import cn.soft.common.api.dto.OnlineAuthDTO;
import cn.soft.common.api.dto.message.*;
import cn.soft.common.system.vo.*;
import cn.soft.modules.system.service.ISysBaseAPI;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 底层共通业务API，提供其他独立模块调用
 */
@Slf4j
@Service
public class SysBaseApiImpl implements ISysBaseAPI {

    @Override
    public Set<String> queryUserRoles(String username) {
        return null;
    }

    @Override
    public Set<String> queryUserAuths(String username) {
        return null;
    }

    @Override
    public DynamicDataSourceModel getDynamicDbSourceById(String dbSourceId) {
        return null;
    }

    @Override
    public DynamicDataSourceModel getDynamicDbSourceByCode(String dbSourceCode) {
        return null;
    }

    @Override
    public LoginUser getUserByName(String username) {
        return null;
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

    @Override
    public Map<String, List<DictModel>> translateManyDict(String dictCodes, String keys) {
        return null;
    }

    @Override
    public List<DictModel> translateDictFromTableByKeys(String table, String text, String code, String keys) {
        return null;
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

    @Override
    public Set<String> getUserRoleSet(String username) {
        return null;
    }

    @Override
    public Set<String> getUserPermissionSet(String username) {
        return null;
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
