package com.example.administrator.mytestall.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.mytestall.Bean.NewsBean;
import com.example.administrator.mytestall.ListenerUnitl.NoDoubleClickListener;
import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.notification.NewsNotification;
import com.example.administrator.mytestall.ui.FullscreenActivity;
import com.example.administrator.mytestall.ui.WebActivity;
import com.example.administrator.mytestall.unitls.TimeUtil;
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
            AppCompatTextView item_name, item_name_2, item_name_3;
            AppCompatTextView item_time, item_time_2, item_time_3, author_name, author_name_2, author_name_3;
            ImageView item_img1, item_img1_2_1, item_img1_2_2, item_img1_3_1, item_img1_3_2, item_img1_3_3;
            LinearLayout ll,ll_1, ll_2, ll_3;
            view = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            item_name = (AppCompatTextView) view.findViewById(R.id.item_name);
            item_name_2 = (AppCompatTextView) view.findViewById(R.id.item_name_2);
            item_name_3 = (AppCompatTextView) view.findViewById(R.id.item_name_3);
            author_name = (AppCompatTextView) view.findViewById(R.id.author_name);
            author_name_2 = (AppCompatTextView) view.findViewById(R.id.author_name_2);
            author_name_3 = (AppCompatTextView) view.findViewById(R.id.author_name_3);
            item_time = (AppCompatTextView) view.findViewById(R.id.item_time);
            item_time_2 = (AppCompatTextView) view.findViewById(R.id.item_time_2);
            item_time_3 = (AppCompatTextView) view.findViewById(R.id.item_time_3);
            item_img1 = (ImageView) view.findViewById(R.id.item_img1);
            item_img1_2_1 = (ImageView) view.findViewById(R.id.item_img1_2_1);
            item_img1_2_2 = (ImageView) view.findViewById(R.id.item_img1_2_2);
            item_img1_3_1 = (ImageView) view.findViewById(R.id.item_img1_3_1);
            item_img1_3_2 = (ImageView) view.findViewById(R.id.item_img1_3_2);
            item_img1_3_3 = (ImageView) view.findViewById(R.id.item_img1_3_3);
            ll = (LinearLayout) view.findViewById(R.id.ll);
            ll_1 = (LinearLayout) view.findViewById(R.id.ll_1);

            ll_2 = (LinearLayout) view.findViewById(R.id.ll_2);
            ll_3 = (LinearLayout) view.findViewById(R.id.ll_3);
            viewHolder = new ViewHolder(item_name, item_name_2, item_name_3, item_time, item_time_2, item_time_3
                    , author_name, author_name_2, author_name_3,
                    item_img1, item_img1_2_1, item_img1_2_2, item_img1_3_1, item_img1_3_2, item_img1_3_3,
                    ll,ll_1 ,ll_2, ll_3);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (bean.get(position).getThumbnail_pic_s() != null
                && bean.get(position).getThumbnail_pic_s02() != null
                && bean.get(position).getThumbnail_pic_s03() == null) {

            viewHolder.ll_1.setVisibility(View.GONE);
            viewHolder.ll_2.setVisibility(View.VISIBLE);
            viewHolder.ll_3.setVisibility(View.GONE);
            viewHolder.item_name_2.setText(bean.get(position).getTitle());
            viewHolder.author_name_2.setText(bean.get(position).getAuthor_name().trim());
            viewHolder.item_time_2.setText(TimeUtil.friendlyTime(bean.get(position).getDate()));
            Picasso.with(context).load(bean.get(position).getThumbnail_pic_s()).into(viewHolder.item_img1_2_1);
            Picasso.with(context).load(bean.get(position).getThumbnail_pic_s02()).into(viewHolder.item_img1_2_2);
            viewHolder.ll.setOnClickListener(new NoDoubleClickListener() {

                @Override
                protected void onNoDoubleClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", bean.get(position).getUrl());
                    context.startActivity(intent);
                }
            });

        } else if (bean.get(position).getThumbnail_pic_s() != null
                && bean.get(position).getThumbnail_pic_s02() != null
                && bean.get(position).getThumbnail_pic_s03() != null) {

            viewHolder.ll_1.setVisibility(View.GONE);
            viewHolder.ll_2.setVisibility(View.GONE);
            viewHolder.ll_3.setVisibility(View.VISIBLE);
            viewHolder.item_name_3.setText(bean.get(position).getTitle());
            viewHolder.author_name_3.setText(bean.get(position).getAuthor_name().trim());
            viewHolder.item_time_3.setText(TimeUtil.friendlyTime(bean.get(position).getDate()));
            Picasso.with(context).load(bean.get(position).getThumbnail_pic_s()).into(viewHolder.item_img1_3_1);
            Picasso.with(context).load(bean.get(position).getThumbnail_pic_s02()).into(viewHolder.item_img1_3_2);
            Picasso.with(context).load(bean.get(position).getThumbnail_pic_s03()).into(viewHolder.item_img1_3_3);
            viewHolder.ll.setOnClickListener(new NoDoubleClickListener() {

                @Override
                protected void onNoDoubleClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", bean.get(position).getUrl());
                    context.startActivity(intent);
                }
            });

        } else if (bean.get(position).getThumbnail_pic_s() != null
                && bean.get(position).getThumbnail_pic_s02() == null
                && bean.get(position).getThumbnail_pic_s03() == null) {

            viewHolder.ll_1.setVisibility(View.VISIBLE);
            viewHolder.ll_2.setVisibility(View.GONE);
            viewHolder.ll_3.setVisibility(View.GONE);
            viewHolder.item_name.setText(bean.get(position).getTitle());
            viewHolder.author_name.setText(bean.get(position).getAuthor_name().trim());
            viewHolder.item_time.setText(TimeUtil.friendlyTime(bean.get(position).getDate()));
            Picasso.with(context).load(bean.get(position).getThumbnail_pic_s()).into(viewHolder.item_img1);

            viewHolder.ll.setOnClickListener(new NoDoubleClickListener() {

                @Override
                protected void onNoDoubleClick(View v) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", bean.get(position).getUrl());
               /*     NewsNotification.notify(context,bean.get(position).getUrl(),bean.get(position).getAuthor_name()
                            ,bean.get(position).getTitle(),2);*/
                    context.startActivity(intent);

                }
            });

        }

        return view;
    }

    class ViewHolder {
        AppCompatTextView item_name, item_name_2, item_name_3;
        AppCompatTextView item_time, item_time_2, item_time_3, author_name, author_name_2, author_name_3;
        ImageView item_img1, item_img1_2_1, item_img1_2_2, item_img1_3_1, item_img1_3_2, item_img1_3_3;
        LinearLayout ll,ll_1, ll_2, ll_3;
        ViewHolder(AppCompatTextView item_name, AppCompatTextView item_name_2, AppCompatTextView item_name_3,
                   AppCompatTextView  item_time,AppCompatTextView item_time_2,AppCompatTextView item_time_3
                ,AppCompatTextView author_name,AppCompatTextView author_name_2,AppCompatTextView author_name_3,
                   ImageView  item_img1, ImageView item_img1_2_1,ImageView item_img1_2_2
                ,ImageView item_img1_3_1,ImageView item_img1_3_2,ImageView item_img1_3_3,
                   LinearLayout ll,  LinearLayout ll_1,LinearLayout ll_2,LinearLayout ll_3){
            this.item_img1 = item_img1;
            this.author_name = author_name;
            this.ll = ll;
            this.ll_1 = ll_1;
            this.item_name = item_name;
            this.item_time = item_time;
            this.item_img1_2_1 = item_img1_2_1;
            this.item_img1_2_2 = item_img1_2_2;
            this.author_name_2 = author_name_2;
            this.ll_2 = ll_2;
            this.item_name_2 = item_name_2;
            this.item_time_2 = item_time_2;
            this.item_img1_3_1 = item_img1_3_1;
            this.item_img1_3_2 = item_img1_3_2;
            this.item_img1_3_3 = item_img1_3_3;
            this.author_name_3 = author_name_3;
            this.ll_3 = ll_3;
            this.item_name_3 = item_name_3;
            this.item_time_3 = item_time_3;
        }

    }
}
