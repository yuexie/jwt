package com.xieyue.jwt.controller;

import com.xieyue.jwt.dao.entity.Visitor;
import com.xieyue.jwt.service.visitor.VisitorService;
import com.xieyue.jwt.service.visitor.VisitorServiceImpl;
import com.xieyue.jwt.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
