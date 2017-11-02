package com.example.administrator.mytestall.ListenerUnitl;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/10/30.
 */

public abstract class NoDoubleOnItemClickListener implements com.bigkoo.convenientbanner.listener.OnItemClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    @Override
    public void onItemClick(int position) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleOnItemClick(position);
        }
    }
    protected abstract void onNoDoubleOnItemClick(int  position);
}
