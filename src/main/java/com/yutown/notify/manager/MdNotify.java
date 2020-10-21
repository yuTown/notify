package com.yutown.notify.manager;

import com.yutown.notify.constants.Constants;
import com.yutown.notify.model.enums.NotifyTypeEnum;
import com.yutown.notify.model.message.SendMdMessage;
import org.springframework.stereotype.Component;

/**
 * @author dong jing xi
 * @date 2020/10/22 2:20
 **/
@Component
public class MdNotify extends BaseNotify {
    @Override
    protected Object buildMessage(String message) {
        return SendMdMessage.getMdMessage(message);
    }

    @Override
    protected int getLength() {
        return Constants.MD_MAX_LENGTH;
    }

    @Override
    protected NotifyTypeEnum notifyType() {
        return NotifyTypeEnum.MARK_DOWN;
    }
}
