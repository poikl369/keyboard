package com.ly.keyboard;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import project.ly.com.mylibrary.AbsKeyBoardChangeListener;

public class MainActivity extends AppCompatActivity {

    private AbsKeyBoardChangeListener mAbsKeyBoardChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAbsKeyBoardChangeListener = new AbsKeyBoardChangeListener(MainActivity.this) {

            @Override public void onKeyBoardChanged(boolean isChanged) {
                String text = isChanged ? " show keyboard " : " hide keyboard ";
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAbsKeyBoardChangeListener != null) {
            mAbsKeyBoardChangeListener.release();
        }
    }
}
