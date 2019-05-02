package com.ahmedteleb.requestchat.RecycleViewStory;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmedteleb.requestchat.R;

public class StoryViewHolder extends RecyclerView.ViewHolder
{
    public TextView email;

    public StoryViewHolder(View itemView)
    {
        super(itemView);
        email  =itemView.findViewById(R.id.email);

    }

}
