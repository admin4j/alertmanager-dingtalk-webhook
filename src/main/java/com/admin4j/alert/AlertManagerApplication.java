package com.admin4j.alert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author andanyang
 * @since 2022/12/9 13:37
 */
@ConfigurationPropertiesScan("com.admin4j.alert.props")
@SpringBootApplication
public class AlertManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertManagerApplication.class);
    }
}
