package com.bug.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TypeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类别ID
     */
    private Long typeId;

    /**
     * 类别名称
     */
    private String typeName;
}
