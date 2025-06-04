package com.example.videoadslibrary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.videoadslibrary.Interfaces.AdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdPlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_player);

        String videoUrl = getIntent().getStringExtra("video_url");
        String adTitle = getIntent().getStringExtra("ad_title");
        String adId = getIntent().getStringExtra("ad_id");
        setTitle(adTitle);

        playerView = findViewById(R.id.player_view);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

        View overlay = findViewById(R.id.overlay_layout);
        overlay.setOnClickListener(v -> {
            VideoAdsSdk.getAdService().incrementClick(adId).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) { }

                @Override
                public void onFailure(Call<Void> call, Throwable t) { }
            });

            String advertiserUrl = "https://www.google.com";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(advertiserUrl));
            startActivity(browserIntent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}