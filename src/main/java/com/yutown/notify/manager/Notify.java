package com.yutown.notify.manager;

import com.yutown.notify.exception.NotifyException;
import com.yutown.notify.model.enums.NotifyTypeEnum;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author dong jing xi
 * @date 2020/10/22 1:44
 **/
@Slf4j
public class Notify {

    @Resource
    private List<BaseNotify> notifies;

    // todo 选择多个机器人
    public void send(NotifyTypeEnum notifyTypeEnum, String message) {
        Optional<BaseNotify> first = notifies.stream().filter(x -> x.notifyType().equals(notifyTypeEnum)).findFirst();
        if (first.isPresent()) {
            first.get().notify(message);
        } else {
            log.error("类型匹配错误：{}", notifyTypeEnum);
            throw new NotifyException("类型匹配错误");
        }
    }

}
