package com.github.anddd7.service;

import com.github.anddd7.domain.Message;

public interface MessageService {
    void sendMessage(Message msg);

    Message receiveMessage();
}
