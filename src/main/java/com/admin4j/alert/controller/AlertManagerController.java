package com.admin4j.alert.controller;

import com.admin4j.alert.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andanyang
 * @since 2022/12/9 13:38
 */
@RestController
public class AlertManagerController {

    @Autowired
    ISendMessageService sendMessageService;

    @PostMapping("AlertManager")
    public void alert(@RequestBody String requestBody) {
        sendMessageService.sendAlertManager(requestBody);
    }
}
