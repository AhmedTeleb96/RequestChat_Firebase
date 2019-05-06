package com.ahmedteleb.requestchat.RecyclerViewStory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahmedteleb.requestchat.DisplayImageActivity;
import com.ahmedteleb.requestchat.R;

public class StoryViewHolder extends RecyclerView.ViewHolder implements  RecyclerView.OnClickListener
{
    public TextView email;
    public LinearLayout layout;
    public StoryViewHolder(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        email  =itemView.findViewById(R.id.email);
        layout  =itemView.findViewById(R.id.layout_item);

    }

    @Override
    public void onClick(View view)
    {
        Intent intent =new Intent(view.getContext(), DisplayImageActivity.class);
        Bundle bundle =new Bundle();
        bundle.putString("userId",email.getTag().toString());
        bundle.putString("chatOrStory",email.getTag().toString());

        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}
