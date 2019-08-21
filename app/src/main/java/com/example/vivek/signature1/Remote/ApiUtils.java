package com.example.vivek.signature1.Remote;

public class ApiUtils {
    public static final String Base_Url = "http://<OFFICEGEST_URL>/api/entities/customers";

    public static Userservice getUserservice(){
        return UserClient.getClint(Base_Url).create(Userservice.class);

    }
}
