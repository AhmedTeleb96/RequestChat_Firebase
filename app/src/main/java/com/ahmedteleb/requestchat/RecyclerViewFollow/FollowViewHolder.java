package com.ahmedteleb.requestchat.RecyclerViewFollow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmedteleb.requestchat.R;

public class FollowViewHolder extends RecyclerView.ViewHolder
{
    public TextView email;
    public Button follow;

    public FollowViewHolder(View itemView)
    {
        super(itemView);
        email  =itemView.findViewById(R.id.email);
        follow =itemView.findViewById(R.id.follow_btn);

    }

}
