package com.github.anddd7.domain;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class HelloWorld {

    public HelloWorld() {
        log.debug("由容器创建 HelloWorld .");
    }

    public HelloWorld(String str) {
        log.debug("手动创建 HelloWorld :" + str);
    }
}
