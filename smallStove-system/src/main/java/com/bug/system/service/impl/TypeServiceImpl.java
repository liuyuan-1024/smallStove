package com.bug.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.system.domain.entity.Type;
import com.bug.system.service.TypeService;
import com.bug.system.mapper.TypeMapper;
import org.springframework.stereotype.Service;

/**
* @author 源
* @description 针对表【sys_type(类别表)】的数据库操作Service实现
* @createDate 2022-06-09 20:06:57
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




