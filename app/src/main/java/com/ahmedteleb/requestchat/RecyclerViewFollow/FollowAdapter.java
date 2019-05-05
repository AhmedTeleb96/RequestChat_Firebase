package com.ahmedteleb.requestchat.RecyclerViewFollow;

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

public class FollowAdapter extends RecyclerView.Adapter<FollowViewHolder>
{

    private List<FollowObject> usersList ;
    private Context context;

    public FollowAdapter(List<FollowObject> usersList, Context context)
    {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View LayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_followers_item,null);
        FollowViewHolder rcv =new FollowViewHolder(LayoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowViewHolder holder, int position) {

        holder.email.setText(usersList.get(position).getEmail());

        if(UserInformation.userFollowList.contains(usersList.get(holder.getLayoutPosition()).getUid()))
        {
            holder.follow.setText("following");

        }else {

            holder.follow.setText("follow");
        }

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if(!UserInformation.userFollowList.contains(usersList.get(holder.getLayoutPosition()).getUid())){
                    holder.follow.setText("following");
                    FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("following").child(usersList.get(holder.getLayoutPosition()).getUid()).setValue(true);
                }else{
                    holder.follow.setText("follow");
                    FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("following").child(usersList.get(holder.getLayoutPosition()).getUid()).removeValue();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
