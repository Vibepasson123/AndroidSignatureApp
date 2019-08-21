package com.example.vivek.signature1.Remote;

import com.example.vivek.signature1.model.ResObj;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface Userservice {

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("/")
    Call<ResObj>login(@Header("Authorization") String token);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("/signature/{id}")
    Call<ResObj>postSign(@Header("Authorization") String token, @Path("id") String id);
}
