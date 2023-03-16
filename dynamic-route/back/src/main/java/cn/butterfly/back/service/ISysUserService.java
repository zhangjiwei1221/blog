package cn.butterfly.back.service;

import cn.butterfly.back.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务类
 *
 * @author zjw
 * @date 2021-12-11
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 返回用户登录 token
     *
	 * @param sysUser 用户
     * @return token
     */
    String login(SysUser sysUser);

    /**
     * 根据 token 获取用户
     *
	 * @param token token
     * @return 用户
     */
    SysUser getUserByToken(String token);

}
