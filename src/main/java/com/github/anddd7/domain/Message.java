package com.github.anddd7.domain;


public class Message {
    String code;
    String msg;

    public Message(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return code + " : " + msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
