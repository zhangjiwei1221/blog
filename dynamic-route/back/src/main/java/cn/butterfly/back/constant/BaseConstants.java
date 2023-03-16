package cn.butterfly.back.constant;

/**
 * 基础常量类
 *
 * @author zjw
 * @date 2021-12-03
 */
public class BaseConstants {

    /**
     * 匹配所有路径
     */
    public static final String ALL_PATTERN = "/**";

    /**
     * 允许所有来源
     */
    public static final String ALL_ORIGIN = "*";

    /**
     * 数据库日期格式化规则
     */
    public static final String DB_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 请求成功状态码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 请求失败状态码
     */
    public static final int ERROR_CODE = 500;

    /**
     * 请求失败状态码
     */
    public static final long ROOT_NODE = 0L;

    /**
     * 创建时间字段名
     */
    public static final String GMT_CREATE_FILED = "gmtCreate";

    /**
     * 修改时间字段名
     */
    public static final String GMT_MODIFIED_FILED = "gmtModified";

    /**
     * 默认页码
     */
    public static final String DEFAULT_PAGE_NUM = "1";

    /**
     * 默认分页列表信息条数
     */
    public static final String DEFAULT_PAGE_SIZE = "10";

    /**
     * 用户名字符串
     */
    public static final String USERNAME_STR = "username";

    private BaseConstants() {}

}
