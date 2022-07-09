package com.bug.framework.exception;

import com.bug.framework.models.ResultEnum;
import lombok.Getter;

/**
 * 自定义系统异常
 */
@Getter
public class SystemException extends RuntimeException {

    private final ResultEnum resultEnum;

    public SystemException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

}
