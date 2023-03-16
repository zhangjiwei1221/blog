package cn.butterfly.flinkcdc.config;

import cn.butterfly.flinkcdc.annotation.FlickCdcMessageListener;
import cn.butterfly.flinkcdc.event.FlinkCdcMessageEvent;
import cn.butterfly.flinkcdc.listener.FlinkCdcListener;
import com.google.common.collect.Maps;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 监听器容器配置类
 *
 * @author zjw
 * @date 2023-03-14
 */
@Configuration
public class ListenerContainerConfiguration implements ApplicationContextAware, SmartInitializingSingleton {

    private ConfigurableApplicationContext applicationContext;

    /**
     * 容器
     */
    private static final Map<String, Object> CONTAINER = Maps.newHashMap();

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        applicationContext.getBeansWithAnnotation(FlickCdcMessageListener.class)
                .entrySet()
                .stream()
                .filter(entry -> !ScopedProxyUtils.isScopedTarget(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .forEach(this::registerContainer);
    }

    /**
     * 注解 bean 到容器中
     *
	 * @param beanName bean 名称
	 * @param bean bean
     */
    private void registerContainer(String beanName, Object bean) {
        var clazz = AopProxyUtils.ultimateTargetClass(bean);
        var annotation = clazz.getAnnotation(FlickCdcMessageListener.class);
        var tableName = annotation.value().toUpperCase();
        CONTAINER.put(tableName, bean);
    }

    /**
     * 处理消息
     *
	 * @param flinkCdcMessageEvent 消息事件
     */
    @SuppressWarnings("all")
    @EventListener(FlinkCdcMessageEvent.class)
    public void handleMessage(FlinkCdcMessageEvent flinkCdcMessageEvent) {
        var bean = CONTAINER.get(flinkCdcMessageEvent.getTableName().toUpperCase());
        if (bean == null) {
            return;
        }
        if (bean instanceof FlinkCdcListener flinkCdcListener) {
            flinkCdcListener.onMessage(flinkCdcMessageEvent.getMessage());
        }
    }

}
