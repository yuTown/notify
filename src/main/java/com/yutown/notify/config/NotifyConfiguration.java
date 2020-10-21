package com.yutown.notify.config;

import com.yutown.notify.manager.Notify;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author bairenjie
 * @date 2020/10/11 16:57
 */
@ComponentScan(basePackages = {"com.yutown.notify.manager"})
@Import(value = {Notify.class, NotifyProperties.class, WorkWeChatRobotConfig.class})
@Configuration
public class NotifyConfiguration {
}
