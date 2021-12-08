package com.angelof.cit.lab.x01_consumer;

import com.angelof.cit.lab.x00_data.Principal;
import com.angelof.cit.lab.x00_data.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PrincipalConsumer extends Principal {
    public static void main(String[] args) {

        List<User> usersList = Arrays.asList(ANGELO, EZEQUIEL, ELIO);

        UserConsumer userConsumer = new UserConsumer();

        // 01
        usersList.forEach(userConsumer);
        System.out.println();

        // 02
        usersList.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println("Points: ..." + user.getPoints());
            }
        });
        System.out.println();

        // 03
        usersList.forEach(user -> System.out.println("Moderator: ..." + user.isModerator()));
        System.out.println();

    }
}
