package com.xieyue.jwt.service.visitor;

import com.xieyue.jwt.dao.entity.Visitor;
import org.springframework.stereotype.Component;

@Component
public interface VisitorService {

    public Visitor findById(int id);

}
