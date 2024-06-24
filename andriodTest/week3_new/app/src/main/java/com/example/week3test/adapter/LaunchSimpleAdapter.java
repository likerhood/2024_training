package com.example.week3test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.week3test.R;
import com.example.week3test.utils.ToastUtil;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class LaunchSimpleAdapter extends PagerAdapter {

    private List<View> mViewList = new ArrayList<>();

    public LaunchSimpleAdapter(Context mContext, int[] imageArray) {
        for (int i = 0; i < imageArray.length; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_launch, null);
            ImageView iv_launch = view.findViewById(R.id.iv_launch);
            RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
            Button btn_start = view.findViewById(R.id.btn_start);
            iv_launch.setImageResource(imageArray[i]);

            // 每一个页面分配一组对应的按钮
            for (int i1 = 0; i1 < imageArray.length; i1++) {
                RadioButton radio = new RadioButton(mContext);
                radio.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                radio.setPadding(10,10,10,10);
                rg_indicate.addView(radio);
            }

            // 当前位置的单选按钮要高亮显示
            ((RadioButton)rg_indicate.getChildAt(i)).setChecked(true);

            // 最后一个页面按钮展示
            if(i == imageArray.length - 1){
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(v -> {
                    ToastUtil.show(mContext, "欢迎开启美好生活");
                });
            }

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
