package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ProgressActivity extends AppCompatActivity {

    private Intent intent;
    private ProgressBar progressBar;
    private ObjectAnimator progresAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        intent=getIntent();

        init();
        progresAnimator.start();
        progresAnimator.setDuration(2000);
        progresAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameId,new Fragment1()).commit();

            }
        });

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void init(){
        progressBar=findViewById(R.id.progressBar);
        progresAnimator=ObjectAnimator.ofInt(progressBar,"progress",0,100);

    }
}