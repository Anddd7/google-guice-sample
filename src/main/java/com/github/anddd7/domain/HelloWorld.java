package com.github.anddd7.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
    private static final Logger log = LoggerFactory.getLogger(HelloWorld.class);

    public HelloWorld() {
        log.debug("由容器创建 HelloWorld .");
    }

    public HelloWorld(String str) {
        log.debug("手动创建 HelloWorld :" + str);
    }
}
