package com.zjw.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HTMLController
 *
 * @author zjw
 * @createTime 2021/1/16 17:55
 */
@Controller
public class HTMLController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/info")
    public String info() {
        return "info";
    }

    @RequestMapping("/broadcast")
    public String broadcast() {
        return "broadcast";
    }
}
