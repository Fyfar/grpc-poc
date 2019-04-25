package me.grpc.poc;

import java.util.UUID;

public class UserServiceStub {

    public static String getUserToken() {
        return UUID.randomUUID().toString();
    }

}
