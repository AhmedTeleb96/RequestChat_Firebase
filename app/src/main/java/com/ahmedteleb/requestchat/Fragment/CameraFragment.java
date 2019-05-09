package com.ahmedteleb.requestchat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ahmedteleb.requestchat.FindUsersActivity;
import com.ahmedteleb.requestchat.R;
import com.ahmedteleb.requestchat.ShowCaptureActivity;
import com.ahmedteleb.requestchat.LoginRegistration.SplashScreenActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CameraFragment extends Fragment implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    final int CAMERA_REQUEST_CODE =1;
    Camera.PictureCallback jpegCallback;


    public static CameraFragment getInstance_()
    {
        CameraFragment cameraFragment = new CameraFragment();
        return cameraFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera , container,false);

        surfaceView = view.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {

          // requestPermissions(getActivity(),new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
           requestPermissions(new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

        }else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        Button logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        Button capture_btn = view.findViewById(R.id.capture);
        capture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        Button findUsers_btn = view.findViewById(R.id.findUsers);
        findUsers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findUsers();
            }
        });

        jpegCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

               Bitmap rotateBitmap = rotateBitmap(bitmap);

                String fileLocation = SaveImageToStorage(rotateBitmap);
                if(fileLocation!= null){
                    Intent intent = new Intent(getActivity(), ShowCaptureActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        };

        return view;
    }

    public String SaveImageToStorage(Bitmap bitmap){
        String fileName = "imageToSend";
        try{
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }catch(Exception e){
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    private Bitmap rotateBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix=new Matrix();
        matrix.setRotate(90);
        return Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);
    }

    private void captureImage()
    {
        camera.takePicture(null,null,jpegCallback);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder2) {
        camera = Camera.open();
        Camera.Parameters param = camera.getParameters();
        camera.setDisplayOrientation(90);
        param.setPreviewFrameRate(30);
        param.setFlashMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        Camera.Size bestSize=null;
        List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
        bestSize=sizeList.get(0);
        for (int i=1 ; i<sizeList.size() ; i++)
        {
            if((sizeList.get(i).width * sizeList.get(i).height) > (bestSize.width * bestSize.height))
            {
                bestSize = sizeList.get(i);
            }
        }
        param.setPictureSize(bestSize.width,bestSize.height);


        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(surfaceHolder2);
        } catch (IOException e) {

        }
        camera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case CAMERA_REQUEST_CODE :
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    surfaceHolder.addCallback(this);
                    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                }else {
                    Toast.makeText(getContext(),"Please provide the permission",Toast.LENGTH_SHORT).show();
                }

                break;
            }
        }
    }

    private void findUsers()
    {

        Intent intent = new Intent(getContext(), FindUsersActivity.class);
        startActivity(intent);
        return;
    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return;

    }
}
