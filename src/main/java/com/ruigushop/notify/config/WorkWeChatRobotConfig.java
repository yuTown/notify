package com.ruigushop.notify.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author Wangchong
 * @date 2020/7/1 11:03
 */
public class WorkWeChatRobotConfig {

    @Resource
    private NotifyProperties notifyProperties;

    @Bean
    public WorkWeChatRobotRequestInterceptor requestInterceptor() {
        return new WorkWeChatRobotRequestInterceptor();
    }

    private class WorkWeChatRobotRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate template) {
            template.query("key", notifyProperties.getRobotKey());
        }
    }
}
