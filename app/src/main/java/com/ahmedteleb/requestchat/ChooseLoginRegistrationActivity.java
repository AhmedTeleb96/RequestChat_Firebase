package com.ahmedteleb.requestchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLoginRegistrationActivity extends AppCompatActivity {

    Button login_btn;
    Button registration_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_registration);

        login_btn = findViewById(R.id.cLogin_btn);
        registration_btn = findViewById(R.id.cRegistration_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent =new Intent(getApplication(),LoginActivity.class);
                startActivity(login_intent);
                return;
            }
        });

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent register_intent =new Intent(getApplication(),RegistrationActivity.class);
                startActivity(register_intent);
                return;
            }
        });

    }
}
