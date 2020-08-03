package com.xieyue.jwt.controller;

import com.xieyue.jwt.dao.entity.Visitor;
import com.xieyue.jwt.service.visitor.VisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @ClassName :   ThymeleafController
 * @Description :
 * @Author :      XieYue@gmail.com
 * @Date: 2020-08-03 23:49
 */

@Controller
@RequestMapping("temp")
public class ThymeleafController {

    @Resource(name = "visitorServiceImpl")
    private VisitorService visitorService;


    @GetMapping("/index")
    public String showPage(Model model){
        model.addAttribute("message","hello the world~~");
        return "index";
    }

    @GetMapping("/visitor")
    public ModelAndView showDetail(String visitorId){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("visitor");

        Visitor visitor = visitorService.findById(1);
        mv.addObject("visitor", visitor);

        return mv;
    }

}
