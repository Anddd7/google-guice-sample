package com.github.anddd7.service.impl;

import com.github.anddd7.dao.MessageDao;
import com.github.anddd7.dao.impl.MessageDaoImpl;
import com.github.anddd7.domain.Message;
import com.github.anddd7.service.MessageService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageServiceImpl implements MessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageDaoImpl.class);
    /**
     * 注入Dao
     */
    @Inject
    MessageDao messageDao;

    public void sendMessage(Message msg) {
        messageDao.sendMessage(msg);
    }

    public Message receiveMessage() {
        return messageDao.receiveMessage();
    }
}
