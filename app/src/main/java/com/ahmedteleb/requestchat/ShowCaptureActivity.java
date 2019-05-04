package com.ahmedteleb.requestchat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ShowCaptureActivity extends AppCompatActivity {

    String Uid;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_capture);


        try {
            bitmap = BitmapFactory.decodeStream(getApplication().openFileInput("imageToSend"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            finish();
            return;
        }

        ImageView imageView = findViewById(R.id.capturedImage);
        imageView.setImageBitmap(bitmap);


        Uid = FirebaseAuth.getInstance().getUid();
        Button saveStory_btn = findViewById(R.id.saveStory);
        saveStory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveToStories();
            }
        });
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

        Task<Uri>  uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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

                Map<String,Object> mapToUpload = new HashMap<>();
                mapToUpload.put("imageurl",imageUrl.toString());
                mapToUpload.put("timeStampBegin",currentTimeStamp);
                mapToUpload.put("timeStampEnd",endTimeStamp);

                userStoryDB.child(key).setValue(mapToUpload);


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
