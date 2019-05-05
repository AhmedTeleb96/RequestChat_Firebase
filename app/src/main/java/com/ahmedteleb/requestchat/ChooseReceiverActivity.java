package com.ahmedteleb.requestchat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.ahmedteleb.requestchat.RecyclerViewFollow.FollowObject;
import com.ahmedteleb.requestchat.RecyclerViewReceiver.ReceiverAdapter;
import com.ahmedteleb.requestchat.RecyclerViewReceiver.ReceiverObject;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseReceiverActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    String Uid;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_receiver);

        try {
            bitmap = BitmapFactory.decodeStream(getApplication().openFileInput("imageToSend"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            finish();
            return;
        }

        Uid = FirebaseAuth.getInstance().getUid();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        layoutManager= new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);
        adapter =new ReceiverAdapter(getDataSet(),getApplication());
        recyclerView.setAdapter(adapter);

        FloatingActionButton floating_Abtn = findViewById(R.id.floating_Abtn);
        floating_Abtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveToStories();
            }
        });
    }

    private ArrayList<ReceiverObject> receiverObjectArrayList =new ArrayList<>();
    private ArrayList<ReceiverObject> getDataSet()
    {
        listenForData();
        return receiverObjectArrayList;
    }

    private void listenForData()
    {
        for (int i=0 ; i < UserInformation.userFollowList.size() ;i++)
        {
            DatabaseReference usersDB = FirebaseDatabase.getInstance().getReference().child("users").child(UserInformation.userFollowList.get(i));

            usersDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String email_ = "";
                    String uid = dataSnapshot.getRef().getKey();
                    if (dataSnapshot.child("email").getValue() != null) {
                        email_ = dataSnapshot.child("email").getValue().toString();
                    }

                    ReceiverObject receiverObject = new ReceiverObject(email_, uid, false);
                    if (!receiverObjectArrayList.contains(receiverObject)) {
                        receiverObjectArrayList.add(receiverObject);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

        }

    }

    private void saveToStories()
    {
        final DatabaseReference userStoryDB = FirebaseDatabase.getInstance().getReference().child("users").child(Uid).child("story");
        final String key = userStoryDB.push().getKey();
        final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("captures").child(key);

        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);
        byte[] imageToUpload = baos.toByteArray();
        UploadTask uploadTask= filepath.putBytes(imageToUpload);

        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                Uri imageUrl = task.getResult();

                long currentTimeStamp = System.currentTimeMillis();
                long endTimeStamp = System.currentTimeMillis() + (24*60*60*1000);

                CheckBox storyCB =findViewById(R.id.story_CB);
                if (storyCB.isChecked()) {
                    Map<String, Object> mapToUpload = new HashMap<>();
                    mapToUpload.put("imageurl", imageUrl.toString());
                    mapToUpload.put("timeStampBegin", currentTimeStamp);
                    mapToUpload.put("timeStampEnd", endTimeStamp);
                    userStoryDB.child(key).setValue(mapToUpload);
                }

                for (int i=0 ; i < receiverObjectArrayList.size() ;i++)
                {
                    DatabaseReference userDB = FirebaseDatabase.getInstance().getReference().child("users").child(receiverObjectArrayList.get(i).getUid()).child("received").child(Uid);

                    Map<String, Object> mapToUpload = new HashMap<>();
                    mapToUpload.put("imageurl", imageUrl.toString());
                    mapToUpload.put("timeStampBegin", currentTimeStamp);
                    mapToUpload.put("timeStampEnd", endTimeStamp);
                    userDB.child(key).setValue(mapToUpload);
                }

                     Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);

                    finish();
                    return;
            }
        });



        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                finish();
                return;
            }
        });

    }
}
