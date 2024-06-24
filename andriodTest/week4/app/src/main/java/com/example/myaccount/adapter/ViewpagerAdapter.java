package com.example.myaccount.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myaccount.MyApplication;
import com.example.myaccount.R;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends PagerAdapter{

    private Context mContext;
    // 图像列表
    private List<View> mViewList = new ArrayList<>();
    public View currentSelectCategory1,currentSelectCategory2;
    public String cate1, cate2;

    public ViewpagerAdapter(Context mContext, String[] mInComeCatogryName, int[] mIncomePic, String[] mOutComeCatogryName, int[] mOutcomePic) {
        this.mContext = mContext;
        currentSelectCategory1 = MyApplication.getInstance().currentSelectCategory1;
        cate1 = MyApplication.getInstance().cate1;
        // 支出
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category_panel, null);
        GridLayout gl_channel = view.findViewById(R.id.gl_channel);
        gl_channel.removeAllViews();

       //ayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置分类大小
        int widthInDp = 70;
        int heightInDp = 80;
        float scale = mContext.getResources().getDisplayMetrics().density;
        int widthInPx = (int) (widthInDp * scale + 0.5f);
        int heightInPx = (int) (heightInDp * scale + 0.5f);

        // 创建新的布局参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthInPx, heightInPx);

        // 定位资源和监听事件
        for (int i = 0; i < mOutComeCatogryName.length; i++) {

            // 找到分类
            View item_category = LayoutInflater.from(mContext).inflate(R.layout.item_category, null);
            TextView tv_name = item_category.findViewById(R.id.tv_catogory);
            ImageView iv_thumb = item_category.findViewById(R.id.iv_thumb);
            String name = mOutComeCatogryName[i];

            if (i == 0){
                currentSelectCategory1 = item_category;
                ImageView used = currentSelectCategory1.findViewById(R.id.iv_thumb);
                MyApplication.getInstance().cate1 = name;
                used.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape_selected));

            }

            // 点击事件
            iv_thumb.setOnClickListener(v -> {
                ImageView used = currentSelectCategory1.findViewById(R.id.iv_thumb);
                used.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape));
                v.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape_selected));
                currentSelectCategory1 = item_category;
                MyApplication.getInstance().cate1 = name;
                Toast.makeText(mContext, "支出类别"+MyApplication.getInstance().cate1, Toast.LENGTH_SHORT).show();
            });

            tv_name.setText(mOutComeCatogryName[i]);
            iv_thumb.setImageResource(mOutcomePic[i]);
            gl_channel.addView(item_category, params);
        }

        // 支出加入一个viewpager
        mViewList.add(view);


        // 收入
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_category_panel, null);
        GridLayout gl_channel2 = view2.findViewById(R.id.gl_channel);
        gl_channel2.removeAllViews();
        currentSelectCategory2 = MyApplication.getInstance().currentSelectCategory2;
        cate2 = MyApplication.getInstance().cate2;

        // 定位资源和监听事件
        for (int i = 0; i < mInComeCatogryName.length; i++) {
            // 找到分类
            View item_category = LayoutInflater.from(mContext).inflate(R.layout.item_category, null);
            TextView tv_name = item_category.findViewById(R.id.tv_catogory);
            ImageView iv_thumb = item_category.findViewById(R.id.iv_thumb);
            // 点击事件
            String name = mInComeCatogryName[i];

            if (i == 0){
                currentSelectCategory2 = item_category;
                ImageView used = currentSelectCategory2.findViewById(R.id.iv_thumb);
                MyApplication.getInstance().cate2 = name;
                used.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape_selected));

            }
            iv_thumb.setOnClickListener(v -> {
                ImageView used = currentSelectCategory2.findViewById(R.id.iv_thumb);
                used.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape));
                v.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_shape_selected));
                currentSelectCategory2 = item_category;
                MyApplication.getInstance().cate2 = name;
                Toast.makeText(mContext, "收入类别"+MyApplication.getInstance().cate2, Toast.LENGTH_SHORT).show();
            });

            tv_name.setText(name);
            iv_thumb.setImageResource(mIncomePic[i]);
            gl_channel2.addView(item_category, params);
        }

        // 支出加入一个viewpager
        mViewList.add(view2);

    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    // 实例化指定位置，并且添加到容器中
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 添加一个view到container中，然后返回一个根这个view可以关联起来的对象
        // 这个view可以是自己，也可以是其他无关的对象
        // 关键是在isViewFromObject可以将view和这个object关联起来
        View item = mViewList.get(position);
        container.addView(item);
        return item;
    }


    // 从容器中销毁指定位置的页面
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));
    }
}
