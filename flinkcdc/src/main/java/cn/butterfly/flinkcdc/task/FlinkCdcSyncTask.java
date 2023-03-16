package cn.butterfly.flinkcdc.task;

import cn.butterfly.flinkcdc.event.FlinkCdcMessageEvent;
import cn.butterfly.flinkcdc.pojo.WithSchemeMessage;
import cn.butterfly.flinkcdc.pojo.WithoutSchemeMessage;
import cn.butterfly.flinkcdc.properties.CheckPointProperties;
import cn.butterfly.flinkcdc.properties.DataSourceProperties;
import cn.butterfly.flinkcdc.util.SavepointUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.DiscardingSink;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import java.io.File;

/**
 * 数据同步任务
 *
 * @author zjw
 * @date 2023-03-14
 */
@Slf4j
@Component
public class FlinkCdcSyncTask implements CommandLineRunner, ApplicationContextAware {

    @Resource
    private CheckPointProperties checkPointProperties;

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private SourceFunction<String> dataSourceFunction;

    private static ApplicationContext applicationContext;

    private static boolean includeScheme;

    private static ObjectMapper objectMapper;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        FlinkCdcSyncTask.applicationContext = applicationContext;
    }

    @Autowired
    public void setIncludeScheme() {
        FlinkCdcSyncTask.includeScheme = dataSourceProperties.getIncludeScheme();
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        FlinkCdcSyncTask.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        // 检查点恢复配置
        var saveDir = checkPointProperties.getSaveDir();
        var folder = new File(saveDir);
        if (!folder.exists() && !folder.isDirectory()) {
            if (!folder.mkdirs()) {
                throw new IllegalStateException("文件夹创建失败");
            }
        }
        var dataSourceType = dataSourceProperties.getType().name().toLowerCase();
        var dataSourceSaveDir = saveDir + File.separator + dataSourceType;
        var savepointDir = SavepointUtils.getSavepointRestore(dataSourceSaveDir);
        var configuration = new Configuration();
        if (savepointDir != null) {
            var savepointRestoreSettings = SavepointRestoreSettings.forPath(savepointDir);
            SavepointRestoreSettings.toConfiguration(savepointRestoreSettings, configuration);
        }
        var env = StreamExecutionEnvironment.getExecutionEnvironment(configuration);
        env.enableCheckpointing(checkPointProperties.getInterval(), CheckpointingMode.EXACTLY_ONCE);
        var checkpointConfig = env.getCheckpointConfig();
        checkpointConfig.setCheckpointStorage(checkPointProperties.getStorageType().getPrefix() + dataSourceSaveDir);
        // 运行任务
        env.addSource(dataSourceFunction).addSink(new JsonValSink()).setParallelism(Runtime.getRuntime().availableProcessors());
        env.execute();
    }

    /**
     * Json 数据处理
     */
    static class JsonValSink extends DiscardingSink<String> {

        @Override
        public void invoke(String value, Context context) {
            try {
                if (includeScheme) {
                    var withSchemeMessage = objectMapper.readValue(value, new TypeReference<WithSchemeMessage<Object>>() {});
                    var tableName = withSchemeMessage.getPayload().getSource().getTable();
                    applicationContext.publishEvent(new FlinkCdcMessageEvent(FlinkCdcSyncTask.class, tableName, value));
                    return;
                }
                var withoutSchemeMessage = objectMapper.readValue(value, new TypeReference<WithoutSchemeMessage<Object>>() {});
                var tableName = withoutSchemeMessage.getSource().getTable();
                applicationContext.publishEvent(new FlinkCdcMessageEvent(FlinkCdcSyncTask.class, tableName, value));
            } catch (Exception e) {
                log.error("处理消息出错: ", e);
            }
        }

    }

}
