CREATE DATABASE `smallStove-vue` DEFAULT CHARSET utf8mb4;


DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          BIGINT                                                        NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户账号',
    `password`    VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
    `nickname`    VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户昵称',
    `remark`      VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `email`       VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '用户邮箱',
    `mobile`      VARCHAR(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '手机号码',
    `sex`         TINYINT                                                                DEFAULT '0' COMMENT '用户性别',
    `avatar`      VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '头像地址',
    `status`      TINYINT                                                       NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `login_ip`    VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '最后登录IP',
    `login_date`  DATETIME                                                               DEFAULT NULL COMMENT '最后登录时间',
    `creator`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     BIT(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_username` (`username`, `update_time`) USING BTREE
) ENGINE = INNODB
  AUTO_INCREMENT = 115
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户信息表';


DROP TABLE IF EXISTS sys_role;
CREATE TABLE `sys_role`
(
    `role_id`   BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` VARCHAR(30)  NOT NULL COMMENT '角色名称',
    `role_key`  VARCHAR(100) NOT NULL COMMENT '角色权限字符串,以英文逗号分隔',
    `status`    CHAR(1)      NOT NULL COMMENT '角色状态（0正常 1停用）',
    `deleted`   CHAR(1)      DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `remark`    VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8 COMMENT ='角色信息表';
INSERT INTO `sys_role`(`role_id`, `role_name`, `role_key`, `status`, `deleted`, `remark`)
VALUES (1, '超级管理员', 'admin', '0', '0', '超级管理员'),
       (2, '普通角色', 'common', '0', '0', '普通角色');


DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu
(
    menu_id     BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    menu_name   VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id   BIGINT(20)   DEFAULT 0 COMMENT '父菜单ID',
    order_num   INT(4)       DEFAULT 0 COMMENT '显示顺序',
    `path`      VARCHAR(200) DEFAULT '' COMMENT '路由地址',
    component   VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    `query`     VARCHAR(255) DEFAULT NULL COMMENT '路由参数',
    is_frame    INT(1)       DEFAULT 1 COMMENT '是否为外链（0是 1否）',
    is_cache    INT(1)       DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
    menu_type   CHAR(1)      DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`   CHAR(1)      DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
    `status`    CHAR(1)      DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
    perms       VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    icon        VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT '' COMMENT '备注',
    PRIMARY KEY (menu_id)
) ENGINE = INNODB
  AUTO_INCREMENT = 2000 COMMENT = '菜单权限表';


DROP TABLE IF EXISTS sys_article;
CREATE TABLE sys_article
(
    user_id       bigint(20)  NOT NULL comment '作者ID',
    article_id    bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '文章ID',
    title         varchar(64) NOT NULL COMMENT '文章标题',
    thumbnail     longtext comment '文章缩略图',
    content       longtext COMMENT '文章内容',
    views_number  int(11)     DEFAULT '' COMMENT '文章浏览量',
    likes_number  int(11)     default 0 comment '文章点赞数',
    allow_comment char(1)     default '0' comment '是否允许评论（0代表允许, 1代表禁止）',
    `status`      char(1)     default '0' comment '文章状态（0代表公开, 1代表私密）',
    create_by     VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time   DATETIME COMMENT '创建时间',
    update_by     VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time   DATETIME COMMENT '更新时间',
    deleted       CHAR(1)     DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (article_id)
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT = '文章表';


DROP TABLE IF EXISTS sys_type;
CREATE TABLE sys_type
(
    type_id     bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '类别ID',
    type_name   varchar(16) NOT NULL COMMENT '类别名称',
    `status`    char(1)     default '0' comment '类别状态（0代表正常, 1代表停用）',
    create_by   VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_by   VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间',
    deleted     CHAR(1)     DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (type_id)
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT = '类别表';


DROP TABLE IF EXISTS sys_comment;
CREATE TABLE sys_comment
(
    type_id     bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    content     longtext   NOT NULL COMMENT '评论内容',
    article_id  bigint(20) NOT NULL COMMENT '文章ID',
    parent_id   bigint(20) NOT NULL COMMENT '父评论ID（0代表无父评论，即根评论）',
    user_id     bigint(20) NOT NULL COMMENT '评论者ID',
    to_user_id  bigint(20) NOT NULL COMMENT '被评论者ID',
    create_by   VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_by   VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间',
    deleted     CHAR(1)     DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (type_id)
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT = '评论表';


DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE `sys_user_role`
(
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8 COMMENT ='用户和角色关联表';


DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu
(
    role_id BIGINT(20) NOT NULL COMMENT '角色ID',
    menu_id BIGINT(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE = INNODB COMMENT = '角色和菜单关联表';


DROP TABLE IF EXISTS sys_article_type;
CREATE TABLE sys_article_type
(
    article_id bigint(20) NOT NULL COMMENT '文章ID',
    type_id    bigint(20) NOT NULL COMMENT '类别ID',
    PRIMARY KEY (article_id, type_id)
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT = '文章类别表';

DROP TABLE IF EXISTS sys_article_like;
CREATE TABLE sys_article_like
(
    user_id     bigint(20) NOT NULL COMMENT '用户ID',
    article_id  bigint(20) NOT NULL COMMENT '文章ID',
    type        char(1)    NOT NULL default '0' COMMENT '点赞类型（0代表点赞，1代表点踩）',
    create_by   VARCHAR(64)         DEFAULT '' COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    update_by   VARCHAR(64)         DEFAULT '' COMMENT '更新者',
    update_time DATETIME COMMENT '更新时间',
    deleted     CHAR(1)             DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    foreign key (user_id) references sys_user (`id`) on update cascade on delete cascade,
    foreign key (article_id) references sys_article (`article_id`) on update cascade on delete cascade
) ENGINE = INNODB
  AUTO_INCREMENT = 1 COMMENT = '点赞表';


