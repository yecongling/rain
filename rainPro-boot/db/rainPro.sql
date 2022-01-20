/* 1、新建系统日志表  sys_log */
CREATE TABLE if not exists  `sys_log`
(
    `id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL,
    `log_type`      int(2)                                                   NULL DEFAULT NULL COMMENT '日志类型（1登录日志，2操作日志）',
    `log_content`   varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志内容',
    `operate_type`  int(2)                                                   NULL DEFAULT NULL COMMENT '操作类型',
    `userid`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '操作用户账号',
    `username`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '操作用户名称',
    `ip`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT 'IP',
    `method`        varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '请求java方法',
    `request_url`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '请求路径',
    `request_param` longtext CHARACTER SET utf8 COLLATE utf8_general_ci      NULL COMMENT '请求参数',
    `request_type`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '请求类型',
    `cost_time`     bigint(20)                                               NULL DEFAULT NULL COMMENT '耗时',
    `create_by`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime                                                 NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '更新人',
    `update_time`   datetime                                                 NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_table_userid` (`userid`) USING BTREE,
    INDEX `index_logt_ype` (`log_type`) USING BTREE,
    INDEX `index_operate_type` (`operate_type`) USING BTREE,
    INDEX `index_log_type` (`log_type`) USING BTREE,
    INDEX `idx_sl_userid` (`userid`) USING BTREE,
    INDEX `idx_sl_log_type` (`log_type`) USING BTREE,
    INDEX `idx_sl_operate_type` (`operate_type`) USING BTREE,
    INDEX `idx_sl_create_time` (`create_time`) USING BTREE
) ENGINE = MyISAM
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '系统日志表'
  ROW_FORMAT = Dynamic;

