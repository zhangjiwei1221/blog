package cn.zjw.flowable.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author zjw
 * @date 2021/3/19
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

}
