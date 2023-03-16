package cn.butterfly.back.controller;

import cn.butterfly.back.base.BaseResult;
import cn.butterfly.back.entity.SysUser;
import cn.butterfly.back.service.ISysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录控制器
 *
 * @author zjw
 * @date 2022-07-03
 */
@RestController
public class LoginController {

    @Resource
    private ISysUserService sysUserService;

    @PostMapping("/login")
    public BaseResult<String> login(@RequestBody SysUser sysUser) {
        return BaseResult.success(sysUserService.login(sysUser));
    }

}