/* 2、新建系统用户表  sys_user */
CREATE TABLE if not exists  `sys_user`  (
     `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
     `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
     `real_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
     `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
     `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5密码盐',
     `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
     `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
     `sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别(0-默认未知,1-男,2-女)',
     `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
     `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
     `org_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构编码',
     `status` tinyint(1) NULL DEFAULT NULL COMMENT '性别(1-正常,2-冻结)',
     `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
     `third_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方登录的唯一标识',
     `third_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方类型',
     `activity_sync` tinyint(1) NULL DEFAULT NULL COMMENT '同步工作流引擎(1-同步,0-不同步)',
     `work_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工号，唯一键',
     `post` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务，关联职务表',
     `telephone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机号',
     `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
     `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
     `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
     `user_identity` tinyint(1) NULL DEFAULT NULL COMMENT '身份（1普通成员 2上级）',
     `depart_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '负责部门',
     `rel_tenant_ids` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '多租户标识',
     `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备ID',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `index_user_name`(`username`) USING BTREE,
     UNIQUE INDEX `uniq_sys_user_work_no`(`work_no`) USING BTREE,
     UNIQUE INDEX `uniq_sys_user_username`(`username`) USING BTREE,
     UNIQUE INDEX `uniq_sys_user_phone`(`phone`) USING BTREE,
     UNIQUE INDEX `uniq_sys_user_email`(`email`) USING BTREE,
     INDEX `index_user_status`(`status`) USING BTREE,
     INDEX `index_user_del_flag`(`del_flag`) USING BTREE,
     INDEX `idx_su_username`(`username`) USING BTREE,
     INDEX `idx_su_status`(`status`) USING BTREE,
     INDEX `idx_su_del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;


/* 3、 新建系统角色表  sys_role */
CREATE TABLE if not exists  `sys_role`  (
     `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
     `role_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
     `role_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
     `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
     `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
     `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
     `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `uniq_sys_role_role_code`(`role_code`) USING BTREE,
     INDEX `idx_sr_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

/* 4、 新建用户角色表 用户和角色的关联表 sys_user_role */
CREATE TABLE if not exists  `sys_user_role`  (
      `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
      `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
      `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
      PRIMARY KEY (`id`) USING BTREE,
      INDEX `index2_group_user_id`(`user_id`) USING BTREE,
      INDEX `index2_group_ole_id`(`role_id`) USING BTREE,
      INDEX `index2_group_useridandroleid`(`user_id`, `role_id`) USING BTREE,
      INDEX `idx_sur_user_id`(`user_id`) USING BTREE,
      INDEX `idx_sur_role_id`(`role_id`) USING BTREE,
      INDEX `idx_sur_user_role_id`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

/* 5、新建系统部门表  sys_depart */
CREATE TABLE if not exists  `sys_depart`  (
       `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
       `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父机构ID',
       `depart_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构/部门名称',
       `depart_name_en` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '英文名',
       `depart_name_abbr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩写',
       `depart_order` int(11) NULL DEFAULT 0 COMMENT '排序',
       `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
       `org_category` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '机构类别 1公司，2组织机构，2岗位',
       `org_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构类型 1一级部门 2子部门',
       `org_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构编码',
       `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
       `fax` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传真',
       `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
       `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
       `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态（1启用，0不启用）',
       `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除状态（0，正常，1已删除）',
       `qywx_identifier` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对接企业微信的ID',
       `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
       `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
       `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
       `update_time` datetime NULL DEFAULT NULL COMMENT '更新日期',
       PRIMARY KEY (`id`) USING BTREE,
       UNIQUE INDEX `uniq_depart_org_code`(`org_code`) USING BTREE,
       INDEX `index_depart_parent_id`(`parent_id`) USING BTREE,
       INDEX `index_depart_depart_order`(`depart_order`) USING BTREE,
       INDEX `index_depart_org_code`(`org_code`) USING BTREE,
       INDEX `idx_sd_parent_id`(`parent_id`) USING BTREE,
       INDEX `idx_sd_depart_order`(`depart_order`) USING BTREE,
                               INDEX `idx_sd_org_code`(`org_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织机构表' ROW_FORMAT = Dynamic;

/* 6、新建用户部门关系表  sys_user_depart */
CREATE TABLE if not exists  `sys_user_depart`  (
        `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
        `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
        `dep_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id',
        PRIMARY KEY (`ID`) USING BTREE,
        INDEX `index_depart_groupk_userid`(`user_id`) USING BTREE,
        INDEX `index_depart_groupkorgid`(`dep_id`) USING BTREE,
        INDEX `index_depart_groupk_uidanddid`(`user_id`, `dep_id`) USING BTREE,
        INDEX `idx_sud_user_id`(`user_id`) USING BTREE,
        INDEX `idx_sud_dep_id`(`dep_id`) USING BTREE,
        INDEX `idx_sud_user_dep_id`(`user_id`, `dep_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

/* 7、创建系统租户表 sys_tenant  */
CREATE TABLE if not exists  `sys_tenant`  (
   `id` int(5) NOT NULL COMMENT '租户编码',
   `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
   `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
   `create_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
   `begin_date` datetime NULL DEFAULT NULL COMMENT '开始时间',
   `end_date` datetime NULL DEFAULT NULL COMMENT '结束时间',
   `status` int(1) NULL DEFAULT NULL COMMENT '状态 1正常 0冻结',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '多租户信息表' ROW_FORMAT = Dynamic;

/* 8、新建系统字典表 sys_dict */
CREATE TABLE if not exists  `sys_dict`  (
     `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
     `dict_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
     `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
     `del_flag` int(1) NULL DEFAULT NULL COMMENT '删除状态',
     `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
     `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
     `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
     `type` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '字典类型0为string,1为number',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `indextable_dict_code`(`dict_code`) USING BTREE,
     UNIQUE INDEX `uk_sd_dict_code`(`dict_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

/* 9、新建字典明细表 sys_dict_item */
CREATE TABLE if not exists `sys_dict_item`  (
      `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
      `dict_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典id',
      `item_text` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典项文本',
      `item_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典项值',
      `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
      `sort_order` int(10) NULL DEFAULT NULL COMMENT '排序',
      `status` int(11) NULL DEFAULT NULL COMMENT '状态（1启用 0不启用）',
      `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
      `create_time` datetime NULL DEFAULT NULL,
      `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
      `update_time` datetime NULL DEFAULT NULL,
      PRIMARY KEY (`id`) USING BTREE,
      INDEX `index_table_dict_id`(`dict_id`) USING BTREE,
      INDEX `index_table_sort_order`(`sort_order`) USING BTREE,
      INDEX `index_table_dict_status`(`status`) USING BTREE,
      INDEX `idx_sdi_role_dict_id`(`dict_id`) USING BTREE,
      INDEX `idx_sdi_role_sort_order`(`sort_order`) USING BTREE,
      INDEX `idx_sdi_status`(`status`) USING BTREE,
      INDEX `idx_sdi_dict_val`(`dict_id`, `item_value`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

/* 10、新建系统菜单表  sys_permission */
CREATE TABLE if not exists `sys_permission`  (
       `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
       `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父id',
       `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单标题',
       `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
       `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
       `component_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件名字',
       `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一级菜单跳转地址',
       `menu_type` int(11) NULL DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
       `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限编码',
       `perms_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '权限策略1显示2禁用',
       `sort_no` double(8, 2) NULL DEFAULT NULL COMMENT '菜单排序',
       `always_show` tinyint(1) NULL DEFAULT NULL COMMENT '聚合子路由: 1是0否',
       `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
       `is_route` tinyint(1) NULL DEFAULT 1 COMMENT '是否路由菜单: 0:不是  1:是（默认值1）',
       `is_leaf` tinyint(1) NULL DEFAULT NULL COMMENT '是否叶子节点:    1:是   0:不是',
       `keep_alive` tinyint(1) NULL DEFAULT NULL COMMENT '是否缓存该页面:    1:是   0:不是',
       `hidden` int(2) NULL DEFAULT 0 COMMENT '是否隐藏路由: 0否,1是',
       `hide_tab` int(2) NULL DEFAULT NULL COMMENT '是否隐藏tab: 0否,1是',
       `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
       `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
       `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
       `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
       `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
       `del_flag` int(1) NULL DEFAULT 0 COMMENT '删除状态 0正常 1已删除',
       `rule_flag` int(3) NULL DEFAULT 0 COMMENT '是否添加数据权限1是0否',
       `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮权限状态(0无效1有效)',
       `internal_or_external` tinyint(1) NULL DEFAULT NULL COMMENT '外链菜单打开方式 0/内部打开 1/外部打开',
       PRIMARY KEY (`id`) USING BTREE,
       INDEX `index_prem_pid`(`parent_id`) USING BTREE,
       INDEX `index_prem_is_route`(`is_route`) USING BTREE,
       INDEX `index_prem_is_leaf`(`is_leaf`) USING BTREE,
       INDEX `index_prem_sort_no`(`sort_no`) USING BTREE,
       INDEX `index_prem_del_flag`(`del_flag`) USING BTREE,
       INDEX `index_menu_type`(`menu_type`) USING BTREE,
       INDEX `index_menu_hidden`(`hidden`) USING BTREE,
       INDEX `index_menu_status`(`status`) USING BTREE,
       INDEX `idx_sp_parent_id`(`parent_id`) USING BTREE,
       INDEX `idx_sp_is_route`(`is_route`) USING BTREE,
       INDEX `idx_sp_is_leaf`(`is_leaf`) USING BTREE,
       INDEX `idx_sp_sort_no`(`sort_no`) USING BTREE,
       INDEX `idx_sp_del_flag`(`del_flag`) USING BTREE,
       INDEX `idx_sp_menu_type`(`menu_type`) USING BTREE,
       INDEX `idx_sp_hidden`(`hidden`) USING BTREE,
       INDEX `idx_sp_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

/* 11、新建角色菜单关联信息表   sys_role_permission */
CREATE TABLE if not exists `sys_role_permission`  (
    `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
    `permission_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限id',
    `data_rule_ids` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据权限ids',
    `operate_date` datetime NULL DEFAULT NULL COMMENT '操作时间',
    `operate_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作ip',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_group_role_per_id`(`role_id`, `permission_id`) USING BTREE,
    INDEX `index_group_role_id`(`role_id`) USING BTREE,
    INDEX `index_group_per_id`(`permission_id`) USING BTREE,
    INDEX `idx_srp_role_per_id`(`role_id`, `permission_id`) USING BTREE,
    INDEX `idx_srp_role_id`(`role_id`) USING BTREE,
    INDEX `idx_srp_permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

/* 12、新建系统通告表   sys_announcement */
CREATE TABLE if not exists `sys_announcement`  (
     `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
     `msg_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
     `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
     `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
     `sender` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布人',
     `priority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优先级（L低，M中，H高）',
     `msg_category` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2' COMMENT '消息类型1:通知公告2:系统消息',
     `msg_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通告对象类型（USER:指定用户，ALL:全体用户）',
     `send_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布状态（0未发布，1已发布，2已撤销）',
     `send_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
     `cancel_time` datetime NULL DEFAULT NULL COMMENT '撤销时间',
     `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除状态（0，正常，1已删除）',
     `bus_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型(email:邮件 bpm:流程)',
     `bus_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务id',
     `open_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打开方式(组件：component 路由：url)',
     `open_page` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件/路由 地址',
     `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
     `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
     `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
     `user_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '指定用户',
     `msg_abstract` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '摘要',
     `dt_task_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '钉钉task_id，用于撤回消息',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统通告表' ROW_FORMAT = Dynamic;

/* 13、新建系统通知发送表  sys_announcement_send */
CREATE TABLE if not exists `sys_announcement_send`  (
      `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
      `annt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通告ID',
      `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
      `read_flag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阅读状态（0未读，1已读）',
      `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
      `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
      `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
      `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
      `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户通告阅读标记表' ROW_FORMAT = Dynamic;

/* 14、新建职务表 sys_position */
CREATE TABLE if not exists `sys_position`  (
     `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
     `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务编码',
     `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务名称',
     `post_rank` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职级',
     `company_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司id',
     `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
     `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
     `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
     `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
     `sys_org_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构编码',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `uniq_code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;