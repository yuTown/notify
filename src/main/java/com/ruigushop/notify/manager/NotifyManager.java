package com.ruigushop.notify.manager;

import com.ruigu.rbox.cloud.kanai.model.ResponseCode;
import com.ruigu.rbox.cloud.kanai.util.JsonUtil;
import com.ruigushop.notify.config.NotifyProperties;
import com.ruigushop.notify.model.SendRobotMessageReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * 群机器人 发消息
 *
 * @author dongjingxi
 * @date 2020/10/11 13:20 下午
 */
@Slf4j
public class NotifyManager {

    @Resource
    private Environment environment;

    @Resource
    private NotifyProperties notifyProperties;

    @Resource
    private RestTemplate restTemplate;


    private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=";

    private static final int MAX_LENGTH = 2048;

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
        if (message.length() > MAX_LENGTH) {
            log.error(String.format("超过企业微信发送消息最大长度,当前信息%s", message));
            message = "超过企业微信发送消息最大长度,请去日志中查询详细信息";
        }
        try {
            String url = SEND_MESSAGE_URL + notifyProperties.getRobotKey();

            SendRobotMessageReq sendRobotMessageReq = SendRobotMessageReq.getTextMessageNoticeResearch(message);
            sendRobotMessageReq.getText().setMentioned_mobile_list(emptyListIfNull(notifyProperties.getUser().getMobile()));
            sendRobotMessageReq.getText().setMentioned_list(emptyListIfNull(notifyProperties.getUser().getUserId()));

            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, sendRobotMessageReq, Object.class);

            if (!Objects.equals(responseEntity.getStatusCode().value(), ResponseCode.SUCCESS.getCode())) {
                log.error("restTemplate send notify fail message{} response{} ", message, JsonUtil.toJsonString(responseEntity));
            }

            Map<String, Object> map = JsonUtil.objectToMap(responseEntity);

            // 消息消息发送成功
            if (!"0".equals(map.get("errcode").toString())) {
                log.error("notification has error! message: body {} message{}", message, map.get("errmsg"));
            }
        } catch (Exception e) {
            log.error("notification has error! message: body {} error message {} ", message, e.getMessage());
            log.error("error exception", e);
        }
    }


    public List<String> emptyListIfNull(List<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }

}
