package com.yutown.notify.manager;

import com.yutown.notify.config.NotifyProperties;
import com.yutown.notify.model.enums.NotifyTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static com.yutown.notify.util.Utils.objectToMap;

/**
 * 群机器人 发消息
 *
 * @author dongjingxi
 * @date 2020/10/11 13:20 下午
 */
@Slf4j
public abstract class BaseNotify {

    @Resource
    private Environment environment;

    @Resource
    private NotifyProperties notifyProperties;

    @Resource
    private RestTemplate restTemplate;


    private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=";

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
//            message = String.format("运行环境:%s, %s", Arrays.toString(environment.getActiveProfiles()), message);
            send(message);
        }
    }

    /**
     * 发送消息企业微信消息.
     */
    private void send(String message) {
        if (message.length() > getLength()) {
            log.error(String.format("超过企业微信发送消息最大长度,当前信息%s", message));
            message = "超过企业微信发送消息最大长度,请去日志中查询详细信息";
        }

        try {
            String url = SEND_MESSAGE_URL + notifyProperties.getRobotKey();

            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, buildMessage(message), Object.class);

            if (!Objects.equals(responseEntity.getStatusCode().value(), HttpStatus.OK.value())) {
                log.error("restTemplate send notify fail message{} response{} ", message, responseEntity);
            }

            Map<String, Object> map = objectToMap(responseEntity.getBody());

            // 消息消息发送成功
            if (!"0".equals(map.get("errcode").toString())) {
                log.error("notification has error! message: body {} message{}", message, map.get("errmsg"));
            }
        } catch (Exception e) {
            log.error("notification has error! message: body {} error message {} ", message, e.getMessage());
            log.error("error exception", e);
        }
    }

    protected abstract Object buildMessage(String message);

    protected abstract int getLength();

    protected abstract NotifyTypeEnum notifyType();


}
