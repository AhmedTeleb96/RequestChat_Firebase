package com.ahmedteleb.requestchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.ahmedteleb.requestchat.RecycleViewFollow.RCadapter;
import com.ahmedteleb.requestchat.RecycleViewFollow.UsersObject;

import java.util.ArrayList;
import java.util.List;

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
        adapter =new RCadapter(getDataSet(),getApplication());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<UsersObject> usersObjectArrayList =new ArrayList<>();
    private ArrayList<UsersObject> getDataSet()
    {

        return usersObjectArrayList;
    }
}
