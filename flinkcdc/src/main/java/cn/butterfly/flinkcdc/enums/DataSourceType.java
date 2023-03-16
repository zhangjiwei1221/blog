package cn.butterfly.flinkcdc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据源类型
 *
 * @author zjw
 * @date 2023-03-14
 */
@Getter
@AllArgsConstructor
public enum DataSourceType {

    /**
     * MySQL, 默认类型
     */
    MYSQL(1),

    /**
     * ORACLE
     */
    ORACLE(2);

    /**
     * 类型
     */
    private final int type;

}
