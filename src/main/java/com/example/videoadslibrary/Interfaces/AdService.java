package com.example.videoadslibrary.Interfaces;

import com.example.videoadslibrary.Ad;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdService {
    @GET("api/ads/{id}")
    Call<Ad> getAdById(@Path("id") String id);

    @POST("api/ads/{id}/view")
    Call<Void> incrementView(@Path("id") String id);

    @POST("api/ads/{id}/click")
    Call<Void> incrementClick(@Path("id") String id);
}
