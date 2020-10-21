package com.yutown.notify.manager;

import com.yutown.notify.config.NotifyProperties;
import com.yutown.notify.constants.Constants;
import com.yutown.notify.model.enums.NotifyTypeEnum;
import com.yutown.notify.model.message.SendTextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.yutown.notify.util.Utils.*;

/**
 * 群机器人 发消息
 *
 * @author dongjingxi
 * @date 2020/10/11 13:20 下午
 */
@Slf4j
@Component
public class TextNotify extends BaseNotify {

    @Resource
    private NotifyProperties notifyProperties;

    @Override
    protected Object buildMessage(String message) {
        SendTextMessage sendTextMessage = SendTextMessage.getTextMessageNoticeResearch(message);
        sendTextMessage.getText().setMentioned_mobile_list(emptyStringListIfNull(notifyProperties.getUser().getMobile()));
        sendTextMessage.getText().setMentioned_list(emptyStringListIfNull(notifyProperties.getUser().getUserId()));
        return sendTextMessage;
    }

    @Override
    protected int getLength() {
        return Constants.TEXT_MAX_LENGTH;
    }


    @Override
    protected NotifyTypeEnum notifyType() {
        return NotifyTypeEnum.TEXT;
    }

}
