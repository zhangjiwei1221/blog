package cn.butterfly.flinkcdc.properties;

import cn.butterfly.flinkcdc.enums.DataSourceType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源配置
 *
 * @author zjw
 * @date 2023-03-14
 */
@Getter
@Setter
@ConfigurationProperties(DataSourceProperties.PREFIX)
public class DataSourceProperties {

    /**
     * Prefix of {@link DataSourceProperties}.
     */
    public static final String PREFIX = "flinkcdc.data-source";

    /**
     * 冒号.
     */
    public static final String COLON = ":";

    /**
     * 数据源类型, 默认为 MySQL.
     */
    private DataSourceType type = DataSourceType.MYSQL;

    /**
     * 数据源地址, 格式 ip:port.
     */
    private String addr;

    /**
     * 数据源数据库名.
     */
    private String database;

    /**
     * 数据源认证用户名.
     */
    private String username;

    /**
     * 数据源认证密码.
     */
    private String password;

    /**
     * 是否包含 Scheme 处理, 默认 false.
     */
    private Boolean includeScheme = false;

    /**
     * 数据源地址.
     */
    private DataSourceAddr dataSourceAddr;

    /**
     * 表列表, 格式 schemaName.tableName.
     */
    private List<String> tableList = new ArrayList<>();

    /**
     * 获取数据源配置
     *
     * @return 数据源配置
     */
    public DataSourceProperties getConfigProperties() {
        var dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDataSourceAddr(new DataSourceAddr(this.addr));
        dataSourceProperties.setType(this.type);
        dataSourceProperties.setDatabase(this.database);
        dataSourceProperties.setTableList(this.tableList);
        dataSourceProperties.setUsername(this.username);
        dataSourceProperties.setPassword(this.password);
        dataSourceProperties.setIncludeScheme(this.includeScheme);
        return dataSourceProperties;
    }

    /**
     * 数据源地址 record
     */
    public record DataSourceAddr(String hostname, Integer port) {

        public DataSourceAddr(String addr) {
            this(addr.substring(0, addr.indexOf(DataSourceProperties.COLON)),
                    Integer.valueOf(addr.substring(addr.indexOf(DataSourceProperties.COLON) + 1)));
        }

    }

}
