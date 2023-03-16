package cn.butterfly.back.config;

import cn.butterfly.back.constant.BaseConstants;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis Plus 自动填充功能
 *
 * @author alicas
 * @date 2020-09-01
 */
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		strictInsertFill(metaObject, BaseConstants.GMT_CREATE_FILED, LocalDateTime.class, LocalDateTime.now());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		strictUpdateFill(metaObject, BaseConstants.GMT_MODIFIED_FILED, LocalDateTime.class, LocalDateTime.now());
	}

}
