package com.zjw.stomp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HTMLController
 *
 * @author zjw
 * @createTime 2021/1/19 18:03
 */
@Controller
public class HTMLController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/greet")
    public String greet() {
        return "greet";
    }

}
