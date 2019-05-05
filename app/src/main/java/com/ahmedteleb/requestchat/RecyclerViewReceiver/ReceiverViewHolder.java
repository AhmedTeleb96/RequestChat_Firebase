package com.ahmedteleb.requestchat.RecyclerViewReceiver;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ahmedteleb.requestchat.R;

public class ReceiverViewHolder extends RecyclerView.ViewHolder
{
    public TextView email;
    public CheckBox receiver_CB;

    public ReceiverViewHolder(View itemView)
    {
        super(itemView);
        email  =itemView.findViewById(R.id.email);
        receiver_CB =itemView.findViewById(R.id.receive_CB);

    }

}
