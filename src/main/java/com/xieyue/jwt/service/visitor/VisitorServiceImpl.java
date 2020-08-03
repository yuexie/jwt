package com.xieyue.jwt.service.visitor;

import com.xieyue.jwt.dao.entity.Visitor;
import com.xieyue.jwt.dao.entity.VisitorRecord;
import com.xieyue.jwt.dao.entity.VisitorRecordView;
import com.xieyue.jwt.dao.mapper.VisitorMapper;
import com.xieyue.jwt.dao.mapper.VisitorRecordMapper;
import com.xieyue.jwt.dao.mapper.VisitorRecordViewMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName :   VisitorServiceImpl
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-28 23:25
 */
@Service("visitorServiceImpl")
public class VisitorServiceImpl implements VisitorService {

    @Resource
    private VisitorMapper visitorMapper;

    @Resource
    private VisitorRecordMapper visitorRecordMapper;

    @Resource
    private VisitorRecordViewMapper visitorRecordViewMapper;


    @Override
    public Visitor findById(int id){
        return visitorMapper.selectByPrimaryKey(id);
    }

    @Override
    public VisitorRecord saveRecord(VisitorRecord visitorRecord) {
        visitorRecordMapper.insert(visitorRecord);
        return visitorRecord;
    }

    @Override
    public List<VisitorRecordView> getVisitorRecordList(int visitorId) {
        return visitorRecordViewMapper.selectAll();
    }


}
