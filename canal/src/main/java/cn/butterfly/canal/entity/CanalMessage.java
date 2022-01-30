package cn.butterfly.canal.entity;

import lombok.Data;
import java.util.List;

/**
 * Canal 消息
 *
 * @author zjw
 * @date 2022-01-30
 */
@Data
public class CanalMessage<T> {

    /**
     * 数据库名
     */
    private String database;

    /**
     * 表名
     */
    private String table;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 变更数据
     */
    private List<T> data;

}
