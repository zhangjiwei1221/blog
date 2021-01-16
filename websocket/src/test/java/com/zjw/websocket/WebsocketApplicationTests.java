package com.zjw.websocket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class WebsocketApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void jdbcTest() {
        String sql = "select * from user";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.print(entry.getKey() + " : " + entry.getValue() + "\t");
            }
            System.out.println();
        }
    }

}
