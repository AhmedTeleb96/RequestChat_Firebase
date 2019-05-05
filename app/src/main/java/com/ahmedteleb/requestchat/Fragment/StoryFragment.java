package com.ahmedteleb.requestchat.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ahmedteleb.requestchat.R;
import com.ahmedteleb.requestchat.RecyclerViewStory.StoryAdapter;
import com.ahmedteleb.requestchat.RecyclerViewStory.StoryObject;
import com.ahmedteleb.requestchat.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoryFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public static StoryFragment getInstance_()
    {
        StoryFragment storyFragment = new StoryFragment();
        return storyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story , container,false);

        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StoryAdapter(getDataSet(),getContext());
        recyclerView.setAdapter(adapter);

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

    private void listenForData()
    {
        for (int i=0 ; i< UserInformation.userFollowList.size();i++)
        {
            DatabaseReference listFollowingDB = FirebaseDatabase.getInstance().getReference().child("users").child(UserInformation.userFollowList.get(i));
            listFollowingDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String Email = dataSnapshot.child("email").getValue().toString();
                    String Uid = dataSnapshot.getRef().getKey();
                    long timestampBegin = 0;
                    long timestampEnd   = 0;

                    for (DataSnapshot storySnapShot : dataSnapshot.child("story").getChildren())
                    {
                        if (storySnapShot.child("timeStampBegin").getValue() != null)
                        {
                            timestampBegin = Long.parseLong(storySnapShot.child("timeStampBegin").getValue().toString());
                        }

                        if (storySnapShot.child("timeStampEnd").getValue() != null)
                        {
                            timestampEnd = Long.parseLong(storySnapShot.child("timeStampEnd").getValue().toString());
                        }

                        long timestampCurrent = System.currentTimeMillis();
                        if (timestampCurrent >= timestampBegin && timestampCurrent <= timestampEnd)
                        {
                            StoryObject storyObject = new StoryObject(Email,Uid, "story");

                            if(!StoryObjectArrayList.contains(storyObject)) {
                                StoryObjectArrayList.add(storyObject);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
