package com.bug.framework.models;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultEnum {

    //**********************参考HTTP状态码**********************

    /**
     * 成功
     */
    SUCCESS(200, "请求成功!"),

    /**
     * 请求失败!  客户端请求的语法错误，服务器无法理解
     */
    FAIL(400, "请求失败!"),

    /**
     * 未认证（签名错误） 请求要求用户的身份认证
     */
    UNAUTHORIZED(401, "请求未认证!"),

    /**
     * 请求错误,拒绝访问请求错误,拒绝访问!
     * 服务器理解请求客户端的请求，但是拒绝执行此请求
     */
    FORBIDDEN(403, "请求错误,拒绝访问!"),

    /**
     * 接口不存在 服务器无法根据客户端的请求找到资源（网页）。
     * 通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
     */
    NOT_FOUND(404, "接口不存在!"),

    /**
     * 参数错误,缺少必要的参数服务器无法根据客户端请求的内容特性完成请求
     */
    NOT_ACCEPTABLE(406, "参数错误,缺少必要的参数!"),

    /**
     * 资源不存在 客户端请求的资源已经不存在。
     * 410不同于404，如果资源以前有, 但现在被永久删除了可使用410代码，
     * 网站设计人员可通过301代码指定资源的新位置
     */
    GONE(410, "资源不存在!"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),


    //**********************自定义状态码**********************

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(2000000, "登录成功!"),
    /**
     * 登录失败
     */
    LOGIN_FAIL(4000000, "账号或密码错误, 登录失败!"),
    /**
     * 未登录
     */
    LOGIN_NOT(4000100, "未登录, 请登陆后操作!"),


    /**
     * 注册出现意外, 注册失败
     */
    REGISTER_SUCCESS(2000100, "注册成功!"),
    /**
     * 注册出现意外, 注册失败
     */
    REGISTER_FAIL(4000100, "注册失败!"),


    /**
     * token正常
     * AUTHENTICATION
     */
    AUTHENTICATION_NORMAL(2000200, "身份验证正常, 无需操作!"),
    /**
     * token非法
     */
    AUTHENTICATION_ILLEGAL(4000200, "身份验证非法, 请重新登录!"),
    /**
     * 刷新token成功
     */
    AUTHENTICATION_REFRESH_SUCCESS(2000201, "身份验证刷新成功!"),
    /**
     * token过期
     */
    AUTHENTICATION_EXPIRED(4000209, "身份验证过期, 请重新登录!"),


    /**
     * 查询文章列表成功
     */
    ARTICLE_LIST_SUCCESS(2000300, "查询文章列表成功!"),
    /**
     * 查询文章列表失败
     */
    ARTICLE_LIST_FAIL(4000300, "查询文章列表失败!"),
    /**
     * 查询文章详情成功
     */
    ARTICLE_DETAILS_SUCCESS(2000301, "查询文章详情成功!"),
    /**
     * 查询文章详情失败
     */
    ARTICLE_DETAILS_FAIL(4000301, "查询文章详情失败!"),
    /**
     * 发表文章成功
     */
    ARTICLE_PUBLISH_SUCCESS(2000302, "发表文章成功!"),
    /**
     * 发表文章失败
     */
    ARTICLE_PUBLISH_FAIL(4000302, "发表文章失败!"),
    /**
     * 修改文章成功
     */
    ARTICLE_UPDATE_SUCCESS(2000303, "修改文章成功!"),
    /**
     * 修改文章失败
     */
    ARTICLE_UPDATE_FAIL(4000303, "修改文章失败!"),
    /**
     * 删除文章成功
     */
    ARTICLE_DELETE_SUCCESS(2000304, "删除文章成功!"),
    /**
     * 删除文章失败
     */
    ARTICLE_DELETE_FAIL(4000304, "删除文章失败!"),


    /**
     * 浏览成功
     */
    VIEWS_SUCCESS(2000400, "浏览成功!"),
    /**
     * 浏览失败
     */
    VIEWS_FAIL(4000400, "浏览失败!"),


    /**
     * 点赞成功
     */
    LIKES_SUCCESS(2000500, "点赞成功!"),
    /**
     * 点赞失败
     */
    LIKES_FAIL(4000500, "点赞失败!"),
    /**
     * 取消点赞成功
     */
    LIKES_CANCEL_SUCCESS(2000501, "取消点赞成功!"),
    /**
     * 取消点赞失败
     */
    LIKES_CANCEL_FAIL(4000501, "取消点赞失败!"),


    /**
     * 获取评论列表成功
     */
    COMMENT_LIST_SUCCESS(2000600, "获取评论列表成功!"),
    /**
     * 获取评论列表失败
     */
    COMMENT_LIST_FAIL(4000600, "获取评论列表失败!"),
    /**
     * 评论发表成功
     */
    COMMENT_PUBLISH_SUCCESS(2000601, "评论发表成功!"),
    /**
     * 评论发表失败
     */
    COMMENT_PUBLISH_FAIL(4000601, "评论发表失败!"),
    /**
     * 评论删除成功
     */
    COMMENT_DELETE_SUCCESS(2000602, "评论删除成功!"),
    /**
     * 评论删除失败
     */
    COMMENT_DELETE_FAIL(4000602, "评论删除失败!"),


    /**
     * 收藏成功
     */
    FAVORITES_SUCCESS(2000700, "收藏成功!"),
    /**
     * 收藏失败
     */
    FAVORITES_FAIL(4000700, "收藏失败!"),

    /**
     * 获取文章标签成功
     */
    TAGS_GET_SUCCESS(2000800, "获取文章标签成功"),
    /**
     * 获取文章标签失败
     */
    TAGS_GET_FAIL(4000800, "获取文章标签失败"),

    /**
     * 获取用户信息成功
     */
    USER_INFO_SUCCESS(2000900, "获取用户信息成功!"),
    /**
     * 获取用户信息失败
     */
    USER_INFO_FAIL(4000900, "获取用户信息失败!"),
    /**
     * 修改用户信息成功
     */
    USER_INFO_UPDATE_SUCCESS(2000901, "修改用户信息成功!"),
    /**
     * 获取用户信息失败
     */
    USER_INFO_UPDATE_FAIL(4000901, "修改用户信息失败!"),


    /**
     * 发帖或评论失败
     */
    PUBLISH_FAIL(4001000, "含有敏感词, 请注意你的措辞!"),

    /**
     * 上传成功
     */
    UPLOAD_SUCCESS(2001100, "上传成功!"),
    /**
     * 上传失败
     */
    UPLOAD_FAIL(4001100, "上传失败!"),


    /**
     * 全局异常处理捕获到的其他异常
     */
    GLOBAL_EXCEPTION(4000524, "全局异常"),

    ;


    /**
     * 状态码
     */
    private final int code;

    /**
     * 请求消息
     */
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
