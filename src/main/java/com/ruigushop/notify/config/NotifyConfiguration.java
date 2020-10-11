package com.ruigushop.notify.config;

import com.ruigushop.notify.manager.NotifyManager;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author bairenjie
 * @date 2020/10/11 16:57
 */
@Import(value = {NotifyManager.class, NotifyProperties.class, WorkWeChatRobotConfig.class})
@Configuration
public class NotifyConfiguration {
}
