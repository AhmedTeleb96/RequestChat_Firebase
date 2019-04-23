package com.ahmedteleb.requestchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity
{
    public  static boolean started = false;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null)
        {
            Intent main_intent =new Intent(getApplication(),MainActivity.class);
            main_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main_intent);
            finish();
            return;

        }else
        {
            Intent choose_intent =new Intent(getApplication(),ChooseLoginRegistrationActivity.class);
            choose_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(choose_intent);
            finish();
            return;
        }
    }
}
