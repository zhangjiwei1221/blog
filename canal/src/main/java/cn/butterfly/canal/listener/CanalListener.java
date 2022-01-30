package cn.butterfly.canal.listener;

import cn.butterfly.canal.config.CanalConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import oshi.util.Util;
import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Canal 监听
 *
 * @author zjw
 * @date 2022-01-27
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CanalListener {

    private final CanalConfig canalConfig;

    @PostConstruct
    private void init() {
        if (Boolean.TRUE.equals(canalConfig.getEnable())) {
            listen();
        }
    }

    /**
     * 监听 Canal 增量数据
     */
    private void listen() {
        new Thread(() -> {
            // 连接到 Canal
            CanalConnector connector = connect();
            try {
                // 循环获取数据库变更信息
                while (true) {
                    Message message = connector.getWithoutAck(canalConfig.getBatchSize());
                    long batchId = message.getId();
                    List<CanalEntry.Entry> entryList = message.getEntries();
                    if (batchId == -1 || entryList.isEmpty()) {
                        Util.sleep(500);
                    } else {
                        // 存在变更信息则打印
                        entryList.forEach(this::printEntry);
                    }
                    connector.ack(batchId);
                }
            } finally {
                connector.disconnect();
            }
        }).start();
    }

    /**
      * 连接到 Canal
      *
      * @return 连接信息
      */
    private CanalConnector connect() {
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(canalConfig.getHost(), canalConfig.getPort()),
                canalConfig.getTopic(),
                canalConfig.getUsername(),
                canalConfig.getPassword()
        );
        connector.connect();
        connector.subscribe();
        connector.rollback();
        return connector;
    }

    /**
     * 打印变更实体信息
     *
	 * @param entry 实体信息
     */
    @SneakyThrows
    private void printEntry(CanalEntry.Entry entry) {
        CanalEntry.EntryType entryType = entry.getEntryType();
        if (entryType == CanalEntry.EntryType.TRANSACTIONBEGIN || entryType == CanalEntry.EntryType.TRANSACTIONEND) {
            return;
        }
        String lineSeparator = System.lineSeparator();
        StringBuilder info = new StringBuilder(lineSeparator);
        info.append("==========数据变更信息==========").append(lineSeparator);
        CanalEntry.Header header = entry.getHeader();
        info.append(String.format("数据库.表名: %s.%s%n", header.getSchemaName(), header.getTableName()));
        info.append(String.format("操作类型: %s%n", header.getEventType()));
        CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
        for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
            CanalEntry.EventType eventType = rowChange.getEventType();
            if (eventType == CanalEntry.EventType.DELETE) {
                info.append(String.format("delete: %s%n", getDataInfo(rowData.getBeforeColumnsList())));
            } else if (eventType == CanalEntry.EventType.INSERT) {
                info.append(String.format("insert: %s%n", getDataInfo(rowData.getAfterColumnsList())));
            } else {
                info.append(String.format("update: %s%n", getDataInfo(rowData.getAfterColumnsList())));
            }
        }
        log.info(info.toString());
    }

    /**
     * 获取行更改信息
     *
	 * @param columns 行列表
     */
    private String getDataInfo(List<CanalEntry.Column> columns) {
        return JSON.toJSONString(
                columns.stream()
                        .collect(Collectors.toMap(CanalEntry.Column::getName, CanalEntry.Column::getValue))
        );
    }

}
