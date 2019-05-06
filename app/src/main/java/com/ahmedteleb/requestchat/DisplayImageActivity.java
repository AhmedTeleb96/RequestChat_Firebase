package com.ahmedteleb.requestchat;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayImageActivity extends AppCompatActivity {

    String userId , chatOrStory , currentUid;
    private ImageView imageView;
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        currentUid = FirebaseAuth.getInstance().getUid();

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        chatOrStory = bundle.getString("chatOrStory");

        imageView = findViewById(R.id.image);

        switch(chatOrStory){
            case "chat":
                listenForChat();
                break;
            case "story":
                listenForStory();
                break;
        }

    }

    private void listenForChat()
    {
        final DatabaseReference chatDB = FirebaseDatabase.getInstance().getReference().child("users").child(currentUid).child("received").child(userId);
        chatDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imageUrl = "";
                for(DataSnapshot chatSnapshot : dataSnapshot.getChildren()){

                    if(chatSnapshot.child("imageUrl").getValue() != null){
                        imageUrl = chatSnapshot.child("imageUrl").getValue().toString();
                    }
                    imageUrlArrayList.add(imageUrl);
                    if (!started){
                        started = true;
                        InitializeDisplay();
                    }
                    chatDB.child(chatSnapshot.getKey()).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private ArrayList<String> imageUrlArrayList = new ArrayList<>();
    private void listenForStory()
    {

            DatabaseReference listFollowingDB = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            listFollowingDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String imageUrl = "";
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

                        if (storySnapShot.child("imageurl").getValue() != null)
                        {
                            imageUrl = storySnapShot.child("imageurl").getValue().toString();
                        }

                        long timestampCurrent = System.currentTimeMillis();
                        if (timestampCurrent >= timestampBegin && timestampCurrent <= timestampEnd)
                        {
                            imageUrlArrayList.add(imageUrl);

                            if (!started)
                            {
                                started =true;
                                InitializeDisplay();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        private int imageIterator = 0;
    private void InitializeDisplay()
    {
        Glide.with(getApplication()).load(imageUrlArrayList.get(imageIterator)).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });

        final Handler handler =new Handler();
        final int delay = 5000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                changeImage();
                handler.postDelayed(this,delay);
            }
        },delay);
    }

    private void changeImage()
    {
        if (imageIterator == imageUrlArrayList.size()-1 )
        {
            finish();
            return;
        }

        imageIterator++;
        Glide.with(getApplication()).load(imageUrlArrayList.get(imageIterator)).into(imageView);

    }

}
