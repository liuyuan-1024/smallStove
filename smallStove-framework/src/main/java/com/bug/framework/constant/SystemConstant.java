package com.bug.framework.constant;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 系统常量
 */
public class SystemConstant {

    /**
     * 普通用户注册时，添加的角色ID
     */
    public static final Long COMMON_ROLE_ID = 2L;


    /**
     * 请求头中的权限令牌名称
     */
    public static final String ACCESS_TOKEN = "access_token";
    /**
     * 请求头中的刷新令牌名称
     */
    public static final String REFRESH_TOKEN = "refresh_token";


    /**
     * 角色、菜单的状态--正常
     */
    public static final String STATUS_NORMAL = "0";
    /**
     * 角色、菜单的状态--停用
     */
    public static final String STATUS_DEACTIVATE = "1";


    /**
     * 文章的状态--公开
     */
    public static final String ARTICLE_STATUS_PUBLIC = "0";
    /**
     * 文章的状态--私密
     */
    public static final String ARTICLE_STATUS_PRIVATE = "1";
    public static final Integer PAGE_CURRENT = 1;
    public static final Integer PAGE_SIZE = 10;

    /**
     * 文章默认浏览量和点赞数
     */
    public static final Integer ARTICLE_VIEWS_LIKES_DEFAULT = 0;
    public static final Long COMMENT_ROOT = 0L;

    /**
     * 图片后缀
     */
    public static final String JPG = "jpg";
    public static final String JPEG = "jpeg";
    public static final String PNG = "png";
}
