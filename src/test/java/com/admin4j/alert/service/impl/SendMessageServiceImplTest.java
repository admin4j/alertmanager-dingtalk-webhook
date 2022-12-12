package com.admin4j.alert.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author andanyang
 * @since 2022/12/9 15:24
 */
@SpringBootTest
class SendMessageServiceImplTest {

    @Autowired
    private SendMessageServiceImpl sendMessageService;

    @Test
    void sendAlertManager() {

        String alertStr = "{\"receiver\":\"web\\\\.hook\",\"status\":\"firing\",\"alerts\":[{\"status\":\"firing\",\"labels\":{\"alertname\":\"hostCpuUsageAlert\",\"instance\":\"192.168.1.12:9567\",\"severity\":\"page\"},\"annotations\":{\"description\":\"192.168.1.12:9567 CPU usage above 85% (current value: 0.13683333333264994)\",\"summary\":\"Instance 192.168.1.12:9567 CPU usgae high\"},\"startsAt\":\"2022-12-08T09:37:48.961Z\",\"endsAt\":\"0001-01-01T00:00:00Z\",\"generatorURL\":\"http://node13:9090/graph?g0.expr=sum+by%28instance%29+%28avg+without%28cpu%29+%28irate%28node_cpu_seconds_total%7Bmode%21%3D%22idle%22%7D%5B5m%5D%29%29%29+%3E+0.07\\u0026g0.tab=1\",\"fingerprint\":\"f05d26ab06192e0e\"}],\"groupLabels\":{\"alertname\":\"hostCpuUsageAlert\",\"severity\":\"page\"},\"commonLabels\":{\"alertname\":\"hostCpuUsageAlert\",\"instance\":\"192.168.1.12:9567\",\"severity\":\"page\"},\"commonAnnotations\":{\"description\":\"192.168.1.12:9567 CPU usage above 85% (current value: 0.13683333333264994)\",\"summary\":\"Instance 192.168.1.12:9567 CPU usgae high\"},\"externalURL\":\"http://node13:9093\",\"version\":\"4\",\"groupKey\":\"{}:{alertname=\\\"hostCpuUsageAlert\\\", severity=\\\"page\\\"}\",\"truncatedAlerts\":0}";
        sendMessageService.sendAlertManager(alertStr);
    }
}