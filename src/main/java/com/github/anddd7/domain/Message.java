package com.github.anddd7.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
public class Message {
    String code;
    String msg;

    @Override
    public String toString() {
        return code + " : " + msg;
    }
}
