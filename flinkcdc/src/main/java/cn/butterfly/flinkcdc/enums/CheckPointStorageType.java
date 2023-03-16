package cn.butterfly.flinkcdc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 检查点存储类型
 *
 * @author zjw
 * @date 2023-03-14
 */
@Getter
@AllArgsConstructor
public enum CheckPointStorageType {

    /**
     * 文件,默认方式
     */
    FILE("file:///"),

    /**
     * HDFS
     */
    HDFS("hdfs://");

    /**
     * 前缀
     */
    private final String prefix;

}
