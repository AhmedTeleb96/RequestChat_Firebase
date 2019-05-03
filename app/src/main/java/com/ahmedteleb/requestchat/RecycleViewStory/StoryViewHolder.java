package com.ahmedteleb.requestchat.RecycleViewStory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahmedteleb.requestchat.DisplayImageActivity;
import com.ahmedteleb.requestchat.R;

public class StoryViewHolder extends RecyclerView.ViewHolder implements  RecyclerView.OnClickListener
{
    public TextView email;

    public StoryViewHolder(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        email  =itemView.findViewById(R.id.email);

    }

    @Override
    public void onClick(View view)
    {
        Intent intent =new Intent(view.getContext(), DisplayImageActivity.class);
        Bundle bundle =new Bundle();
        bundle.putString("userId",email.getTag().toString());
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}
