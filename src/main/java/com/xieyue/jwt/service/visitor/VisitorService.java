package com.xieyue.jwt.service.visitor;

import com.xieyue.jwt.dao.entity.Visitor;
import com.xieyue.jwt.dao.entity.VisitorRecord;
import com.xieyue.jwt.dao.entity.VisitorRecordView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VisitorService {

    public Visitor findById(int id);

    public VisitorRecord saveRecord(VisitorRecord visitorRecord);

    public List<VisitorRecordView> getVisitorRecordList(int visitorId);

}
