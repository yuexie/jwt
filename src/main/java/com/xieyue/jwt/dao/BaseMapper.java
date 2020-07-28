package com.xieyue.jwt.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @description  通用mapper
 * @author       XieYue
 * @date         2020/7/28 23:14 
 * @Param        
 * @return       
 **/

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
