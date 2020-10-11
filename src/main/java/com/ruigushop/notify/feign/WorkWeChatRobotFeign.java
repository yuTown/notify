package com.ruigushop.notify.feign;

import com.ruigushop.notify.config.WorkWeChatRobotConfig;
import com.ruigushop.notify.model.SendRobotMessageReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Wangchong
 * @date 2020/7/1 10:48
 */
@FeignClient(name = "workWeChatRobot", url = "https://qyapi.weixin.qq.com", configuration = WorkWeChatRobotConfig.class)
public interface WorkWeChatRobotFeign {
    /***
     * 使用企业微信机器人发送消息
     * @param sendRobotMessageReq 消息
     * @return
     */
    @PostMapping(value = "/cgi-bin/webhook/send")
    Object sendMessage(SendRobotMessageReq sendRobotMessageReq);
}
