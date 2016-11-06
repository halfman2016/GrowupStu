package com.yper.jiangfeng.growupstu.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yongchun.library.view.ImageSelectorActivity;
import com.yper.jiangfeng.growupstu.Adapter.MainSubjectListAdapter;
import com.yper.jiangfeng.growupstu.Module.Student;
import com.yper.jiangfeng.growupstu.Module.Subject;
import com.yper.jiangfeng.growupstu.R;
import com.yper.jiangfeng.growupstu.Utils.MDBTools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Student student;
    private ListView listView;
    List<Subject> subjectList=new ArrayList<>();

private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent=getIntent();
        Gson gson=new GsonBuilder().create();
        student=gson.fromJson(intent.getStringExtra("student"),Student.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(student.getName());
        setSupportActionBar(toolbar);


        listView= (ListView) findViewById(R.id.listview);



        loaddata();
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.mainactivity,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.takephoto:



            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent;
        if(resultCode==0) return;

        switch (requestCode)
        {
            case ImageSelectorActivity.REQUEST_IMAGE:
                intent = new Intent(MainActivity.this, TakePhoto.class);
                ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                String filename= (images.get(0));
                intent.putExtra("filename",filename);
                Gson gson=new GsonBuilder().create();
                intent.putExtra("student",gson.toJson(student));
                startActivityForResult(intent, 103);
                break;
        }

    }

    public Handler mhandler=new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what)
            {
                case 1:

                    listView.setAdapter(new MainSubjectListAdapter(subjectList, getBaseContext()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           Subject subject= (Subject) parent.getAdapter().getItem(position);
                            Intent intent =new Intent(MainActivity.this,Subject_oper.class);
                            Gson gson=new GsonBuilder().create();
                            intent.putExtra("student",gson.toJson(student));
                            intent.putExtra("subject",gson.toJson(subject));
                            startActivity(intent);
                        }
                    });
                    break;

            }
            super.handleMessage(msg);

        }
    };
    private void loaddata()
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                MDBTools mdb=new MDBTools();
                subjectList=mdb.getSubjects();
                Message message=new Message();
                message.what=1;
                mhandler.sendMessage(message);

            }
        }.start();

    }
}
