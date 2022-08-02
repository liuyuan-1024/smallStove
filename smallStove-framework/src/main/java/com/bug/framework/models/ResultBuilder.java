package com.bug.framework.models;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 统一响应类
 */
public final class ResultBuilder {

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public Result<?> success() {
        return Result.builder()
                .code(ResultEnum.SUCCESS.getCode())
                .message(ResultEnum.SUCCESS.getMessage())
                .build();
    }

    /**
     * 返回成功消息
     *
     * @param message 返回内容
     * @return 成功消息
     */
    public <T> Result<T> success(String message) {
        return Result.<T>builder()
                .code(ResultEnum.SUCCESS.getCode())
                .message(message)
                .build();
    }

    /**
     * 返回成功消息
     *
     * @param data 数据对象
     * @return 成功消息
     */
    public <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(ResultEnum.SUCCESS.getCode())
                .message(ResultEnum.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    /**
     * 返回成功消息
     *
     * @param message 返回内容
     * @return 成功消息
     */
    public <T> Result<T> success(String message, T data) {
        return Result.<T>builder()
                .code(ResultEnum.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 返回成功消息
     *
     * @param resultEnum 状态码,返回内容
     * @return 成功消息
     */
    public <T> Result<T> success(ResultEnum resultEnum) {
        return Result.<T>builder()
                .code(resultEnum.getCode())
                .message(resultEnum.getMessage())
                .build();
    }

    /**
     * 返回成功消息
     *
     * @param resultEnum 状态码,返回内容
     * @param data       数据对象
     * @return 成功消息
     */
    public <T> Result<T> success(ResultEnum resultEnum, T data) {
        return Result.<T>builder()
                .code(resultEnum.getCode())
                .message(resultEnum.getMessage())
                .data(data)
                .build();
    }


    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public Result<?> error() {
        return Result.builder()
                .code(ResultEnum.FAIL.getCode())
                .message(ResultEnum.FAIL.getMessage())
                .build();
    }

    /**
     * 返回错误消息
     *
     * @param message 返回内容
     * @return 错误消息
     */
    public Result<?> error(String message) {
        return Result.builder()
                .code(ResultEnum.FAIL.getCode())
                .message(message)
                .build();
    }

    /**
     * 返回错误消息
     *
     * @param message 返回内容
     * @return 成功消息
     */
    public <T> Result<T> error(String message, T data) {
        return Result.<T>builder()
                .code(ResultEnum.FAIL.getCode())
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 根据枚举返回错误消息
     *
     * @param resultEnum 状态码 返回内容
     * @return 错误消息
     */
    public Result<?> error(ResultEnum resultEnum) {
        return Result.builder()
                .code(resultEnum.getCode())
                .message(resultEnum.getMessage())
                .build();
    }

    /**
     * 自定义错误返回错误消息
     *
     * @param resultEnum 状态码 返回内容
     * @param data       返回的数据
     * @return 错误消息
     */
    public <T> Result<T> error(ResultEnum resultEnum, T data) {
        return Result.<T>builder()
                .code(resultEnum.getCode())
                .message(resultEnum.getMessage())
                .data(data)
                .build();
    }
}
