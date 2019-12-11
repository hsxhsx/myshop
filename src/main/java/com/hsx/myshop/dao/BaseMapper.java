package com.hsx.myshop.dao;

import com.hsx.myshop.entity.BaseEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface BaseMapper <T> extends Mapper<T>, MySqlMapper<T> , ConditionMapper<T>, IdsMapper<T> {
//    /**
//     * * 单表分页查询 * * @param object * @param offset * @param limit * @return 
//     */
//    @SelectProvider(type = BaseMapperProvider.class, method = "dynamicSQL")
//    List selectPage(@Param("entity") T object, @Param("offset") int offset, @Param("limit") int limit);

}
