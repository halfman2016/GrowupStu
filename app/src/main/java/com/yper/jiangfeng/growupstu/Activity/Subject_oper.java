package com.yper.jiangfeng.growupstu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yongchun.library.view.ImageSelectorActivity;
import com.yper.jiangfeng.growupstu.Adapter.SubjectListFragmentAdapter;
import com.yper.jiangfeng.growupstu.Module.Annouce;
import com.yper.jiangfeng.growupstu.Module.Photopic;
import com.yper.jiangfeng.growupstu.Module.Student;
import com.yper.jiangfeng.growupstu.Module.Subject;
import com.yper.jiangfeng.growupstu.R;
import com.yper.jiangfeng.growupstu.Utils.MDBTools;

import java.util.ArrayList;
import java.util.List;

public class Subject_oper extends AppCompatActivity {

    private Student student;
    private Subject subject;
    private List<Photopic> photopicList =new ArrayList<>();
    private ListView listView;
    private MDBTools mdb=new MDBTools();
    private Annouce ann;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Gson gson=new GsonBuilder().create();
        student=gson.fromJson(intent.getStringExtra("student"),Student.class);
        subject=gson.fromJson(intent.getStringExtra("subject"),Subject.class);

        setContentView(R.layout.activity_subject_oper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(subject.getSubjectName());
        setSupportActionBar(toolbar);


        listView= (ListView) findViewById(R.id.listphoto);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxSeletNum=1;

                ImageSelectorActivity.start(Subject_oper.this,maxSeletNum,ImageSelectorActivity.MODE_SINGLE,true,true,false);

            }
        });

        loaddata();
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

        switch (resultCode)
        {
            case 102:

                loaddata();

                break;
        }

    }

    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:

                        listView.setAdapter(new SubjectListFragmentAdapter(photopicList, getBaseContext(),student));

                    break;
            }
        }
    };
    public void  loaddata()
    {
        new Thread(){

            @Override
            public void run() {
                super.run();
                photopicList=mdb.getSubjectPhoto(subject);
                ann=mdb.getAnnouceLatest(subject);
                if(photopicList==null)
                {return;}
                else
                {
                    Message msg=new Message();
                    msg.what=1;
                    myhandler.sendMessage(msg);
                }

            }
        }.start();
    }


}
