package com.example.week3test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.week3test.adapter.CartAdapter;
import com.example.week3test.database.ShoppingDBHelper;
import com.example.week3test.enity.CartInfo;
import com.example.week3test.enity.GoodsInfo;
import com.example.week3test.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView tv_count, tv_total_price;
    private ShoppingDBHelper mDBHelper;
    // 购物车列表布局
    private LinearLayout ll_cart, ll_empty, ll_content;
    private ListView lv_cart;
    // 购物车信息列表
    private List<CartInfo> mCartList;
    // 声明一个根据商品编号查找商品信息的map映射，吧商品信息缓存起来，这样就不用每一次去查询数据库
    private HashMap<Integer, GoodsInfo> mGoodsMap = new HashMap<>();
    private CartAdapter mCartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        TextView tv_title = findViewById(R.id.tv_shopping_title);
        tv_title.setText("购物车");

        // 购物车中商品数量
        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        tv_total_price = findViewById(R.id.tv_total_price);

        // 获得购物车列表
        lv_cart = findViewById(R.id.lv_cart);

        // 创建数据库帮助类
        mDBHelper = ShoppingDBHelper.getInstance(this);

        // 返回
        findViewById(R.id.iv_back).setOnClickListener(this);

        // 没有商品时的列表显示
        ll_empty = findViewById(R.id.ll_empty);
        ll_content = findViewById(R.id.ll_content);

        // 结算和清空按钮的事件监听
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);
        // 购物车没有商品时的事件监听
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);

        showCount();

    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();
    }

    // 展示购物车的商品列表
    private void showCart() {
        // 移除所有子视图
        // 查询购物车数据库的所有商品记录
        mCartList = mDBHelper.queryAllCartInfo();
        if (mCartList.size() == 0){
            return;
        }

        for (CartInfo info: mCartList) {
            // 根据商品编号查询商品数据库的商品记录
            GoodsInfo goods = mDBHelper.queryGoodsById(info.goodsId);
            mGoodsMap.put(info.goodsId, goods);
            info.goods = goods;
        }

        // 全局适配器
        mCartAdapter = new CartAdapter(this, mCartList);
        lv_cart.setAdapter(mCartAdapter);
        // 设置点击事件
        lv_cart.setOnItemClickListener(this);
        // 设置长按删除事件
        lv_cart.setOnItemLongClickListener(this);

            // 计算总金额
        refreshTotalPrice();

    }

    private void deleteGoods(CartInfo info) {
        MyApplication.getInstance().goodsCount -= info.count;
        // 在数据库中删除
        mDBHelper.deleteCartInfoByGoodsId(info.goodsId);
        // 从列表删除
        CartInfo removed = null;
        for (CartInfo cartInfo : mCartList) {
            if (cartInfo.goodsId == info.goodsId){
                removed = cartInfo;
                break;
            }
        }

        mCartList.remove(removed);
        // 显示最新的商品数量
        showCount();
        mGoodsMap.remove(info.goodsId);
        refreshTotalPrice();
        ToastUtil.show(this,"已删除");
    }

    private void showCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        // 购物车没有商品
        if (MyApplication.getInstance().goodsCount == 0){
            ll_empty.setVisibility(View.VISIBLE);
            // 表头隐藏
            ll_content.setVisibility(View.GONE);
            // 购物车全部清楚
//            ll_cart.removeAllViews();
            // 通知适配器发生数据变化
            mCartAdapter.notifyDataSetChanged();
        } else {
            ll_empty.setVisibility(View.GONE);
            // 表头隐藏
            ll_content.setVisibility(View.VISIBLE);
        }
    }

    private void refreshTotalPrice() {
        int totalPrice = 0;
        for(CartInfo info: mCartList){
            GoodsInfo goods = mGoodsMap.get(info.goodsId);
            totalPrice += goods.price * info.count;
        }

        tv_total_price.setText(String.valueOf(totalPrice));

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back){
            finish();
        } else if (v.getId() == R.id.btn_shopping_channel) {
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_clear) {
            // 在数据库中删除
            mDBHelper.deleteAllCartInfo();
            MyApplication.getInstance().goodsCount = 0;
            showCount();
            ToastUtil.show(this,"已经全部删除");
        }else if (v.getId() == R.id.btn_settle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
            builder.setTitle("结算商品");
            builder.setMessage("抱歉，尚未开通支付功能");
            builder.setPositiveButton("我知道了",null);
            builder.create().show();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "商品详情");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CartInfo info = mCartList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
        builder.setMessage("是否从购物车移走" + info.goods.name + "?");
        builder.setPositiveButton("是",(dialog, which) -> {
            // 移除当前视图
            mCartList.remove(position);
            // 将适配器设置为全局的，通知适配器数据发生改变
            mCartAdapter.notifyDataSetChanged();
            // 删除该商品
            deleteGoods(info);
        });
        builder.setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
}