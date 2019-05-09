package com.ahmedteleb.requestchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ahmedteleb.requestchat.Fragment.CameraFragment;
import com.ahmedteleb.requestchat.Fragment.ChatFragment;
import com.ahmedteleb.requestchat.Fragment.StoryFragment;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserInformation userInformationEventListener =new UserInformation();
        userInformationEventListener.startFetch();

        viewPager = findViewById(R.id.viewpager);
        fragmentPagerAdapter = new myViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(1);

        final View bachground = findViewById(R.id.bg_color);
        final int bg_blue = ContextCompat.getColor(this,R.color.bg_blue);
        final int bg_foshia = ContextCompat.getColor(this,R.color.bg_foshia);
        final int bg_green = ContextCompat.getColor(this,R.color.bg_light_green);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                switch (position)
                {
                    case 0:
                        bachground.setBackgroundColor(bg_green);
                        bachground.setAlpha(1-positionOffset);

                    case 2:
                        bachground.setBackgroundColor(bg_blue);
                        bachground.setAlpha(positionOffset);

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public  static  class  myViewPagerAdapter extends FragmentPagerAdapter
    {

        public myViewPagerAdapter(FragmentManager fm) { super(fm); }

        @Override
        public Fragment getItem(int position) {
            switch (position){

                case 0:
                        // return fragment chat
                    return ChatFragment.getInstance_();

                case 1:
                        // return fragment camera
                    return CameraFragment.getInstance_();

                case 2:
                        // return fragment story
                    return StoryFragment.getInstance_();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
