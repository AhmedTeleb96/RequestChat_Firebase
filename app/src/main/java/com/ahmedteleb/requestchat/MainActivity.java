package com.ahmedteleb.requestchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        viewPager = findViewById(R.id.viewpager);
        fragmentPagerAdapter = new myViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(1);

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
