package com.example.administrator.mytestall.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.test.espresso.core.deps.guava.eventbus.EventBus;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.mytestall.R;

public class Main2Activity extends AppCompatActivity {

    AppCompatImageView imageView;
    boolean isonlistener=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView= (AppCompatImageView) findViewById(R.id.imageView);


        View view=getLayoutInflater().inflate(R.layout.dialog_refresh,null);
        final Dialog dialog=new Dialog(this,R.style.dialog);
        final AnimationDrawable animationDrawable= (AnimationDrawable) view.findViewById(R.id.img).getBackground();
        animationDrawable.start();
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog.show();

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
