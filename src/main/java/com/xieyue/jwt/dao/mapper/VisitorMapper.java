package com.xieyue.jwt.dao.mapper;

import com.xieyue.jwt.dao.BaseMapper;
import com.xieyue.jwt.dao.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName :   VisitorMapper
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-28 23:15
 */
public interface VisitorMapper extends BaseMapper<Visitor> {

    public List<Visitor> selectAllVisitor(Integer id);

}
