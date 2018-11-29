package com.ice.chao.demo;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements ColorSeekBar.ProgressChangeListener {

    private static final String TAG = "ice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorSeekBar colorSeekBar = findViewById(R.id.color_seek_bar);
        colorSeekBar.addProgressChangeListenter(this);
        colorSeekBar.setPlice(66);
    }

    @Override
    public void change(float max, float progress, float plice, float change) {
//        Toast.makeText(this, "max: " + max + "     progress : " + progress + "      plice : " + plice, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "change : " + change, Toast.LENGTH_SHORT).show();
    }

}
