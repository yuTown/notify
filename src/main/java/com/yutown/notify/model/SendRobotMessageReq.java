package com.yutown.notify.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author Wangchong
 * @date 2020/7/1 10:29
 */
@Getter
public class SendRobotMessageReq {

    private String msgtype;

    private SendRobotMessageDTO text;

    /***
     * 获取消息(文本格式,通知研发)
     * @param content
     * @return
     */
    public static SendRobotMessageReq getTextMessageNoticeResearch(String content) {
        SendRobotMessageReq sendRobotMessageReq = new SendRobotMessageReq();
        sendRobotMessageReq.msgtype = "text";
        SendRobotMessageDTO sendRobotMessageDTO = new SendRobotMessageDTO();
        sendRobotMessageDTO.setContent(content);
        sendRobotMessageReq.text = sendRobotMessageDTO;
        return sendRobotMessageReq;
    }

    @Data
    public static class SendRobotMessageDTO {
        /***
         * 文本内容，最长不超过2048个字节，必须是utf8编码
         */
        private String content;
        /***
         * userid的列表，提醒群中的指定成员(@某个成员)，@all表示提醒所有人，如果开发者获取不到userid，可以使用mentioned_mobile_list
         */
        private List<String> mentioned_list;
        /***
         * 手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人
         */
        private List<String> mentioned_mobile_list;
    }
}
