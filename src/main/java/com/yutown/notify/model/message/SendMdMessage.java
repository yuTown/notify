package com.yutown.notify.model.message;

import lombok.Data;
import lombok.Getter;

/**
 * @author dong jing xi
 * @date 2020/10/22 2:13
 **/
@Getter
public class SendMdMessage {

    private String msgtype;
    private SendMdMessageDTO markdown;

    public static SendMdMessage getMdMessage(String content) {
        SendMdMessage sendMdMessage = new SendMdMessage();
        sendMdMessage.msgtype = "markdown";
        SendMdMessageDTO sendMdMessageDTO = new SendMdMessageDTO();
        sendMdMessageDTO.setContent(content);
        sendMdMessage.markdown = sendMdMessageDTO;
        return sendMdMessage;
    }


    @Data
    public static class SendMdMessageDTO {
        private String content;
    }
}
