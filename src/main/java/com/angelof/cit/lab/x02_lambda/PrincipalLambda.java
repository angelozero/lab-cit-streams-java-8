package com.angelof.cit.lab.x02_lambda;

import com.angelof.cit.lab.x00_data.Principal;
import com.angelof.cit.lab.x00_data.User;

import java.util.function.Consumer;

public class PrincipalLambda extends Principal {
    public static void main(String[] args) {

        // 01
        Consumer<User> viewUsers =
                (User u) -> System.out.println(u.getName());

        USERS_LIST.forEach(viewUsers);

        // 02
        USERS_LIST.forEach(user -> System.out.println(user.getPoints()));

        // 03
        Runnable r1 = new Runnable(){
            public void run(){
                for (int i = 0; i <= 1000; i++) {
                    System.out.println(i);
                } }
        };
        new Thread(r1).start();

        // 04
        Runnable r2 = () -> {
            for (int i = 0; i <= 1000; i++) {
                System.out.println(i);
            }
        };
        new Thread(r2).start();

        // 05
        new Thread(() -> {
            for (int i = 0; i <= 1000; i++) {
                System.out.println(i);
            }
        }).start();
    }
}
