package com.github.anddd7.dao;

import com.github.anddd7.domain.Message;

public interface MessageDao {
    void sendMessage(Message msg);

    Message receiveMessage();
}
