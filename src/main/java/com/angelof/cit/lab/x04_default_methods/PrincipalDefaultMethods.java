package com.angelof.cit.lab.x04_default_methods;

import com.angelof.cit.lab.x00_data.Principal;
import com.angelof.cit.lab.x00_data.User;

import java.util.function.Consumer;

public class PrincipalDefaultMethods extends Principal {
    public static void main(String[] args) {

        Consumer<User> showName = user -> System.out.println("Name:   " + user.getName());
        Consumer<User> showPoints = user -> System.out.println("Points: " + user.getPoints() + "\n");

        USERS_LIST_ARRAY.forEach(showName.andThen(showPoints));

        USERS_LIST_ARRAY.removeIf(userInfo -> userInfo.getPoints() <= 75);

        System.out.println();

        USERS_LIST_ARRAY.forEach(user -> System.out.println(user));

    }
}
