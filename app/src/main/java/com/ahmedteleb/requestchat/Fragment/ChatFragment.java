package com.ahmedteleb.requestchat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedteleb.requestchat.R;

public class ChatFragment extends Fragment
{
    public static ChatFragment getInstance_()
    {
        ChatFragment chatFragment = new ChatFragment();
        return chatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat , container,false);
        return view;
    }
}
