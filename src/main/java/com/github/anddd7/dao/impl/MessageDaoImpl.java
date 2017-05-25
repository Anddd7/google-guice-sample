package com.github.anddd7.dao.impl;

import com.github.anddd7.dao.MessageDao;
import com.github.anddd7.domain.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDaoImpl implements MessageDao {
    public void sendMessage(Message msg) {
        log.debug("收到消息{}", msg.toString());
    }

    public Message receiveMessage() {
        log.debug("发送消息");
        return new Message("back", "地瓜地瓜 ,我是土豆 .");
    }
}
