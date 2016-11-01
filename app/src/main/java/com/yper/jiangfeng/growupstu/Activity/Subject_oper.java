package com.yper.jiangfeng.growupstu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yongchun.library.view.ImageSelectorActivity;
import com.yper.jiangfeng.growupstu.Module.Student;
import com.yper.jiangfeng.growupstu.Module.Subject;
import com.yper.jiangfeng.growupstu.R;

import java.util.ArrayList;

public class Subject_oper extends AppCompatActivity {

    private Student student;
    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Gson gson=new GsonBuilder().create();
        student=gson.fromJson(intent.getStringExtra("student"),Student.class);
        subject=gson.fromJson(intent.getStringExtra("subject"),Subject.class);

        setContentView(R.layout.activity_subject_oper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxSeletNum=1;

                ImageSelectorActivity.start(Subject_oper.this,maxSeletNum,ImageSelectorActivity.MODE_SINGLE,true,true,false);

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent;
        if(resultCode==0) return;

        switch (requestCode)
        {
            case ImageSelectorActivity.REQUEST_IMAGE:
                intent = new Intent(Subject_oper.this, TakePhoto.class);
                ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                String filename= (images.get(0));
                intent.putExtra("filename",filename);

                Gson gson=new GsonBuilder().create();
                intent.putExtra("student",gson.toJson(student));
                intent.putExtra("subject",gson.toJson(subject));
                startActivityForResult(intent, 103);
                break;
        }

    }

}
