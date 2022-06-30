package cn.soft.modules.system.service;

import cn.soft.common.api.CommonAPI;
import cn.soft.common.api.dto.OnlineAuthDTO;
import cn.soft.common.api.dto.message.*;
import cn.soft.common.system.vo.*;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 底层共通业务API，提供其他独立模块调用
 */
public interface ISysBaseAPI extends CommonAPI {

    /**
     *  发送系统消息
     * @param messageDTO 使用构造器赋值参数 如果不设置category(消息类型)则默认为2 发送系统消息
     */
    void sendSysAnnouncement(MessageDTO messageDTO);

    /**
     * 发送消息  附带业务参数
     * @param message 使用构造器赋值参数
     */
    void sendBusAnnouncement(BusMessageDTO message);

    /**
     * 通过模板发送消息
     * @param message 使用构造器赋值参数
     */
    void sendTemplateAnnouncement(TemplateMessageDTO message);

    /**
     * 通过模板发送消息 附带业务参数
     * @param message 使用构造器赋值参数
     */
    void sendBusTemplateAnnouncement(BusTemplateMessageDTO message);

    /**
     * 通过消息中心模板，生成推送消息
     * @param templateDTO 使用构造器赋值参数
     */
    String parseTemplateByCode(TemplateDTO templateDTO);

    /**
     * 根据用户id查询用户信息
     * @param id 用户ID
     * @return 返回用户信息
     */
    LoginUser getUserById(String id);

    /**
     * 通过用户账号查询角色集合
     * @param username 用户账号
     * @return 返回角色集合
     */
    List<String> getRolesByUsername(String username);

    /**
     * 通过用户账号查询部门集合
     * @param username 用户账号
     * @return 部门集合
     */
    List<String> getDepartIdsByUsername(String username);

    /**
     * 通过用户账号查询部门名字
     * @param username 用户账号
     * @return 部门名字集合
     */
    List<String> getDepartNamesByUsername(String username);

    /**
     * 查询所有父级字典，按照create_time排序
     * @return 字典集合
     */
    List<DictModel> queryAllDic();

    /**
     * 查询所有分类字典
     * @return 分字典集合
     */
    List<SysCategoryModel> queryAllDSysCategory();

    /**
     * 查询所有部门  作为字典信息
     * @return 部门集合
     */
    List<DictModel> queryAllDepartBackDictModel();

    /**
     * 根据业务类型及业务ID修改消息已读
     * @param busType 业务类型
     * @param busId 业务ID
     */
    void updateSysAnnounceReadFlag(String busType, String busId);

