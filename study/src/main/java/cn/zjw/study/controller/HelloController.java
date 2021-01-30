package cn.zjw.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author zjw
 * @createTime 2021/1/29 12:49
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(String msg) {
        return "hello, " + msg;
    }

}
