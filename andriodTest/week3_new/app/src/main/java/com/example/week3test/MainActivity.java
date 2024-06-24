package com.example.week3test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // shared测试
        findViewById(R.id.main_test_shared).setOnClickListener(this);
        // 数据库测试
        findViewById(R.id.main_test_SQL).setOnClickListener(this);
        // 账单测试
        findViewById(R.id.main_test_bill).setOnClickListener(this);
        // 账单测试
        findViewById(R.id.main_test_billstatic).setOnClickListener(this);
        // 外部存储测试
        findViewById(R.id.main_test_filetest).setOnClickListener(this);
        // room测试
        findViewById(R.id.main_test_roomtest).setOnClickListener(this);
        // 购物车测试
        findViewById(R.id.main_test_shoppingtest).setOnClickListener(this);
        // 下拉列表测试
        findViewById(R.id.main_test_spinnertest).setOnClickListener(this);
        // listview测试
        findViewById(R.id.main_test_listview).setOnClickListener(this);
        // ViewPager测试
        findViewById(R.id.main_test_ViewPager).setOnClickListener(this);
        // 启动页测试
        findViewById(R.id.main_test_imagePager).setOnClickListener(this);
        // ViewPager测试
        findViewById(R.id.main_test_fragment).setOnClickListener(this);
        // 启动测试
        findViewById(R.id.main_test_openFragment).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v.getId() == R.id.main_test_shared){
            intent.setClass(this, SharedReadActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_SQL) {
            intent.setClass(this, SQLiteHelperActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_bill) {
            intent.setClass(this, BillAddActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_billstatic) {
            intent.setClass(this, BillPagerActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_filetest) {
            intent.setClass(this, FileWriteActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_roomtest) {
            intent.setClass(this, RoomWriteActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_shoppingtest) {
            intent.setClass(this, ShoppingChannelActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_spinnertest) {
            intent.setClass(this, SpinnerDropdownActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_listview) {
            intent.setClass(this, ListViewActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_ViewPager) {
            intent.setClass(this, ViewPagerActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_imagePager) {
            intent.setClass(this, LaunchSimpleActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_fragment) {
            intent.setClass(this, FragmentTestActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_test_openFragment) {
            intent.setClass(this, LaunchImproveActivity.class);
            startActivity(intent);
        }
    }
}