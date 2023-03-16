package cn.butterfly.flinkcdc.config;

import cn.butterfly.flinkcdc.properties.CheckPointProperties;
import cn.butterfly.flinkcdc.properties.DataSourceProperties;
import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.oracle.OracleSource;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 项目配置类
 *
 * @author zjw
 * @date 2023-03-14
 */
@Configuration
public class FlinkCdcConfiguration {

    @Bean
    public CheckPointProperties checkPointProperties() {
        return new CheckPointProperties();
    }

    @Bean
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public SourceFunction<String> dataSourceFunction(DataSourceProperties dataSourceProperties) {
        var configProperties = dataSourceProperties.getConfigProperties();
        return switch (dataSourceProperties.getType()) {
            case MYSQL -> mySqlSource(configProperties);
            case ORACLE -> oracleSource(configProperties);
        };
    }

    /**
     * 获取 MySQL 数据源
     *
     * @param dataSourceProperties 数据源配置
     * @return MySQL 数据源
     */
    @SuppressWarnings("all")
    private SourceFunction<String> mySqlSource(DataSourceProperties dataSourceProperties) {
        var dataSourceAddr = dataSourceProperties.getDataSourceAddr();
        var prop = new Properties();
        // 详见 https://github.com/ververica/flink-cdc-connectors/wiki/FAQ(ZH)#%E9%80%9A%E7%94%A8-faq Q5
        prop.setProperty("bigint.unsigned.handling.mode","long");
        prop.setProperty("decimal.handling.mode","double");
        return MySqlSource.<String>builder()
                .hostname(dataSourceAddr.hostname())
                .port(dataSourceAddr.port())
                .databaseList(dataSourceProperties.getDatabase())
                .tableList(dataSourceProperties.getTableList().toArray(new String[0]))
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .debeziumProperties(prop)
                .deserializer(new JsonDebeziumDeserializationSchema(dataSourceProperties.getIncludeScheme()))
                .build();
    }

    /**
     * 获取 ORACLE 数据源
     *
     * @param dataSourceProperties 数据源配置
     * @return ORACLE 数据源
     */
    private SourceFunction<String> oracleSource(DataSourceProperties dataSourceProperties) {
        var dataSourceAddr = dataSourceProperties.getDataSourceAddr();
        var prop = new Properties();
        // 详见 https://github.com/ververica/flink-cdc-connectors/wiki/FAQ(ZH)#%E9%80%9A%E7%94%A8-faq Q5
        prop.setProperty("bigint.unsigned.handling.mode","long");
        prop.setProperty("decimal.handling.mode","double");
        Boolean includeDdl = dataSourceProperties.getIncludeScheme();
        if (!Boolean.TRUE.equals(includeDdl)) {
            // 详见 https://github.com/ververica/flink-cdc-connectors/wiki/FAQ(ZH)#oracle-cdc-faq Q1
            prop.setProperty("log.mining.strategy", "online_catalog");
            prop.setProperty("log.mining.continuous.mine", "true");
        }
        return OracleSource.<String>builder()
                .hostname(dataSourceAddr.hostname())
                .port(dataSourceAddr.port())
                .database(dataSourceProperties.getDatabase())
                .tableList(dataSourceProperties.getTableList().toArray(new String[0]))
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .debeziumProperties(prop)
                .deserializer(new JsonDebeziumDeserializationSchema(includeDdl))
                .build();
    }

}
