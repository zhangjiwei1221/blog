package cn.butterfly.flinkcdc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 *
 * @author zjw
 * @date 2023-03-16
 */
@Getter
@AllArgsConstructor
public enum OpType {

    /**
     * 增
     */
    CREATE("c"),

    /**
     * 删
     */
    DELETE("d"),

    /**
     * 改
     */
    UPDATE("u"),

    /**
     * 查
     */
    READ("r");

    /**
     * 操作类型
     */
    private final String type;

    /**
     * 根据操作类型获取对应对象
     *
	 * @param type 操作类型
     * @return 操作类型枚举对象
     */
    public static OpType getObjWithType(String type) {
        for (OpType opType : values()) {
            if (opType.getType().equals(type)) {
                return opType;
            }
        }
        return OpType.READ;
    }

}
