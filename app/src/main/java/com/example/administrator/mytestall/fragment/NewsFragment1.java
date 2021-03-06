package com.example.administrator.mytestall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.administrator.mytestall.Adapter.NewsAdapter;
import com.example.administrator.mytestall.App;
import com.example.administrator.mytestall.Bean.NetworkImageHolderView;
import com.example.administrator.mytestall.Bean.NewsBean;
import com.example.administrator.mytestall.R;

import com.example.administrator.mytestall.ui.WebActivity;
import com.example.administrator.mytestall.unitls.ParseUnitls;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.ToxicBakery.viewpager.transforms.*;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Administrator on 2016/12/12.
 */

public class NewsFragment1 extends Fragment implements ViewPager.OnPageChangeListener
        , com.bigkoo.convenientbanner.listener.OnItemClickListener {
    private View view;
    private TextClock textClock;
    private ListViewCompat listViewCompat;
    private NewsAdapter adapter;
    private List<NewsBean.ResultBean.DataBean> bean, beanBanner;
    private Handler handler;
    private LinearLayout bg_bg;
    private TwinklingRefreshLayout refreshLayout;
    App app;
    private List<String> networkImages, networkImagesUrl;
    /*    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
                "http://img2.3lian.com/2014/f2/37/d/40.jpg",
                "http://d.3987.com/sqmy_131219/001.jpg",
                "http://img2.3lian.com/2014/f2/37/d/39.jpg",
                "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
                "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
                "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
        };*/
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ArrayList<String> transformerList = new ArrayList<String>();

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

    public final void netWork() {
       /* dialog.show();
        dialog.dismiss();
        dialog=null;
        dialog_view=null;*/
        try {

            bean.clear();
            String a = app.mCache.getAsString("guonei");
            Log.e("getAsString", a);
            bean.addAll(ParseUnitls.News(app.mCache.getAsString("guonei")).getResult().getData());
            handler.sendEmptyMessage(1);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "获取数据失败！", Toast.LENGTH_SHORT).show();
        }


    }

    private void initData() {
        initImageLoader();
        // 网络加载例子
        loadTestDatas(10);
        networkImages = app.getImages();
        networkImagesUrl = app.getImagesUrl();
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                //监听翻页事件
                .setOnItemClickListener(this);
        // .setOnPageChangeListener(this);


        listViewCompat.setAdapter(null);
        listViewCompat.addHeaderView(convenientBanner);
        listViewCompat.setAdapter(null);
        listViewCompat.addFooterView(bg_bg);
        bean = new ArrayList<NewsBean.ResultBean.DataBean>();
        adapter = new NewsAdapter(bean, getContext());
        listViewCompat.setAdapter(adapter);
    }

    private void initView() {
        refreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refreshLayout);
        View bgView = LayoutInflater.from(getContext()).inflate(R.layout.bg, null);
        bg_bg = (LinearLayout) bgView.findViewById(R.id.bg_bg);
        bg_bg.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 30));
        View bannerView = LayoutInflater.from(getContext()).inflate(R.layout.banner, null);
        convenientBanner = (ConvenientBanner) bannerView.findViewById(R.id.convenientBanner);
        //addHeaderView必须要在这设置banner的宽高
        convenientBanner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 4));
        textClock = (TextClock) view.findViewById(R.id.time);
        listViewCompat = (ListViewCompat) view.findViewById(R.id.lv);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                new Handler().postDelayed(() -> {
                    refreshLayout.finishRefreshing();
                    app.getData().News("guonei");
                    netWork();
                }, 2000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                new Handler().postDelayed(() -> refreshLayout.finishLoadmore(), 2000);
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
        banner();
    }

    private void banner() {
        if (App.getInstance().getmCache().getAsString("junshi") != null) {
            beanBanner = new ArrayList<NewsBean.ResultBean.DataBean>();
            beanBanner.clear();
            beanBanner.addAll(ParseUnitls.News(App.getInstance().getmCache().getAsString("junshi")).getResult().getData());
            if (beanBanner.size() != 0) {
                for (int i = 0; i < beanBanner.size(); i++) {
                    App.getInstance().getImages().add(beanBanner.get(i).getThumbnail_pic_s());
                    App.getInstance().getImagesUrl().add(beanBanner.get(i).getUrl());
                }

            }
        }

    }

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    //设置翻页动画
    private void loadTestDatas(int position) {
        //各种翻页效果
        transformerList.add(DefaultTransformer.class.getSimpleName());
        transformerList.add(AccordionTransformer.class.getSimpleName());
        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
        transformerList.add(CubeInTransformer.class.getSimpleName());
        transformerList.add(CubeOutTransformer.class.getSimpleName());
        transformerList.add(DepthPageTransformer.class.getSimpleName());
        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
        transformerList.add(RotateDownTransformer.class.getSimpleName());
        transformerList.add(RotateUpTransformer.class.getSimpleName());
        transformerList.add(StackTransformer.class.getSimpleName());
        transformerList.add(ZoomInTransformer.class.getSimpleName());
        transformerList.add(ZoomOutTranformer.class.getSimpleName());
        String transforemerName = transformerList.get(position);
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true, transforemer);

            //部分3D特效需要调整滑动速度
            if (transforemerName.equals("StackTransformer")) {
                convenientBanner.setScrollDuration(1200);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listViewCompat.removeHeaderView(convenientBanner);
        listViewCompat.removeFooterView(bg_bg);
        App.getInstance().getImages().clear();
        App.getInstance().getImagesUrl().clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra("url", networkImagesUrl.get(position));
        getContext().startActivity(intent);

    }
}
