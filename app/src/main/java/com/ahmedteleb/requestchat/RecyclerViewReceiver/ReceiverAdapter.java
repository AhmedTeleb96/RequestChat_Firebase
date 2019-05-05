package com.ahmedteleb.requestchat.RecyclerViewReceiver;

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

public class ReceiverAdapter extends RecyclerView.Adapter<ReceiverViewHolder>
{

    private List<ReceiverObject> usersList ;
    private Context context;

    public ReceiverAdapter(List<ReceiverObject> usersList, Context context)
    {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReceiverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View LayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_receiver_item,null);
        ReceiverViewHolder rcv =new ReceiverViewHolder(LayoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReceiverViewHolder holder, int position) {

        holder.email.setText(usersList.get(position).getEmail());
        holder.receiver_CB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean receiveState = !usersList.get(holder.getLayoutPosition()).isReceive();
                usersList.get(holder.getLayoutPosition()).setReceive(receiveState);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.usersList.size();
    }
}
