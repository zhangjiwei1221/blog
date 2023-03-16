package cn.butterfly.back.service.impl;

import cn.butterfly.back.entity.SysUser;
import cn.butterfly.back.exception.ApiException;
import cn.butterfly.back.mapper.SysUserMapper;
import cn.butterfly.back.service.ISysUserService;
import cn.butterfly.back.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * 用户服务实现类
 *
 * @author zjw
 * @date 2021-12-11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();

    @Override
    public String login(SysUser sysUser) {
        SysUser result = baseMapper.selectOne(userWrapper.eq(SysUser::getUsername, sysUser.getUsername()));
        if (result == null || !Objects.equals(result.getPassword(), sysUser.getPassword())) {
            throw new ApiException("用户名或密码错误");
        }
        return JwtUtils.create(sysUser);
    }

    @Override
    public SysUser getUserByToken(String token) {
        return baseMapper.selectOne(userWrapper.eq(SysUser::getUsername, JwtUtils.getUsername(token)));
    }

}
