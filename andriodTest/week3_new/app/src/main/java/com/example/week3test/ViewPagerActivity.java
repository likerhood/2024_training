package com.example.week3test;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.week3test.adapter.ImagePagerAdapter;
import com.example.week3test.enity.GoodsInfo;
import com.example.week3test.utils.ToastUtil;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ArrayList<GoodsInfo> mGoodsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ViewPager vp_content = findViewById(R.id.vp_content);
        mGoodsList = GoodsInfo.getDefaultList();
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, mGoodsList);
        vp_content.setAdapter(adapter);
        // 添加页面变更监听器
        vp_content.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 翻页完成之后除法，position表示当前滑倒第几页
    @Override
    public void onPageSelected(int position) {
        ToastUtil.show(this, "手机品牌是" + mGoodsList.get(position).name);
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}