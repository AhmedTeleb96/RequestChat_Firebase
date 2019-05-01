package com.ahmedteleb.requestchat.RecycleViewFollow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmedteleb.requestchat.R;

public class RCViewHolder extends RecyclerView.ViewHolder
{
    public TextView email;
    public Button follow;

    public RCViewHolder(View itemView)
    {
        super(itemView);
        email=itemView.findViewById(R.id.email);
        email=itemView.findViewById(R.id.follow_btn);

    }

}
