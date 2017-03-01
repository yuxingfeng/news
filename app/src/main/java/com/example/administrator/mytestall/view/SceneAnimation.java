package com.example.administrator.mytestall.view;

import android.os.Handler;
import android.widget.ImageView;

public class SceneAnimation {
    private ImageView mImageView;
    private int[] mFrameRess;// 图片
    private int[] mDurations;
    private int mDuration;

    private Handler handler = new Handler();
    Runnable mRunnable;

    private int mLastFrameNo;
    private long mBreakDelay;

    //参数包括pDurations数组，执行播放play(1);
    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int[] pDurations) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDurations = pDurations;
        mLastFrameNo = pFrameRess.length - 1;

        mImageView.setBackgroundResource(mFrameRess[0]);
        play(1);
    }

    //参数包括一个int 的执行时间 pDuration， 执行循环播放playConstant(1);
    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;

        mImageView.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
    }

    //参数包括一个int的pDuration，一个long的pBreakDelay，表示每次播放的间隔，执行循环播放playConstant(1);
    public SceneAnimation(ImageView pImageView, int[] pFrameRess, int pDuration, long pBreakDelay) {
        mImageView = pImageView;
        mFrameRess = pFrameRess;
        mDuration = pDuration;
        mLastFrameNo = pFrameRess.length - 1;
        mBreakDelay = pBreakDelay;

        mImageView.setBackgroundResource(mFrameRess[0]);
        playConstant(1);
    }

    private void play(final int pFrameNo) {

        mRunnable = new Runnable() {
            public void run() {
                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                if (pFrameNo == mLastFrameNo)
                    play(0);
                else
                    play(pFrameNo + 1);
            }
        };
        handler.postDelayed(mRunnable, mDurations[pFrameNo]);
    }

    private void playConstant(final int pFrameNo) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mImageView.setBackgroundResource(mFrameRess[pFrameNo]);

                if (pFrameNo == mLastFrameNo)
                    playConstant(0);
                else
                    playConstant(pFrameNo + 1);
            }
        }, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay : mDuration);
    }


};
