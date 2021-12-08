package com.angelof.cit.lab.x04_default_methods;

import com.angelof.cit.lab.x00_data.Data;
import com.angelof.cit.lab.x00_data.User;

import java.util.Collections;
import java.util.function.Consumer;

public class DataInfoDefaultMethods extends Data {
    public static void main(String[] args) {

        Consumer<User> showName = user -> System.out.println("Name:   " + user.getName());
        Consumer<User> showPoints = user -> System.out.println("Points: " + user.getPoints() + "\n");

        USERS_LIST_ARRAY.forEach(showName.andThen(showPoints));

        // Removing
        USERS_LIST_ARRAY.removeIf(userInfo -> userInfo.getPoints() <= 15);

        System.out.println();


        // Sorting
        Collections.sort(USERS_LIST_ARRAY, (user1, user2) -> user1.getName().compareTo(user2.getName()));

        //
        USERS_LIST_ARRAY.forEach(user -> System.out.println(user));

    }
}
