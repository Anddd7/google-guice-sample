package com.github.anddd7.service.impl;

import com.github.anddd7.domain.User;
import com.github.anddd7.service.UserService;

import java.util.Arrays;
import java.util.List;

public class EmployeeServiceImpl implements UserService {
    @Override
    public List<User> getUser() {
        User user1 = new User(1001, "员工1");
        User user2 = new User(1002, "员工2");
        return Arrays.asList(new User[]{user1, user2});
    }
}
