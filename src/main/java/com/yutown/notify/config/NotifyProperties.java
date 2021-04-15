package com.yutown.notify.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongjingxi
 * @date 2020/10/11
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "work-we-chat")
public class NotifyProperties {

    // TODO: 2021/4/15 渠道配置
    /**
     * 1. 企业微信机器人
     * 2. 企业微信应用(收,发消息)
     */

    private NotifyUser user;
    private String robotKey;
    private boolean enabledSend = false;

    @Data
    public static class NotifyUser {

        /**
         * 企业微信用户id, @all发送全员
         */
        private List<String> userId;

        /**
         * 企业微信用户手机号
         */
        private List<String> mobile;

    }

}