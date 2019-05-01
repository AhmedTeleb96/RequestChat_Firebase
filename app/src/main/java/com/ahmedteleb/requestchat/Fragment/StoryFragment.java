package com.ahmedteleb.requestchat.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedteleb.requestchat.R;

public class StoryFragment extends Fragment {

    public static StoryFragment getInstance_()
    {
        StoryFragment storyFragment = new StoryFragment();
        return storyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story , container,false);
        return view;
    }
}
