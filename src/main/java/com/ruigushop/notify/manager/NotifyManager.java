package com.ruigushop.notify.manager;

import com.ruigu.rbox.cloud.kanai.util.JsonUtil;
import com.ruigushop.notify.config.NotifyProperties;
import com.ruigushop.notify.model.SendRobotMessageReq;
import com.ruigushop.notify.feign.WorkWeChatRobotFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * 群机器人 发消息
 *
 * @author chusen
 * @date 2020/7/16 1:20 下午
 */
@Slf4j
@Component
public class NotifyManager {

    @Autowired
    private Environment environment;

    @Resource
    private WorkWeChatRobotFeign workWeChatRobotFeign;

    @Resource
    private NotifyProperties notifyProperties;


    /**
     * 发送告警信息
     *
     * @param message
     * @return
     */
    @Async
    public void notify(String message) {
        // 开启了 才发送
        if (notifyProperties.isEnabledSend()) {
            // 拼接运行环境
            message = String.format("运行环境:%s, %s", Arrays.toString(environment.getActiveProfiles()), message);
            sendMessage(message);
        }
    }

    /**
     * 发送消息企业微信消息.
     */
    private void sendMessage(String message) {
        if (message.length() > 2048) {
            log.error(String.format("超过企业微信发送消息最大长度,当前信息%s", message));
            workWeChatRobotFeign.sendMessage(SendRobotMessageReq.getTextMessageNoticeResearch("超过企业微信发送消息最大长度,请去日志中查询详细信息"));
            return;
        }
        try {
            SendRobotMessageReq sendRobotMessageReq = SendRobotMessageReq.getTextMessageNoticeResearch(message);
            sendRobotMessageReq.getText().setMentioned_mobile_list(notifyProperties.getUser().getMobile());
            sendRobotMessageReq.getText().setMentioned_list(notifyProperties.getUser().getUserId());
            Object response = workWeChatRobotFeign.sendMessage(sendRobotMessageReq);
            Map<String, Object> map = JsonUtil.objectToMap(response);
            // 消息消息发送成功
            if (!"0".equals(map.get("errcode").toString())) {
                log.error("notification has error! message: body {} message{}", message, map.get("errmsg"));
            }
        } catch (Exception e) {
            log.error("notification has error! message: body {} error message {} ", message, e.getMessage());
            log.error("error exception", e);
        }
    }

}
