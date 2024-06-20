package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MetaDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_data);
        TextView tv_meta = findViewById(R.id.tv_meta);
        // 获取应用包管理器
        PackageManager pm = getPackageManager();
        try {
            // 从包管理器中获取当前元数据信息
            ActivityInfo info = pm.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            // 获取活动附加信息
            Bundle bundle = info.metaData;
            String weather = bundle.getString("weather");
            tv_meta.setText(weather);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}