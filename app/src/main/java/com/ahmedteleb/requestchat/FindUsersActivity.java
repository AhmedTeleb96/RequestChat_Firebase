package com.ahmedteleb.requestchat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ahmedteleb.requestchat.RecyclerViewFollow.FollowAdapter;
import com.ahmedteleb.requestchat.RecyclerViewFollow.FollowObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FindUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    EditText input;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_users);

        input= findViewById(R.id.findUsersInput);
        search= findViewById(R.id.search);

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        layoutManager= new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);
        adapter =new FollowAdapter(getDataSet(),getApplication());
        recyclerView.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                listenForData();
            }
        });
    }


    private void listenForData()
    {
        DatabaseReference usersDB = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = usersDB.orderByChild("email").startAt(input.getText().toString()).endAt(input.getText().toString()+"\uf8ff");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String email_ ="";
                String uid = dataSnapshot.getRef().getKey();
                if(dataSnapshot.child("email").getValue() != null)
                {
                    email_ =dataSnapshot.child("email").getValue().toString();
                }
                if(! email_.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()))
                {
                    FollowObject followObject =new FollowObject(email_,uid);
                    followObjectArrayList.add(followObject);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void clear()
    {
        int size = this.followObjectArrayList.size();
        this.followObjectArrayList.clear();
        adapter.notifyItemRangeChanged(0,size);
    }

    private ArrayList<FollowObject> followObjectArrayList =new ArrayList<>();
    private ArrayList<FollowObject> getDataSet()
    {
        listenForData();
        return followObjectArrayList;
    }
}
