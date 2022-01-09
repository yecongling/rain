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