    /**
     * 查询表字典  支持过滤数据
     * @param table 表明
     * @param text 文本
     * @param code 码
     * @param filterSql 过滤sql
     * @return 返回字典集合
     */
    List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql);

    /**
     * 查询指定table的text code 获取字典
     * @param table 表
     * @param text /
     * @param code /
     * @param keyArray /
     * @return 字典集合
     */
    List<String> queryTableDictByKeys(String table, String text, String code, String[] keyArray);

    /**
     * 查询所有用户 返回ComboModel
     * @return /
     */
    List<ComboModel> queryAllUserBackCombo();

    /**
     * 分页查询用户  返回JSONObject
     * @param userIds 用户ID
     * @param pageNo 页数
     * @param pageSize 每页条数
     * @return /
     */
    JSONObject queryAllUser(String userIds, Integer pageNo, Integer pageSize);

    /**
     * 获取所有角色
     * @return /
     */
    List<ComboModel> queryAllRole();

    /**
     * 获取所有角色 带参数
     * @param roleIds 默认选中角色
     * @return /
     */
    List<ComboModel> queryAllRole(String[] roleIds);

    /**
     * 通过用户账号查询角色ID集合
     * @param username 用户账号
     * @return /
     */
    List<String> getRoleIdsByUsername(String username);

    /**
     * 通过部门编号查询部门ID
     * @param orgCode 部门编号
     * @return /
     */
    String getDepartIdsByOrgCode(String orgCode);

    /**
     * 查询所有部门
     * @return /
     */
    List<SysDepartModel> getAllSysDepart();

    /**
     * 查找父级部门
     * @param departId 部门ID
     * @return /
     */
    DictModel getParentDepartId(String departId);

    /**
     * 根据id获取所有参与用户
     * @param userIds id
     * @return /
     */
    List<LoginUser> queryAllUserByIds(String[] userIds);

    /**
     * 将会议签到信息推动到预览
     * @param  userId id
     */
    void meetingSignWebsocket(String userId);

    /**
     * 根据name获取所有参与用户
     * @param  userNames 用户
     * @return /
     */
    List<LoginUser> queryUserByNames(String[] userNames);

    /**
     * 获取用户的角色集合
     * @param username 用户账号
     * @return /
     */
    Set<String> getUserRoleSet(String username);

    /**
     * 获取用户的权限集合
     * @param username 用户
     * @return /
     */
    Set<String> getUserPermissionSet(String username);

    /**
     * 判断是否有online访问的权限
     * @param onlineAuthDTO 访问权限
     * @return /
     */
    boolean hasOnlineAuth(OnlineAuthDTO onlineAuthDTO);

    /**
     * 通过部门id获取部门全部信息
     * @param id 部门ID
     * @return /
     */
    SysDepartModel selectAllById(String id);

    /**
     * 根据用户id查询用户所属公司下所有用户ids
     * @param userId 用户
     * @return /
     */
    List<String> queryDeptUsersByUserId(String userId);

    /**
     * 根据多个用户账号(逗号分隔)，查询返回多个用户信息
     * @param usernames 用户
     * @return /
     */
    List<JSONObject> queryUsersByUsernames(String usernames);

    /**
     * 根据多个用户ID(逗号分隔)，查询返回多个用户信息
     * @param ids /
     * @return /
     */
    List<JSONObject> queryUsersByIds(String ids);

    /**
     * 根据多个部门编码(逗号分隔)，查询返回多个部门信息
     * @param orgCodes /
     * @return /
     */
    List<JSONObject> queryDepartsByOrgCodes(String orgCodes);

    /**
     * 根据多个部门id(逗号分隔)，查询返回多个部门信息
     * @param ids /
     * @return /
     */
    List<JSONObject> queryDepartsByIds(String ids);

    /**
     * 发送邮件消息
     * @param email 邮件
     * @param title 标题
     * @param content 内容
     */
    void sendEmailMsg(String email,String title,String content);

    /**
     * 获取公司下级部门和公司下所有用户信息
     * @param orgCode 部门ID
     * @return /
     */
    List<Map> getDeptUserByOrgCode(String orgCode);

    /**
     * 查询分类字典翻译
     * @param ids /
     * @return /
     */
    List<String> loadCategoryDictItem(String ids);

    /**
     * 根据字典code加载字典text
     *
     * @param dictCode 顺序：tableName,text,code
     * @param keys     要查询的key
     * @return /
     */
    List<String> loadDictItem(String dictCode, String keys);

    /**
     * 根据字典code查询字典项
     *
     * @param dictCode 要查询的key
     * @return /
     */
    List<DictModel> getDictItems(String dictCode);

    /**
     *  根据多个字典code查询多个字典项
     * @param dictCodeList /
     * @return key = dictCode ； value=对应的字典项
     */
    Map<String, List<DictModel>> getManyDictItems(List<String> dictCodeList);

    /**
     * 【JSearchSelectTag下拉搜索组件专用接口】
     * 大数据量的字典表 走异步加载  即前端输入内容过滤数据
     *
     * @param dictCode 字典code格式：table,text,code
     * @param keyword 过滤关键字
     * @param pageSize 页大小
     * @return /
     */
    List<DictModel> loadDictItemByKeyword(String dictCode, String keyword, Integer pageSize);
}
