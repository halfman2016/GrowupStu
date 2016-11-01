package com.yper.jiangfeng.growupstu.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.jiangfeng.growupstu.Module.Photopic;
import com.yper.jiangfeng.growupstu.Module.Student;
import com.yper.jiangfeng.growupstu.Module.Subject;
import com.yper.jiangfeng.growupstu.R;
import com.yper.jiangfeng.growupstu.Utils.MDBTools;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TakePhoto extends AppCompatActivity {

    Student student;
    Subject subject=null;
    ImageView imageView;
    Photopic photopic=new Photopic();
    EditText photomemo;
    TextView photoauthor;
    TextView photodate;
    String strphotomemo;
    ProgressBar progressBar;
    Gson gson=new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        Intent intent=getIntent();
        String filename=intent.getStringExtra("filename");

        student=gson.fromJson(intent.getStringExtra("student"),Student.class);

//        if(intent.getStringExtra("subject")==null)
//        {
//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                MDBTools mdb=new MDBTools();
//                    subject=mdb.getSubject("a56b41a7-7c6a-4897-8fb2-2cdd39e00381");
//                }
//            }.start();
//
//        }
//        else
//        {
        subject=gson.fromJson(intent.getStringExtra("subject"),Subject.class);
        photopic.setBelongToSubject(subject.get_id());




        Bitmap bm= BitmapFactory.decodeFile(filename);
        bm=bitmapresize(bm,800);


        imageView=(ImageView)findViewById(R.id.imageView);
        photomemo=(EditText)findViewById(R.id.photomemo);
        photoauthor=(TextView) findViewById(R.id.photoauthor);
        photodate=(TextView)findViewById(R.id.photodate);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        photoauthor.setText(student.getName());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        photodate.setText(sdf.format(new Date()));

        imageView.setImageBitmap(bm);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);

        photopic.setPhotopreview(baos.toByteArray());
        Button btn= (Button) findViewById(R.id.btnLoadPhoto);
        MyListener myListener=new MyListener();
        btn.setOnClickListener(myListener);



    }

    private Bitmap bitmapresize(Bitmap bitmap, int newwidth){

        int rawHeight=bitmap.getHeight();
        int rawWidth=bitmap.getWidth();


        float widthScale=((float)newwidth)/rawWidth;
        float heightScale=widthScale;

        Matrix matrix = new Matrix();

        matrix.postScale(heightScale,widthScale);

        bitmap= Bitmap.createBitmap(bitmap,0,0,rawWidth,rawHeight,matrix,true);
        return bitmap;
    }

    private  class  MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            if (view.getId()==R.id.btnLoadPhoto)
            {
                Log.d("myapp","hello loadphoto");
                strphotomemo=photomemo.getText().toString();

                addPhotoTask ad=new addPhotoTask();
                progressBar.setVisibility(View.VISIBLE);
                ad.execute();
            }



        }
    }




private  class addPhotoTask extends AsyncTask<String,Integer,Boolean> {


    @Override
    protected Boolean doInBackground(String... strings) {
        MDBTools mdbTools=new MDBTools();
        photopic.setPhotoauthorid(student.get_id());
        photopic.setPhotodate(new Date());
        photopic.setPhotoauthor(student.getName());
        photopic.setBelongToSubject(subject.get_id());
        photopic.setPhotomemo(strphotomemo);
        publishProgress(1,1);
        if (mdbTools.addPhoto(photopic))
        {

            return true;
        }
        else
        {
            //Toast.makeText(getBaseContext(), "上传失败！请核对手机时间，时间不正确将不能上传！", Toast.LENGTH_SHORT);
            return false;

        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        photopic.setPhotomemo(photomemo.getText().toString());
       if(subject==null) {
           setResult(101);  //main调用 返回101
       }
        else
       {
           setResult(102);  //subject调用 返回102
       }
           finish();
    }
}

    private class getPhotoTask extends AsyncTask<String,Integer,String> {
        Photopic photopic;

        @Override
        protected String doInBackground(String... strings) {

            MDBTools mdbTools=new MDBTools();
            photopic=mdbTools.getPhoto(UUID.fromString(strings[0]));

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            File mediaStorageDir=new File(getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"growup");
//            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "id.jpg");
            Bitmap bm= BitmapFactory.decodeByteArray(photopic.getPhotofile(),0,photopic.getPhotofile().length);
            imageView.setImageBitmap(bm);

        }
    }
}

