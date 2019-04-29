package com.ahmedteleb.requestchat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowCaptureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_capture);

        Bundle extras = getIntent().getExtras();
        byte[] bytes = extras.getByteArray("captured");

        if(bytes!=null){
            ImageView imageView = findViewById(R.id.capturedImage);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            Bitmap rotateBitmap = rotateBitmap(bitmap);

            imageView.setImageBitmap(rotateBitmap);
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix=new Matrix();
        matrix.setRotate(90);
        return Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);
    }
}
