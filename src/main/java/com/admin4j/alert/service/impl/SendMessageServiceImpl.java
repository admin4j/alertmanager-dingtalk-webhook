package com.admin4j.alert.service.impl;

import com.admin4j.alert.enetity.DingTalkConfig;
import com.admin4j.alert.props.AlertProperties;
import com.admin4j.alert.service.ISendMessageService;
import com.admin4j.chatbot.dingtalk.DingRobot;
import com.admin4j.chatbot.dingtalk.request.MarkdownRobotRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author andanyang
 * @since 2022/12/9 13:56
 */
@Service
public class SendMessageServiceImpl implements ISendMessageService {

    @Autowired
    private AlertProperties alertProperties;

    /**
     * {"receiver":"web\\.hook","status":"firing","alerts":[{"status":"firing","labels":{"alertname":"hostCpuUsageAlert","instance":"192.168.1.12:9567","severity":"page"},"annotations":{"description":"192.168.1.12:9567 CPU usage above 85% (current value: 0.13683333333264994)","summary":"Instance 192.168.1.12:9567 CPU usgae high"},"startsAt":"2022-12-08T09:37:48.961Z","endsAt":"0001-01-01T00:00:00Z","generatorURL":"http://node13:9090/graph?g0.expr=sum+by%28instance%29+%28avg+without%28cpu%29+%28irate%28node_cpu_seconds_total%7Bmode%21%3D%22idle%22%7D%5B5m%5D%29%29%29+%3E+0.07\u0026g0.tab=1","fingerprint":"f05d26ab06192e0e"}],"groupLabels":{"alertname":"hostCpuUsageAlert","severity":"page"},"commonLabels":{"alertname":"hostCpuUsageAlert","instance":"192.168.1.12:9567","severity":"page"},"commonAnnotations":{"description":"192.168.1.12:9567 CPU usage above 85% (current value: 0.13683333333264994)","summary":"Instance 192.168.1.12:9567 CPU usgae high"},"externalURL":"http://node13:9093","version":"4","groupKey":"{}:{alertname=\"hostCpuUsageAlert\", severity=\"page\"}","truncatedAlerts":0}
     *
     * @param alertStr
     */
    @Override
    public void sendAlertManager(String alertStr) {

        JSONObject alertObject = JSON.parseObject(alertStr);

        JSONArray alerts = alertObject.getJSONArray("alerts");
        for (Object json : alerts) {
            JSONObject alert = (JSONObject) json;
            sentAlert(alert);
        }
    }

    private void sentAlert(JSONObject alert) {
        JSONObject labels = alert.getJSONObject("labels");
        JSONObject annotations = alert.getJSONObject("annotations");
        String generatorURL = alert.getString("generatorURL");
        String status = alert.getString("status");

        DingTalkConfig dingTalk = alertProperties.getDingTalk();
        if (dingTalk == null) {
            return;
        }
        DingRobot dingRobot = new DingRobot(dingTalk.getAssessToken(), dingTalk.getSecret());

        String summary = annotations.getString("summary");
        String description = annotations.getString("description");

        MarkdownRobotRequest robotRequest = new MarkdownRobotRequest();
        robotRequest.setTitle(summary);

        String text = "# " + summary + "\n## 状态：" + status + "\n> 描述：" + description + "\n### labels:";
        for (String label : labels.keySet()) {
            text += "  \n- " + label + ": " + labels.getString(label);
        }

        text += "\n\n[" + generatorURL + "](" + generatorURL + ")";

        robotRequest.setText(text);
        dingRobot.send(robotRequest);
    }
}
