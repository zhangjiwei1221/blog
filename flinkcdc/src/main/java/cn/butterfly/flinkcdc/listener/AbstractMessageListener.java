package cn.butterfly.flinkcdc.listener;

import cn.butterfly.flinkcdc.pojo.WithoutSchemeMessage;
import cn.butterfly.flinkcdc.properties.DataSourceProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.ParameterizedType;

/**
 * 消息监听处理抽象类
 *
 * @author zjw
 * @date 2023-03-16
 */
@Slf4j
public abstract class AbstractMessageListener<E> implements FlinkCdcListener<String> {

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(String message) {
        var includeScheme = dataSourceProperties.getIncludeScheme();
        if (includeScheme) {
            // 处理包含 scheme 的情况
            return;
        }
        try {
            var entityMessage = objectMapper.readValue(message, new TypeReference<WithoutSchemeMessage<E>>() {});
            var before = entityMessage.getBefore();
            var after = entityMessage.getAfter();
            switch (entityMessage.getOp()) {
                case READ -> read(serializer(after));
                case CREATE -> create(serializer(after));
                case DELETE -> delete(serializer(before));
                case UPDATE -> update(serializer(before), serializer(after));
            }
        } catch (Exception e) {
            log.error("序列化消息失败", e);
        }
    }

    /**
     * 序列化实体
     *
	 * @param entity 实体
     * @return 序列化后的实体
     */
    @SuppressWarnings("all")
    private E serializer(E entity) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(entity),
                (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    /**
     * 读取
     *
     * @param entity 实体
     */
    public void read(E entity) {
//        log.info("读取到实体: {}", entity);
    }

    /**
     * 新增
     *
     * @param entity 实体
     */
    public void create(E entity) {
        log.info("新增实体: {}", entity);
    }

    /**
     * 删除
     *
     * @param entity 实体
     */
    public void delete(E entity) {
        log.info("删除实体: {}", entity);
    }

    /**
     * 修改
     *
     * @param before 修改前信息
     * @param after 修改后信息
     */
    public void update(E before, E after) {
        log.info("实体修改前: {}, 实体修改后: {}", before, after);
    }

}
