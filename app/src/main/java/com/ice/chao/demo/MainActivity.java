package com.ice.chao.demo;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements ColorSeekBar.ProgressChangeListener {

    private static final String TAG = "ice";
    private TextView textView;
    private FrameLayout frameLayout;
    private View viewLeft;
    private View viewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorSeekBar colorSeekBar = findViewById(R.id.color_seek_bar);
        textView = findViewById(R.id.text_view);
        viewLeft = findViewById(R.id.view_left);
        viewRight = findViewById(R.id.view_right);

        colorSeekBar.addProgressChangeListenter(this);
        colorSeekBar.setPlice(66);
    }

    @Override
    public void change(float max, float progress, float plice, float change) {
//        Toast.makeText(this, "max: " + max + "     progress : " + progress + "      plice : " + plice, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "change : " + change, Toast.LENGTH_SHORT).show();

        textView.setText((int) (change * 100) + "%");

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewLeft.getLayoutParams();
        layoutParams.weight = change * 100;
        viewLeft.setLayoutParams(layoutParams);

        layoutParams = (LinearLayout.LayoutParams) viewRight.getLayoutParams();
        layoutParams.weight = (1 - change) * 100;
        viewRight.setLayoutParams(layoutParams);

//        textView.measure(0, 0);
//        int measuredWidth = textView.getMeasuredWidth();
//        int tempWidth = frameLayout.getWidth() - measuredWidth;
//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
//        layoutParams.leftMargin = (int) (tempWidth * change);
//        textView.setLayoutParams(layoutParams);

    }


}
