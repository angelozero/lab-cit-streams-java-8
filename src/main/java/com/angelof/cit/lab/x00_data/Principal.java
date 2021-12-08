package com.angelof.cit.lab.x00_data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Principal {

    public static final User ANGELO = User.builder()
            .name("Angelo")
            .moderator(new Random().nextBoolean())
            .points(new Random().nextInt(150))
            .build();

    public static final User EZEQUIEL = User.builder()
            .name("Ezequiel")
            .moderator(new Random().nextBoolean())
            .points(new Random().nextInt(150))
            .build();

    public static final User ELIO = User.builder()
            .name("Elio")
            .moderator(new Random().nextBoolean())
            .points(new Random().nextInt(150))
            .build();

    public static final List<User> USERS_LIST = Arrays.asList(ANGELO, EZEQUIEL, ELIO);
    public static final List<User> USERS_LIST_ARRAY = new java.util.ArrayList<User>(java.util.Arrays.asList(ANGELO,EZEQUIEL,ELIO));
}
