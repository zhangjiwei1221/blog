package cn.zjw.study.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KotlinDemo {

    @GetMapping("/kotlin")
    fun kotlin() = "Hello, kotlin"

}
