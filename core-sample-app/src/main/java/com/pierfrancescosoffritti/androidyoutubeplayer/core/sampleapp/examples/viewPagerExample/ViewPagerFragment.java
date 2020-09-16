package com.pierfrancescosoffritti.androidyoutubeplayer.core.sampleapp.examples.viewPagerExample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.sampleapp.utils.VideoIdsProvider;
import com.pierfrancescosoffritti.aytplayersample.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ViewPagerFragment extends Fragment {

    private YouTubePlayer initializedYouTubePlayer;

    public static ViewPagerFragment newInstance(String videoId, String videoTitle) {
        ViewPagerFragment myFragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putString("videoId", videoId);
        args.putString("videoTitle", videoTitle);
        myFragment.setArguments(args);
        return myFragment;
    }

    public String getVideoId() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getString("videoId");
        }
        return "";
    }

    public String getVideoTitle() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getString("videoTitle");
        }
        return "";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_view_pager, container, false);

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setText(getVideoTitle());

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(getVideoId(), 0);
                initializedYouTubePlayer = youTubePlayer;
            }
        });


        return view;
    }

    // pause when fragment goes offscreen
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (initializedYouTubePlayer != null) {
            if (visible) {
                initializedYouTubePlayer.play();
            } else {
                initializedYouTubePlayer.pause();
            }
        }
    }
}