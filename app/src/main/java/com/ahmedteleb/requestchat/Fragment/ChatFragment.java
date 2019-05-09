package com.ahmedteleb.requestchat.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ahmedteleb.requestchat.R;
import com.ahmedteleb.requestchat.RecyclerViewStory.StoryAdapter;
import com.ahmedteleb.requestchat.RecyclerViewStory.StoryObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment
{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String uid;

    public static ChatFragment getInstance_()
    {
        ChatFragment chatFragment = new ChatFragment();
        return chatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat , container,false);


        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StoryAdapter(getDataSet(),getContext());
        recyclerView.setAdapter(adapter);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Button resfresh = view.findViewById(R.id.resfresh);
        resfresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                listenForData();
            }
        });

        return view;
    }

    private void clear()
    {
        int size = this.StoryObjectArrayList.size();
        this.StoryObjectArrayList.clear();
        adapter.notifyItemRangeChanged(0,size);
    }

    private ArrayList<StoryObject> StoryObjectArrayList =new ArrayList<>();
    private ArrayList<StoryObject> getDataSet()
    {
        listenForData();
        return StoryObjectArrayList;
    }

    private void listenForData(){
        if(uid != null) {
            DatabaseReference receivedDb = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("received");
            receivedDb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            getUserInfo(snapshot.getKey());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else {
            Log.i("usernull","user id is null");
        }
    }

    private void getUserInfo(String chatUid) {
        DatabaseReference chatUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(chatUid);
        chatUserDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String email = dataSnapshot.child("email").getValue().toString();
                    String uid = dataSnapshot.getRef().getKey();

                    StoryObject storyObject = new StoryObject(email, uid, "chat");
                    if(!StoryObjectArrayList.contains(storyObject))
                    {
                        StoryObjectArrayList.add(storyObject);
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
