package com.example.administrator.mytestall.unitls;

import com.example.administrator.mytestall.Bean.NewsBean;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ParseUnitls {
    public static NewsBean  News(String s)
    {
        Gson gson = new Gson();
        return gson.fromJson(s, NewsBean.class);
    }
}
