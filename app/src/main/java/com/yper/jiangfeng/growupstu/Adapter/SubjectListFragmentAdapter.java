package com.yper.jiangfeng.growupstu.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yper.jiangfeng.growupstu.Activity.Subject_oper;
import com.yper.jiangfeng.growupstu.Module.Comment;
import com.yper.jiangfeng.growupstu.Module.Photopic;
import com.yper.jiangfeng.growupstu.Module.Student;
import com.yper.jiangfeng.growupstu.Module.Zan;
import com.yper.jiangfeng.growupstu.MyApplication;
import com.yper.jiangfeng.growupstu.R;
import com.yper.jiangfeng.growupstu.Utils.BitmapCache;
import com.yper.jiangfeng.growupstu.Utils.MDBTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Feng on 2016/9/26.
 * 专题图片的列表页面
 */

public class SubjectListFragmentAdapter extends BaseAdapter {
    private List<Photopic> photolist =new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    private RequestQueue queue;
    private ImageLoader imageLoader;
    private Student student;
    private MDBTools mdb=new MDBTools();
    private PopupWindow mMorePopupWindow;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;

    public SubjectListFragmentAdapter(List<Photopic> photolist, Context context, Student student) {
        this.photolist = photolist;
        this.context = context;
        this.student=student;
        queue= Volley.newRequestQueue(context);
        imageLoader=new ImageLoader(queue,new BitmapCache());
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photolist.size();
    }

    @Override
    public Object getItem(int position) {
        return photolist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Photopic item=photolist.get(position);
        final viewHolder vh;
        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.subject_list_photo_item,parent,false);
            vh.photopic= (NetworkImageView) convertView.findViewById(R.id.photopic);
            vh.photoauthor= (TextView) convertView.findViewById(R.id.photoauthor);
            vh.photodate= (TextView) convertView.findViewById(R.id.photodate);
            vh.photomemo= (TextView) convertView.findViewById(R.id.photomemo);
            vh.imageButton=(ImageButton) convertView.findViewById(R.id.imageButton);
            vh.relativeLayout=(RelativeLayout) convertView.findViewById(R.id.relayout);
            vh.zanimg=(ImageView) convertView.findViewById(R.id.imgzan);

            convertView.setTag(vh);
        }
        else
        {
            vh= (viewHolder) convertView.getTag();
        }

        String url= MyApplication.getInstance().Url+item.getPicname();
        vh.photopic.setImageUrl(url,imageLoader);
        vh.photoauthor.setText(item.getPhotoauthor());
        vh.photomemo.setText(item.getPhotomemo());
        vh.zanimg.setImageResource(android.R.drawable.btn_star_big_off);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if (item.getPhotodate()!=null )vh.photodate.setText(sdf.format(item.getPhotodate()));

        int iszaned=0;

        if(item.getZanList()!=null)
        {
            for(int i=0;i<item.getZanList().size();i++) {
                if (student.get_id().equals(item.getZanList().get(i).getZanpeopleid())) {
                    vh.zanimg.setImageResource(android.R.drawable.btn_star_big_on);
                    iszaned=1;
                    break;
                }

            }
            }

        final int finalIszaned = iszaned;
        vh.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMore(v, item, finalIszaned);
            }
        });

        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,3,3,10);

        if(item.getCommentList()!=null) {
            for (int i = 0; i < item.getCommentList().size(); i++) {
                TextView txt = new TextView(context);
                txt.setTextColor(Color.BLACK);
                txt.setText(item.getCommentList().get(i).getCommentpeoplename() + " : " + item.getCommentList().get(i).getCommentbody());

                linearLayout.addView(txt);
            }
        }

        RelativeLayout.LayoutParams rp= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        rp.addRule(RelativeLayout.BELOW,R.id.imageButton);

        vh.relativeLayout.addView(linearLayout,rp);
        return convertView;

    }

    static class viewHolder{
        NetworkImageView photopic;
        TextView photoauthor;
        TextView photodate;
        TextView photomemo;
        ImageView zanimg;
        ImageButton imageButton;
        RelativeLayout relativeLayout;

    }

    private void showMore(final View moreBtnView, final Photopic item, final int iszaned) {

        if (mMorePopupWindow == null) {

            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View content = li.inflate(R.layout.layout_more, null, false);

            mMorePopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMorePopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mMorePopupWindow.setOutsideTouchable(true);
            mMorePopupWindow.setTouchable(true);

            content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mShowMorePopupWindowWidth = content.getMeasuredWidth();
            mShowMorePopupWindowHeight = content.getMeasuredHeight();

            View parent = mMorePopupWindow.getContentView();

            TextView like = (TextView) parent.findViewById(R.id.like);
            TextView comment = (TextView) parent.findViewById(R.id.comment);

            // 点赞的监听器
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            if(iszaned==0) {
                                Zan zan = new Zan(student.get_id(), student.getName());
                                List<Zan> zanlist = item.getZanList();
                                if (zanlist == null) zanlist = new ArrayList<Zan>();
                                zanlist.add(zan);

                                item.setZanList(zanlist);
                                mdb.savePhotopic(item);

                            }
                        }
                    }.start();
                mMorePopupWindow.dismiss();
                 }

            });

            // 评论的监听器
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   View pare= moreBtnView.getRootView();
                   final Subject_oper subject_oper=(Subject_oper) pare.getContext();
                    final EditText editText = new EditText(subject_oper);
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(subject_oper);
                    inputDialog.setTitle("输入评论").setView(editText);
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    List<Comment> comments=item.getCommentList();
                                    if (comments==null) comments=new ArrayList<Comment>();
                                    Comment comment=new Comment(student.get_id(),student.getName(),editText.getText().toString());
                                    comments.add(comment);
                                    item.setCommentList(comments);
                                    new Thread(){
                                        @Override
                                        public void run() {
                                            super.run();
                                            mdb.savePhotopic(item);
                                        }
                                    }.start();
                                mMorePopupWindow.dismiss();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

                    mMorePopupWindow.dismiss();



                }
            });
        }

        if (mMorePopupWindow.isShowing()) {
            mMorePopupWindow.dismiss();
        } else {
            int heightMoreBtnView = moreBtnView.getHeight();
            mMorePopupWindow.showAsDropDown(moreBtnView, -mShowMorePopupWindowWidth,
                    -(mShowMorePopupWindowHeight + heightMoreBtnView) / 2);
        }
    }


}
