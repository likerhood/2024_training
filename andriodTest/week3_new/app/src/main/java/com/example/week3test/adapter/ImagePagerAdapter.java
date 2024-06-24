package com.example.week3test.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.week3test.enity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<GoodsInfo> mGoodsList;
    // 图像列表
    private List<ImageView> mViewList = new ArrayList<>();

    public ImagePagerAdapter(Context mContext, ArrayList<GoodsInfo> mGoodsList) {
        this.mContext = mContext;
        this.mGoodsList = mGoodsList;

        for (GoodsInfo info : mGoodsList) {
            ImageView view = new ImageView(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT

            ));
            view.setImageResource(info.pic);
            mViewList.add(view);
        }

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
        ImageView item = mViewList.get(position);
        container.addView(item);
        return item;
    }


    // 从容器中销毁指定位置的页面
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewList.get(position));
    }
}