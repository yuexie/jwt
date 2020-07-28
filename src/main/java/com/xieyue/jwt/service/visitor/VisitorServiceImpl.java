package com.xieyue.jwt.service.visitor;

import com.xieyue.jwt.dao.entity.Visitor;
import com.xieyue.jwt.dao.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName :   VisitorServiceImpl
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-28 23:25
 */
@Service("visitorServiceImpl")
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    VisitorMapper visitorMapper;

    @Override
    public Visitor findById(int id){
        return visitorMapper.selectByPrimaryKey(id);
    }
}
