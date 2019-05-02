package com.ahmedteleb.requestchat.RecycleViewStory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedteleb.requestchat.R;
import com.ahmedteleb.requestchat.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryViewHolder>
{

    private List<StoryObject> usersList ;
    private Context context;

    public StoryAdapter(List<StoryObject> usersList, Context context)
    {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View LayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.receycleview_story_item,null);
        StoryViewHolder rcv =new StoryViewHolder(LayoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryViewHolder holder, int position) {

        holder.email.setText(usersList.get(position).getEmail());
        holder.email.setTag(usersList.get(position).getUid());


    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
