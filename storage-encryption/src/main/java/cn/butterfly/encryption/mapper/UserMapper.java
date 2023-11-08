package cn.butterfly.encryption.mapper;

import cn.butterfly.encryption.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 mapper 接口类
 *
 * @author zjw
 * @date 2023-10-27
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
