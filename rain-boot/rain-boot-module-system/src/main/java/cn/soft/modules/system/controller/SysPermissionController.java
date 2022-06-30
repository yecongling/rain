package cn.soft.modules.system.controller;

import cn.soft.common.api.vo.Result;
import cn.soft.common.constant.CommonConstant;
import cn.soft.common.constant.enums.RoleIndexConfigEnum;
import cn.soft.common.system.vo.LoginUser;
import cn.soft.common.util.ConvertUtils;
import cn.soft.common.util.MD5Util;
import cn.soft.config.RainProBaseConfig;
import cn.soft.modules.system.entity.SysPermission;
import cn.soft.modules.system.model.SysPermissionTree;
import cn.soft.modules.system.model.TreeModel;
import cn.soft.modules.system.service.ISysPermissionService;
import cn.soft.modules.system.service.ISysUserService;
import cn.soft.modules.system.util.PermissionDataUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单权限  前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

    /**
     * 注入系统基础配置
     */
    private RainProBaseConfig rainProBaseConfig;

    @Autowired
    public void setRainProBaseConfig(RainProBaseConfig rainProBaseConfig) {
        this.rainProBaseConfig = rainProBaseConfig;
    }

    /**
     * 注入用户服务
     */
    private ISysUserService userService;

    @Autowired
    public void setUserService(ISysUserService userService) {
        this.userService = userService;
    }

    /**
     * 菜单权限业务实现
     */
    private ISysPermissionService permissionService;

    @Autowired
    public void setPermissionService(ISysPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 列出所有菜单
     *
     * @return 返回构建成菜单树形结构的数据
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<SysPermissionTree>> list() {
        Result<List<SysPermissionTree>> result = new Result<>();
        try {
            LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<>();
            query.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
            query.orderByAsc(SysPermission::getSortNo);
            List<SysPermission> list = permissionService.list(query);
            ArrayList<SysPermissionTree> trees = new ArrayList<>();
            getTreeList(trees, list, null);
            result.setResult(trees);
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取全部的权限树
     *
     * @return \
     */
    @GetMapping("/queryTreeList")
    public Result<Map<String, Object>> queryTreeList() {
        Result<Map<String, Object>> result = new Result<>();
        // 全部权限ids
        List<String> ids = new ArrayList<>();
        try {
            LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<>();
            query.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
            query.orderByAsc(SysPermission::getSortNo);
            List<SysPermission> list = permissionService.list(query);
            for (SysPermission permission : list) {
                ids.add(permission.getId());
            }
            List<TreeModel> treeList = new ArrayList<>();
            getTreeModeList(treeList, list, null);
            Map<String, Object> map = new HashMap<>();
            map.put("treeList", treeList);
            map.put("ids", ids);
            result.setResult(map);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setCode(CommonConstant.SC_INTERNAL_SERVER_ERROR_500);
        }
        return result;
    }

    /**
     * 将权限菜单的平级结构构建成树形结构
     *
     * @param treeList 树形列表
     * @param metaList 系统权限菜单列表
     * @param temp     临时替换变量
     */
    private void getTreeModeList(List<TreeModel> treeList, List<SysPermission> metaList, TreeModel temp) {
        for (SysPermission permission : metaList) {
            String parentId = permission.getParentId();
            TreeModel treeModel = new TreeModel(permission);
            if (temp == null && ConvertUtils.isEmpty(parentId)) {
                treeList.add(treeModel);
                if (!treeModel.getIsLeaf()) {
                    getTreeModeList(treeList, metaList, treeModel);
                }
            } else if (temp != null && parentId != null && parentId.equals(temp.getKey())) {
                temp.getChildren().add(treeModel);
                if (!treeModel.getIsLeaf()) {
                    getTreeModeList(treeList, metaList, treeModel);
                }
            }
        }
    }

    /**
     * 查询用户拥有的菜单权限和按钮权限
     *
     * @return 返回菜单权限和按钮权限
     */
    @GetMapping("/getUserPermissionByToken")
    public Result<?> getUserPermissionByToken() {
        Result<JSONObject> result = new Result<>();
        try {
            // 从登录Shiro获取登录的用户
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            if (ConvertUtils.isEmpty(loginUser)) {
                return Result.error("请登录系统!");
            }
            List<SysPermission> permissions = permissionService.queryByUser(loginUser.getUsername());
            // 添加首页路由
            if (!PermissionDataUtil.hasIndexPage(permissions)) {
                SysPermission indexMenu = permissionService.list(new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getName, "首页")).get(0);
                permissions.add(0, indexMenu);
            }
            // 自定义首页地址
            List<String> roles = userService.getRole(loginUser.getUsername());
            String compUrl = RoleIndexConfigEnum.getIndexByRoles(roles);
            if (StringUtils.isNotBlank(compUrl)) {
                List<SysPermission> menus = permissions.stream().filter(sysPermission -> "首页".equals(sysPermission.getName())).collect(Collectors.toList());
                menus.get(0).setComponent(compUrl);
            }
            JSONObject json = new JSONObject();
            JSONArray menuJsonArray = new JSONArray();
            this.getPermissionJsonArray(menuJsonArray, permissions, null);
            // 一级菜单下的子菜单全部隐藏路由，则一级菜单不显示出来
            this.handleFirstLevelMenuHidden(menuJsonArray);

            JSONArray authJsonArray = new JSONArray();
            this.getAuthJsonArray(authJsonArray, permissions);
            // 查询所有的权限
            LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysPermission::getDelFlag, CommonConstant.DEL_FLAG_0);
            wrapper.eq(SysPermission::getMenuType, CommonConstant.MENU_TYPE_2);
            List<SysPermission> allAuthList = permissionService.list(wrapper);
            JSONArray allAuthJsonArray = new JSONArray();
            this.getAllAuthJsonArray(authJsonArray, allAuthList);
            // 路由菜单
            json.put("menu", menuJsonArray);
            // 按钮权限(用户拥有的权限集合)
            json.put("auth", authJsonArray);
            // 全部权限配置集合（按钮权限、访问权限）
            json.put("allAuth", allAuthJsonArray);
            json.put("sysSafeMode", rainProBaseConfig.getSafeMode());
            result.setResult(json);
        } catch (Exception e) {
            result.error500("查询失败:" + e.getMessage());
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 获取菜单的JSON数组
     *
     * @param jsonArray   /
     * @param permissions /
     * @param parentJson  /
     */
    private void getPermissionJsonArray(JSONArray jsonArray, List<SysPermission> permissions, JSONObject parentJson) {
        for (SysPermission permission : permissions) {
            if (permission.getMenuType() == null) {
                continue;
            }
            String tempPid = permission.getParentId();
            JSONObject json = getPermissionJsonObject(permission);
            if (json == null) {
                continue;
            }
            if (parentJson == null && ConvertUtils.isEmpty(tempPid)) {
                jsonArray.add(json);
                if (!permission.isLeaf()) {
                    getPermissionJsonArray(jsonArray, permissions, json);
                }
            } else if (parentJson != null && ConvertUtils.isNotEmpty(tempPid) && tempPid.equals(parentJson.getString("id"))) {
                // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
                    JSONObject metaJson = parentJson.getJSONObject("meta");
                    if (metaJson.containsKey("permissionList")) {
                        metaJson.getJSONArray("permissionList").add(json);
                    } else {
                        JSONArray permissionList = new JSONArray();
                        permissionList.add(json);
                        metaJson.put("permissionList", permissionList);
                    }
                    // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_1) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_0)) {
                    if (parentJson.containsKey("children")) {
                        parentJson.getJSONArray("children").add(json);
                    } else {
                        JSONArray children = new JSONArray();
                        children.add(json);
                        parentJson.put("children", children);
                    }
                    if (!permission.isLeaf()) {
                        getPermissionJsonArray(jsonArray, permissions, json);
                    }
                }
            }

        }
    }

    /**
     * 一级菜单下的子菜单全部隐藏路由，则一级菜单不显示出来
     *
     * @param jsonArray /
     */
    private void handleFirstLevelMenuHidden(JSONArray jsonArray) {
        jsonArray = jsonArray.stream().map(obj -> {
            JSONObject returnObj = new JSONObject();
            JSONObject jsonObj = (JSONObject) obj;
            if (jsonObj.containsKey("children")) {
                JSONArray childrens = jsonObj.getJSONArray("children");
                childrens = childrens.stream().filter(arrObj -> !"true".equals(((JSONObject) arrObj).getString("hidden"))).collect(Collectors.toCollection(JSONArray::new));
                if (childrens.size() == 0) {
                    jsonObj.put("hidden", true);

                    //vue3版本兼容代码
                    JSONObject meta = new JSONObject();
                    meta.put("hideMenu", true);
                    jsonObj.put("meta", meta);
                }
            }
            return returnObj;
        }).collect(Collectors.toCollection(JSONArray::new));
    }

    /**
     * 获取权限JSON数组（按钮）
     *
     * @param jsonArray /
     * @param allList   /
     */
    private void getAllAuthJsonArray(JSONArray jsonArray, List<SysPermission> allList) {
        JSONObject json;
        for (SysPermission permission : allList) {
            json = new JSONObject();
            json.put("action", permission.getPerms());
            json.put("status", permission.getStatus());
            //1显示2禁用
            json.put("type", permission.getPermsType());
            json.put("describe", permission.getName());
            jsonArray.add(json);
        }
    }

    /**
     * 获取权限JSON数组（按钮）
     *
     * @param jsonArray /
     * @param metaList  /
     */
    private void getAuthJsonArray(JSONArray jsonArray, List<SysPermission> metaList) {
        for (SysPermission permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            JSONObject json;
            if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2) && CommonConstant.STATUS_1.equals(permission.getStatus())) {
                json = new JSONObject();
                json.put("action", permission.getPerms());
                json.put("type", permission.getPermsType());
                json.put("describe", permission.getName());
                jsonArray.add(json);
            }
        }
    }

    /**
     * 根据菜单配置生成路由json
     *
     * @param permission 菜单
     * @return 路由json
     */
    private JSONObject getPermissionJsonObject(SysPermission permission) {
        JSONObject json = new JSONObject();
        // 类型(0：一级菜单 1：子菜单 2：按钮)
        if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_2)) {
            return null;
        } else if (permission.getMenuType().equals(CommonConstant.MENU_TYPE_0) || permission.getMenuType().equals(CommonConstant.MENU_TYPE_1)) {
            json.put("id", permission.getId());
            if (permission.isRoute()) {
                json.put("route", "1");// 表示生成路由
            } else {
                json.put("route", "0");// 表示不生成路由
            }

            if (isWWWHttpUrl(permission.getUrl())) {
                json.put("path", MD5Util.MD5Encode(permission.getUrl(), "utf-8"));
            } else {
                json.put("path", permission.getUrl());
            }

            // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
            if (ConvertUtils.isNotEmpty(permission.getComponentName())) {
                json.put("name", permission.getComponentName());
            } else {
                json.put("name", urlToRouteName(permission.getUrl()));
            }

            JSONObject meta = new JSONObject();
            // 是否隐藏路由，默认都是显示的
            if (permission.isHidden()) {
                json.put("hidden", true);
                //vue3版本兼容代码
                meta.put("hideMenu", true);
            }
            // 聚合路由
            if (permission.isAlwaysShow()) {
                json.put("alwaysShow", true);
            }
            json.put("component", permission.getComponent());
            // 由用户设置是否缓存页面 用布尔值
            meta.put("keepAlive", permission.isKeepAlive());

            /* 往菜单信息里添加外链菜单打开方式 */
            //外链菜单打开方式
            meta.put("internalOrExternal", permission.isInternalOrExternal());
            /* 往菜单信息里添加外链菜单打开方式*/

            meta.put("title", permission.getName());

            // 路由缓存问题，关闭了tab页时再打开就不刷新
            String component = permission.getComponent();
            if (ConvertUtils.isNotEmpty(permission.getComponentName()) || ConvertUtils.isNotEmpty(component)) {
                meta.put("componentName", ConvertUtils.getString(permission.getComponentName(), component.substring(component.lastIndexOf("/") + 1)));
            }

            if (ConvertUtils.isEmpty(permission.getParentId())) {
                // 一级菜单跳转地址
                json.put("redirect", permission.getRedirect());
                if (ConvertUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            } else {
                if (ConvertUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            }
            if (isWWWHttpUrl(permission.getUrl())) {
                meta.put("url", permission.getUrl());
            }
            // 新增适配vue3项目的隐藏tab功能
            if (permission.isHideTab()) {
                meta.put("hideTab", true);
            }
            json.put("meta", meta);
        }

        return json;
    }

    /**
     * 判断是否外网URL 例如： http://localhost:8080/jeecg-boot/swagger-ui.html#/ 支持特殊格式： {{
     * window._CONFIG['domainURL'] }}/druid/ {{ JS代码片段 }}，前台解析会自动执行JS代码片段
     *
     * @return /
     */
    private boolean isWWWHttpUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("{{"));
    }

    /**
     * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-） 举例： URL = /system/role RouteName =
     * system-role
     *
     * @return /
     */
    private String urlToRouteName(String url) {
        if (ConvertUtils.isNotEmpty(url)) {
            if (url.startsWith("/")) {
                url = url.substring(1);
            }
            url = url.replace("/", "-");

            // 特殊标记
            url = url.replace(":", "@");
            return url;
        } else {
            return null;
        }
    }

    /**
     * 将打平的list构建成树形结构的list
     *
     * @param treeList 树形结构list
     * @param metaList 打平的数据list
     * @param temp     临时树对象
     */
    private void getTreeList(List<SysPermissionTree> treeList, List<SysPermission> metaList, SysPermissionTree temp) {
        for (SysPermission permission : metaList) {
            String tempPid = permission.getParentId();
            SysPermissionTree tree = new SysPermissionTree(permission);
            if (temp == null && ConvertUtils.isEmpty(tempPid)) {
                treeList.add(tree);
                if (!tree.getIsLeaf()) {
                    getTreeList(treeList, metaList, tree);
                }
            } else if (temp != null && tempPid != null && tempPid.equals(temp.getId())) {
                temp.getChildren().add(tree);
                if (!tree.getIsLeaf()) {
                    getTreeList(treeList, metaList, tree);
                }
            }
        }
    }

}
