package com.angelof.cit.lab.x03_functional_interface;

import com.angelof.cit.lab.x00_data.Data;
import com.angelof.cit.lab.x00_data.User;

public class DataInfoInterfaceFuncional extends Data {
    public static void main(String[] args) {

        // 01
        UserNameAndPoints<User> userInfo1 = new UserNameAndPoints<User>() {
            @Override
            public String getInfo(User user) {
                return "User name: " + user.getName() + " --- User points: " + user.getPoints();
            }
        };

        USERS_LIST.forEach(user -> {
            if (user.isModerator()) {
                System.out.println(userInfo1.getInfo(user));
            }
        });

        System.out.println();

        // 02
        UserNameAndPoints<User> userInfo2 = user ->
                "User name: " + user.getName() + " --- User points: " + user.getPoints();

        USERS_LIST.forEach(user -> {
            if (!user.isModerator()) {
                System.out.println(userInfo2.getInfo(user));
            }
        });

    }
}
