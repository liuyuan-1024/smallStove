package com.bug.framework.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 * 不要单独使用Result会编译出错,使用ResultBuilder方法调用 Result为返回结果
 *
 * @author BugOS-ly
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.MODULE)
@EqualsAndHashCode()
@Getter
@ApiModel(value = "Result", description = "统一响应")
public final class Result<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private final Integer code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private final String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private final T data;
}
