package com.pierfrancescosoffritti.androidyoutubeplayer.core.sampleapp.examples.viewPagerExample;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.pierfrancescosoffritti.aytplayersample.R;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class ViewPagerActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener, VideoListener {
    private static Pair<String,String>[] videos = new Pair[]{new Pair<>("0Xcfw3bQC28", "Người máy"), new Pair<>("ic_QQHY836Y", "Thời trang"),
            new Pair<>("DJ1lH4wFXeg", "Trượt tuyết"), new Pair<>("66gxb2QxlGk", "Quảng cáo adidas")};
    private StoriesProgressView storie_progress_bar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private VideoListener videoListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_example);

        viewPager = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                storie_progress_bar.startStories(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initProgressBar();
    }

    private void initProgressBar() {
        storie_progress_bar = findViewById(R.id.storie_progress_bar);
        long[] storiesDuration = {15000L, 16000L, 42000L, 45000L};
        storie_progress_bar.setStoriesCountWithDurations(storiesDuration);
        storie_progress_bar.setStoriesListener(this);

    }

    @Override
    public void onNext() {
        int currentItem = viewPager.getCurrentItem();
        int count = viewPagerAdapter.getCount();
        if (currentItem < count - 1) {
            viewPager.setCurrentItem(currentItem + 1);
        }
    }

    @Override
    public void onPrev() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void ready() {
        storie_progress_bar.startStories();
    }
    @Override
    public void pause() {
        storie_progress_bar.pause();
    }
    @Override
    public void resume() {
        storie_progress_bar.resume();
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ViewPagerFragment.newInstance(videos[position].first, videos[position].second);
        }

        @Override
        public int getCount() {
            return videos.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}
interface VideoListener {
    void ready();
    void pause();
    void resume();
}
