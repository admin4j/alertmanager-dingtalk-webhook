package com.admin4j.alert.enetity;

import lombok.Data;

/**
 * 钉钉认证配置文件
 *
 * @author andanyang
 * @since 2022/12/9 15:41
 */
@Data
public class DingTalkConfig {

    /**
     * assessToken
     */
    private String assessToken;
    /**
     * secret
     */
    private String secret;
}
