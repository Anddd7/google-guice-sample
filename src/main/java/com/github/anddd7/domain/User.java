package com.github.anddd7.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    Integer id;
    String name;

    @Override
    public String toString() {
        return id + " : " + name;
    }
}
