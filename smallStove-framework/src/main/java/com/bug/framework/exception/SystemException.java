package com.bug.framework.exception;

import com.bug.framework.models.ResultEnum;
import lombok.Getter;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 自定义系统异常
 */
@Getter
public class SystemException extends RuntimeException {

    private final ResultEnum resultEnum;

    public SystemException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

}
