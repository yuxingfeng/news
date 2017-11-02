package com.example.administrator.mytestall.fragment;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.Toast;

import com.example.administrator.mytestall.Adapter.NewsAdapter;
import com.example.administrator.mytestall.App;
import com.example.administrator.mytestall.Bean.NewsBean;
import com.example.administrator.mytestall.R;
import com.example.administrator.mytestall.unitls.ParseUnitls;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class NewsFragment2 extends Fragment {
    private View view;
    TextClock textClock;
    ListViewCompat listViewCompat;
    NewsAdapter adapter;
    List<NewsBean.ResultBean.DataBean> bean;
    Handler handler;
    LinearLayout ll_news,bg_bg;
    App app;
    private TwinklingRefreshLayout refreshLayout;
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
            bean.addAll(ParseUnitls.News(app.mCache.getAsString("guoji")).getResult().getData());
            handler.sendEmptyMessage(1);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"获取数据失败！",Toast.LENGTH_SHORT).show();
        }


    }

    private void initData() {
        listViewCompat.setAdapter(null);
        listViewCompat.addFooterView(bg_bg);
        bean = new ArrayList<NewsBean.ResultBean.DataBean>();
        adapter = new NewsAdapter(bean, getContext());
        listViewCompat.setAdapter(adapter);
    }

    private void initView() {
        refreshLayout= (TwinklingRefreshLayout) view.findViewById(R.id.refreshLayout);
        View bgView = LayoutInflater.from(getContext()).inflate(R.layout.bg, null);
        bg_bg= (LinearLayout) bgView.findViewById(R.id.bg_bg);
        bg_bg.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 30));
        textClock = (TextClock) view.findViewById(R.id.time);
        listViewCompat = (ListViewCompat) view.findViewById(R.id.lv);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                new Handler().postDelayed(() -> {
                    refreshLayout.finishRefreshing();
                    app.getData().News("guoji");
                    netWork();
                },2000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                new Handler().postDelayed(() -> refreshLayout.finishLoadmore(),2000);
            }
        });
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        listViewCompat.removeFooterView(bg_bg);
    }
}
