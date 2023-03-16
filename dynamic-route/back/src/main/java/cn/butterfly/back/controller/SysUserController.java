package cn.butterfly.back.controller;

import cn.butterfly.back.service.ISysUserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 用户控制器
 *
 * @author zjw
 * @date 2021-12-11
 */
@RestController
@RequestMapping("/api/system/user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;



}
