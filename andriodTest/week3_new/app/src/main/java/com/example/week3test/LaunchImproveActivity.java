package com.example.week3test;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.week3test.adapter.LaunchImproveAdapter;

public class LaunchImproveActivity extends AppCompatActivity {

    // 声明图片
    private int[] launchImageArray = {
            R.drawable.guide_bg1,
            R.drawable.guide_bg2,
            R.drawable.guide_bg3,
            R.drawable.guide_bg4,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_improve);

        ViewPager vp_launch = findViewById(R.id.vp_fragment_content);
        LaunchImproveAdapter adapter = new LaunchImproveAdapter(getSupportFragmentManager(), launchImageArray);
        vp_launch.setAdapter(adapter);



    }
}