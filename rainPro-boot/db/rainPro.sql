/* 1、新建系统日志表  sys_log */
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
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
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
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

INSERT INTO `sys_user` VALUES ('a75d45a015c44384a04449ee80dc3503', 'jeecg', 'jeecg', '58a714412072f0b9', 'mIgiYJow', 'https://static.jeecg.com/temp/国炬软件logo_1606575029126.png', NULL, 1, NULL, NULL, 'A02A01', 1, 0, NULL, NULL, 1, '00002', 'devleader', NULL, 'admin', '2019-02-13 16:02:36', 'admin', '2020-11-26 15:16:05', 1, '', NULL, NULL);

/* 3、 新建系统角色表  sys_role */
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
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
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
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
DROP TABLE IF EXISTS `sys_depart`;
CREATE TABLE `sys_depart`  (
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
DROP TABLE IF EXISTS `sys_user_depart`;
CREATE TABLE `sys_user_depart`  (
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
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
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
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
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
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
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