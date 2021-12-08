package com.angelof.cit.lab.x03_functional_interface;

@FunctionalInterface
public interface UserNameAndPoints<T> {
    String getInfo(T t);

//    String test();
}
