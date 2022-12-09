package com.admin4j.alert.service;

/**
 * @author andanyang
 * @since 2022/12/9 13:44
 */
public interface ISendMessageService {

    /**
     * 发送 AlertManager 告警
     *
     * @param alertStr
     */
    void sendAlertManager(String alertStr);
}
