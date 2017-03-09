package com.github.anddd7.dao.impl;

import com.github.anddd7.dao.MessageDao;
import com.github.anddd7.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageDaoImpl implements MessageDao {
    private static final Logger log = LoggerFactory.getLogger(MessageDaoImpl.class);

    public void sendMessage(Message msg) {
        log.debug("收到消息{}", msg.toString());
    }

    public Message receiveMessage() {
        log.debug("发送消息");
        return new Message("back", "地瓜地瓜 ,我是土豆 .");
    }
}
