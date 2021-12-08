package com.angelof.cit.lab.x01_consumer;

import com.angelof.cit.lab.x00_data.User;

import java.util.function.Consumer;

public class UserConsumer implements Consumer<User> {
    @Override
    public void accept(User user) {
        System.out.println("Name: ... " + user.getName());
    }
}
