package com.example.week3test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.week3test.database.ShoppingDBHelper;
import com.example.week3test.enity.GoodsInfo;
import com.example.week3test.utils.ToastUtil;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener {

    // 商品数据库帮助类
    private ShoppingDBHelper mDBHelper;
    private TextView tv_count;
    private GridLayout gl_channel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);
        mDBHelper = ShoppingDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

        // 设置标题
        TextView tv_title = findViewById(R.id.tv_shopping_title);
        tv_title.setText("手机商场");

//         购物车数量
        tv_count = findViewById(R.id.tv_count);
        gl_channel = findViewById(R.id.gl_channel);

        // 从数据库中查询信息，展示
        showGoods();

        // 结束页面按钮监听和跳转购物车界面监听
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);


    }

    // onResume是当从其他页面跳转到该页面之后，需要执行的操作
    @Override
    protected void onResume() {
        super.onResume();
        // 查询购物车商品总数
        // 可能在其他页面删除或者增加了商品总数
        showCartInfoTotal();
    }

    private void showCartInfoTotal() {
        int count = mDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }

    private void showGoods() {
        // 商品条目是一个线性布局，设置布局的宽度为屏幕的一般
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT);

        // 移除所有子视图
        gl_channel.removeAllViews();

        List<GoodsInfo> list = mDBHelper.queryAllGoodsInfo();
        for (GoodsInfo info: list) {
            // 获取布局item_goods.xml的根视图
            // 变成一个java对象
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_price = view.findViewById(R.id.tv_price);

            // 按钮事件监听
            Button btn_add = view.findViewById(R.id.btn_add);
            btn_add.setOnClickListener(v -> {
                addToCart(info.id, info.name);
            });

            // 动态设置值
            iv_thumb.setImageURI(Uri.parse(info.picPath));
            tv_name.setText(info.name);
//            Log.d("price", String.valueOf(info.price));
            tv_price.setText(String.valueOf((int) info.price));

            // 把商品视图添加到网格布局
            gl_channel.addView(view, params);

        }

    }

    private void addToCart(int goodsId, String name) {
        mDBHelper.insertCartInfo(goodsId);
        // 购物车数量+1
        int cnt = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(cnt));
        ToastUtil.show(this,"已添加到购物车");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back){
            finish();
        }else if (v.getId() == R.id.iv_cart){
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}