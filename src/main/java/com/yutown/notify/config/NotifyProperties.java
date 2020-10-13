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

    private NotifyUser user;
    private String robotKey;
    private boolean enabledSend;

    @Data
    public static class NotifyUser {

        /**
         * 企业微信用户id
         */
        private List<String> userId;

        private List<String> mobile;

    }

}