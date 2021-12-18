package cn.butterfly.tree.config;

import cn.butterfly.tree.constant.BaseConstants;
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
		this.strictInsertFill(metaObject, BaseConstants.GMT_CREATE_FILED, LocalDateTime.class, LocalDateTime.now());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, BaseConstants.GMT_MODIFIED_FILED, LocalDateTime.class, LocalDateTime.now());
	}

}
