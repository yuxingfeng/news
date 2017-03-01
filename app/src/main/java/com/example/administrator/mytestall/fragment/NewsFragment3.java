package com.example.administrator.mytestall.fragment;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.Toast;

import com.example.administrator.mytestall.Adapter.NewsAdapter;
import com.example.administrator.mytestall.App;
import com.example.administrator.mytestall.Bean.NewsBean;
import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.unitls.ParseUnitls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class NewsFragment3 extends Fragment {
    private View view;
    TextClock textClock;
    ListView listViewCompat;
    NewsAdapter adapter;
    List<NewsBean.ResultBean.DataBean> bean;
    Handler handler;
    Dialog dialog;
    View dialog_view;
    LinearLayout ll_news;
    App app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = View.inflate(getContext(), R.layout.news_fragment, null);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        app = (App) getActivity().getApplication();
        initView();
        initData();
        netWork();
    }

    private void netWork() {
       /* dialog.show();
        dialog.dismiss();
        dialog=null;
        dialog_view=null;*/
        try {
            bean.clear();
            bean.addAll(ParseUnitls.News(app.getmSharedPreferences("keji")).getResult().getData());
            handler.sendEmptyMessage(1);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"获取数据失败！",Toast.LENGTH_SHORT).show();
        }


    }

    private void initData() {
       /* if (dialog_view == null)
            dialog_view = View.inflate(getContext(), R.layout.dialog_refresh, null);
        if (dialog == null)
            dialog = new Dialog(getContext(), R.style.dialog);
        final AnimationDrawable animationDrawable = (AnimationDrawable) dialog_view.findViewById(R.id.img).getBackground();
        animationDrawable.start();
        dialog.setContentView(dialog_view);
        dialog.setCanceledOnTouchOutside(false);*/
        bean = new ArrayList<NewsBean.ResultBean.DataBean>();
        adapter = new NewsAdapter(bean, getContext());
        listViewCompat.setAdapter(adapter);
    }

    private void initView() {
        ll_news = (LinearLayout) view.findViewById(R.id.ll_news);
        textClock = (TextClock) view.findViewById(R.id.time);
        listViewCompat = (ListView) view.findViewById(R.id.lv);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

}
