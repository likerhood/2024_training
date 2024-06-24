package com.example.week3test.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.week3test.R;
import com.example.week3test.utils.ToastUtil;


public class LaunchFragment extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static LaunchFragment newInstance(int count ,int position, int image_id) {
        LaunchFragment fragment = new LaunchFragment();
        Bundle args = new Bundle();
        args.putInt("count", count);
        args.putInt("position", position);
        args.putInt("image_id", image_id);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 拿到上下文
        Context mContext = getContext();
        // 先拿到数据
        Bundle arguments = getArguments();
        int count = arguments.getInt("count", 0);
        int position = arguments.getInt("position", 0);
        int imageId = arguments.getInt("image_id", 0);

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_launch, null);
        ImageView iv_launch = view.findViewById(R.id.iv_launch);
        RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
        Button btn_start = view.findViewById(R.id.btn_start);
        iv_launch.setImageResource(imageId);

        // 每一个页面分配一组对应的按钮
        for (int i1 = 0; i1 < count; i1++) {
            RadioButton radio = new RadioButton(mContext);
            radio.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            radio.setPadding(10,10,10,10);
            rg_indicate.addView(radio);
        }

        // 当前位置的单选按钮要高亮显示
        ((RadioButton)rg_indicate.getChildAt(position)).setChecked(true);

        // 最后一个页面按钮展示
        if(position == count - 1){
            btn_start.setVisibility(View.VISIBLE);
            btn_start.setOnClickListener(v -> {
                ToastUtil.show(mContext, "欢迎开启美好生活");
            });
        }



        // Inflate the layout for this fragment
        return view;
    }
}