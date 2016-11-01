package com.yper.jiangfeng.growupstu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.yper.jiangfeng.growupstu.Module.Subject;
import com.yper.jiangfeng.growupstu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2016/9/20.
 */

public class MainSubjectListAdapter extends BaseAdapter {
    private List<Subject> items = new ArrayList<>();
    private LayoutInflater layoutInflater = null;
    private Context context;

    public MainSubjectListAdapter(List<Subject> items, Context context) {
        this.items = items;
        this.context = context;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Subject item=items.get(position);
        final viewHolder vh;
        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.subject_list_item,parent,false);
            vh.subjectName= (TextView) convertView.findViewById(R.id.txtSubName);
            vh.subjectInfo= (TextView) convertView.findViewById(R.id.txtSubinfo);
            vh.subjectStarttime= (TextView) convertView.findViewById(R.id.txtSubstime);
            vh.subjectEndttime= (TextView) convertView.findViewById(R.id.txtSubendtime);

            convertView.setTag(vh);
        }
        else
        {
            vh= (viewHolder) convertView.getTag();
        }

        vh.subjectName.setText(item.getSubjectName());
        vh.subjectInfo.setText(item.getSubjectInfo());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if (item.getStartTime()!=null )vh.subjectStarttime.setText(sdf.format(item.getStartTime()));
        if (item.getEndTime()!=null) vh.subjectEndttime.setText(sdf.format(item.getEndTime()));

        return convertView;
    }

   static class viewHolder{
       TextView subjectName;
       TextView subjectInfo;
       TextView subjectStarttime;
       TextView subjectEndttime;

   }

}
