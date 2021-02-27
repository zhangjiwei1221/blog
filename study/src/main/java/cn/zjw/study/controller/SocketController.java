package cn.zjw.study.controller;

import org.springframework.web.bind.annotation.*;

/**
 * SocketController
 *
 * @author zjw
 * @createTime 2021/2/25 13:22
 */
@RestController
public class SocketController {

    @PostMapping("/socket/{msg}")
    public String socket(@PathVariable String msg) {
        System.out.println(msg);
        return msg;
    }

}
