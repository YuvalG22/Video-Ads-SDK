package com.example.videoadslibrary;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.videoadslibrary.Interfaces.AdService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoAdsSdk {
    private static AdService adService;

    public static void init(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://video-ads-api.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        adService = retrofit.create(AdService.class);
    }

    public static void showAd(Context context, String adId) {
        adService.getAdById(adId).enqueue(new Callback<Ad>() {
            @Override
            public void onResponse(Call<Ad> call, Response<Ad> response) {
                if (response.isSuccessful()) {
                    Ad ad = response.body();

                    Intent intent = new Intent(context, AdPlayerActivity.class);
                    intent.putExtra("ad_title", ad.title);
                    intent.putExtra("video_url", ad.videoUrl);
                    intent.putExtra("ad_id", ad._id);
                    context.startActivity(intent);

                    adService.incrementView(adId).enqueue(new Callback<Void>() {
                        @Override public void onResponse(Call<Void> call, Response<Void> response) {}
                        @Override public void onFailure(Call<Void> call, Throwable t) {}
                    });
                } else {
                    Toast.makeText(context, "Failed to load ad", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ad> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static AdService getAdService(){
        return adService;
    }
}
