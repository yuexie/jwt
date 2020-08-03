package com.xieyue.jwt.controller;

import com.wx.vmind.task.DateUtils;
import com.xieyue.jwt.dao.entity.Visitor;
import com.xieyue.jwt.dao.entity.VisitorRecord;
import com.xieyue.jwt.dao.entity.VisitorRecordView;
import com.xieyue.jwt.service.visitor.VisitorService;
import com.xieyue.jwt.service.visitor.VisitorServiceImpl;
import com.xieyue.jwt.utils.LocalDateUtil;
import com.xieyue.jwt.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName :   TkController
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-07-28 23:21
 */
@Slf4j
@RestController
@RequestMapping("tk")
public class TkController {

    @Resource(name = "visitorServiceImpl")
    private VisitorService visitorService;

    @GetMapping("get")
    public Result getVisitor(){

        Visitor visitor = visitorService.findById(1);

        return Result.result(visitor);
    }

    @GetMapping("save")
    public Result saveVisitorRecord(){
        LocalDateTime dateTimeNow = LocalDateTime.now();

        VisitorRecord visitorRecord = new VisitorRecord();
        visitorRecord.setVisitorId(1);
        visitorRecord.setVisitToName("WangDaQin");
        visitorRecord.setVisitToDate(LocalDateUtil.localDateTimeToDate(dateTimeNow));
        visitorRecord.setAddTime(LocalDateUtil.localDateTimeToDate(dateTimeNow));

        visitorService.saveRecord(visitorRecord);

        List<VisitorRecordView> listResult = visitorService.getVisitorRecordList(1);

        return Result.result(listResult);
    }


    @GetMapping("list")
    public Result getVisitorRecordList(){

        List<VisitorRecordView> listResult = visitorService.getVisitorRecordList(1);

        return Result.result(listResult);
    }


}
