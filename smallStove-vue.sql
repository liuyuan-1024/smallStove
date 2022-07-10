CREATE DATABASE `smallStove-vue` DEFAULT CHARSET utf8mb4;


DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(20)                              NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    varchar(30) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户账号',
    `password`    varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
    `nickname`    varchar(30) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户昵称',
    `remark`      varchar(500) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `email`       varchar(50) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '用户邮箱',
    `mobile`      varchar(11) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '手机号码',
    `sex`         tinyint(4)                                       DEFAULT '0' COMMENT '用户性别',
    `avatar`      varchar(100) COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '头像地址',
    `status`      tinyint(4)                              NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `login_ip`    varchar(50) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '最后登录IP',
    `login_date`  datetime                                         DEFAULT NULL COMMENT '最后登录时间',
    `creator`     varchar(64) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                  NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_username` (`username`, `update_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户信息表';


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`   bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` varchar(30)  NOT NULL COMMENT '角色名称',
    `role_key`  varchar(100) NOT NULL COMMENT '角色权限字符串,以英文逗号分隔',
    `status`    char(1)      NOT NULL COMMENT '角色状态（0正常 1停用）',
    `deleted`   char(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `remark`    varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8 COMMENT ='角色信息表';


DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   varchar(50) NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint(20)   DEFAULT '0' COMMENT '父菜单ID',
    `order_num`   int(4)       DEFAULT '0' COMMENT '显示顺序',
    `path`        varchar(200) DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) DEFAULT NULL COMMENT '组件路径',
    `query`       varchar(255) DEFAULT NULL COMMENT '路由参数',
    `is_frame`    int(1)       DEFAULT '1' COMMENT '是否为外链（0是 1否）',
    `is_cache`    int(1)       DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
    `menu_type`   char(1)      DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     char(1)      DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1)      DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100) DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100) DEFAULT '#' COMMENT '菜单图标',
    `create_by`   varchar(64)  DEFAULT '' COMMENT '创建者',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)  DEFAULT '' COMMENT '更新者',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`menu_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单权限表';


DROP TABLE IF EXISTS `sys_article`;
CREATE TABLE `sys_article`
(
    `user_id`       bigint(20)  NOT NULL COMMENT '作者ID',
    `article_id`    bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '文章ID',
    `title`         varchar(64) NOT NULL COMMENT '文章标题',
    `thumbnail`     longtext COMMENT '文章缩略图',
    `content`       longtext COMMENT '文章内容',
    `views_number`  int(11)     DEFAULT '0' COMMENT '文章浏览量',
    `likes_number`  int(11)     DEFAULT '0' COMMENT '文章点赞数',
    `allow_comment` char(1)     DEFAULT '0' COMMENT '是否允许评论（0代表允许, 1代表禁止）',
    `status`        char(1)     DEFAULT '0' COMMENT '文章状态（0代表公开, 1代表私密）',
    `create_by`     varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time`   datetime    DEFAULT NULL COMMENT '创建时间',
    `update_by`     varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time`   datetime    DEFAULT NULL COMMENT '更新时间',
    `deleted`       char(1)     DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (`article_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章表';


DROP TABLE IF EXISTS `sys_comment`;
CREATE TABLE `sys_comment`
(
    `comment_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `content`     longtext   NOT NULL COMMENT '评论内容',
    `article_id`  bigint(20) NOT NULL COMMENT '文章ID',
    `parent_id`   bigint(20) NOT NULL DEFAULT '0' COMMENT '父评论ID（0代表无父评论，即根评论）',
    `user_id`     bigint(20) NOT NULL COMMENT '评论者ID',
    `to_user_id`  bigint(20) NOT NULL DEFAULT '0' COMMENT '被评论者ID',
    `create_by`   varchar(64)         DEFAULT '' COMMENT '创建者',
    `create_time` datetime            DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)         DEFAULT '' COMMENT '更新者',
    `update_time` datetime            DEFAULT NULL COMMENT '更新时间',
    `deleted`     char(1)             DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (`comment_id`),
    KEY `article_id_forergin` (`article_id`),
    KEY `parent_id_forergin` (`parent_id`),
    KEY `user_id_forergin` (`user_id`),
    KEY `to_user_id_forergin` (`to_user_id`),
    CONSTRAINT `article_id_forergin` FOREIGN KEY (`article_id`) REFERENCES `sys_article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_id_forergin` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8mb4 COMMENT ='评论表';


DROP TABLE IF EXISTS `sys_type`;
CREATE TABLE `sys_type`
(
    `type_id`     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '类别ID',
    `type_name`   varchar(16) NOT NULL COMMENT '类别名称',
    `status`      char(1)     DEFAULT '0' COMMENT '类别状态（0代表正常, 1代表停用）',
    `create_by`   varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime    DEFAULT NULL COMMENT '更新时间',
    `deleted`     char(1)     DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (`type_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4 COMMENT ='类别表';


DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`),
    KEY `role_id_foreign` (`role_id`),
    CONSTRAINT `role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户和角色关联表';


DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`),
    KEY `menu_id_foreign` (`menu_id`),
    CONSTRAINT `menu_id_foreign` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `role_id_forgien` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和菜单关联表';


DROP TABLE IF EXISTS `sys_article_type`;
CREATE TABLE `sys_article_type`
(
    `article_id` bigint(20) NOT NULL COMMENT '文章ID',
    `type_id`    bigint(20) NOT NULL COMMENT '类别ID',
    PRIMARY KEY (`article_id`, `type_id`),
    KEY `type_id_foreign` (`type_id`),
    CONSTRAINT `article_id_foreign` FOREIGN KEY (`article_id`) REFERENCES `sys_article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `type_id_foreign` FOREIGN KEY (`type_id`) REFERENCES `sys_type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章类别表';


DROP TABLE IF EXISTS `sys_article_like`;
CREATE TABLE `sys_article_like`
(
    `user_id`     bigint(20) NOT NULL COMMENT '用户ID',
    `article_id`  bigint(20) NOT NULL COMMENT '文章ID',
    `type`        char(1)    NOT NULL DEFAULT '0' COMMENT '点赞类型（0代表点赞，1代表点踩）',
    `create_by`   varchar(64)         DEFAULT '' COMMENT '创建者',
    `create_time` datetime            DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64)         DEFAULT '' COMMENT '更新者',
    `update_time` datetime            DEFAULT NULL COMMENT '更新时间',
    `deleted`     char(1)             DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (`user_id`, `article_id`),
    KEY `article_id` (`article_id`),
    CONSTRAINT `sys_article_like_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `sys_article_like_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `sys_article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='点赞表';