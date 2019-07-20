package com.bytedance.camera.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Arrays;

public class RecordVideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private static final int REQUEST_EXTERNAL_CAMERA = 101;

    private  static final int REQUEST_PERMISSION = 123;

    private String[] mPermissionsArrays = new String[]{
            Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};

    private int state = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);

        videoView = findViewById(R.id.img);
        findViewById(R.id.btn_picture).setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(RecordVideoActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RecordVideoActivity.this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                //todo 在这里申请相机、存储的权限
                requestPermissions(mPermissionsArrays,123);
            } else {
                Intent takevideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takevideoIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(takevideoIntent,REQUEST_VIDEO_CAPTURE);
                }
            }
        });
        videoView.setOnClickListener(v -> {
            if(state == 0)
            {
                videoView.stopPlayback();
                this.state = 1;
            }
                else if(state == 1){
                    videoView.start();
                    state = 0;
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_CAMERA: {
                if (requestCode == REQUEST_PERMISSION) {
                    Toast.makeText(this, "已经授权" + Arrays.toString(permissions), Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
