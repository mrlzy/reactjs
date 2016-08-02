package com.mrlzy.shiro.plugin.mybatis;

import com.mrlzy.shiro.plugin.dto.BaseDto;
import com.mrlzy.shiro.plugin.dto.Dto;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class ObjectFactory extends DefaultObjectFactory {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected Class<?> resolveInterface(Class<?> type) {
        Class<?> classToCreate= type == Dto.class?BaseDto.class: super.resolveInterface(type);
        return classToCreate;
    }



}
