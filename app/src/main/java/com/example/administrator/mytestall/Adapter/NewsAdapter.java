package com.example.administrator.mytestall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.administrator.mytestall.Bean.NewsBean;
import com.example.administrator.mytestall.ListenerUnitl.NoDoubleClickListener;
import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.ui.WebActivity;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */

public class NewsAdapter extends BaseAdapter {
    List<NewsBean.ResultBean.DataBean> bean;
    Context context;

    public NewsAdapter(List<NewsBean.ResultBean.DataBean> bean, Context context) {
        this.bean = bean;
        this.context = context;

    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            AppCompatTextView item_name;
            AppCompatTextView  item_time;
            AppCompatImageView item_img1;
            LinearLayout ll;
            view = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            item_name = (AppCompatTextView) view.findViewById(R.id.item_name);
            item_time = (AppCompatTextView) view.findViewById(R.id.item_time);
            item_img1 = (AppCompatImageView) view.findViewById(R.id.item_img1);
            ll = (LinearLayout) view.findViewById(R.id.ll);
            viewHolder = new ViewHolder(item_name, item_time, item_img1,ll);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.item_name.setText(bean.get(position).getTitle());


        viewHolder.item_time.setText(bean.get(position).getDate());
        Picasso.with(context).load(bean.get(position).getThumbnail_pic_s()).into(viewHolder.item_img1);
        viewHolder.ll.setOnClickListener(new NoDoubleClickListener() {

            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent=new Intent(context, WebActivity.class);
                intent.putExtra("url",bean.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        return view;
    }

    class ViewHolder {
        AppCompatTextView  item_name;
        AppCompatTextView item_time;
        AppCompatImageView item_img1;
        LinearLayout ll;

        ViewHolder(AppCompatTextView item_name, AppCompatTextView item_time, AppCompatImageView item_img1, LinearLayout ll) {
            this.item_img1 = item_img1;
            this.ll = ll;
            this.item_name = item_name;
            this.item_time = item_time;
        }
    }
}
