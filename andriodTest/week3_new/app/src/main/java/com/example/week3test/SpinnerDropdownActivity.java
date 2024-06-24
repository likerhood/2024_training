package com.example.week3test;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.week3test.adapter.PlanetBaseAdapter;
import com.example.week3test.enity.Planet;
import com.example.week3test.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerDropdownActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final static String[] starArray = {
            "水星",
            "木星",
            "火星",
            "地球",
            "金星",
            "天王星"
    };
    private Spinner sp_downdrop,sp_dialog;
    private Spinner sp_simple,sp_base;
    // 定义下拉列表需要的图片资源
    private static int[] iconArray = {
            R.drawable.xiaomi,
            R.drawable.rongao,
            R.drawable.vivo,
            R.drawable.huawei,
            R.drawable.oppo,
            R.drawable.iphone
    };

    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_dropdown);

        sp_downdrop = findViewById(R.id.sp_dropdown);
        sp_dialog = findViewById(R.id.sp_dialog);
        // 声明一个数组适配器
        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this, R.layout.item_selector, starArray);
        sp_downdrop.setAdapter(startAdapter);

        sp_dialog.setPrompt("请选择星球");
        sp_dialog.setAdapter(startAdapter);
        // 默认选中第一项
        sp_downdrop.setSelection(0);
        sp_dialog.setSelection(0);
        // 给下拉框设置选中监听器
        sp_downdrop.setOnItemSelectedListener(this);
        sp_dialog.setOnItemSelectedListener(this);

        // 声明一个映射对象列表，用于保存图标和名称的配对信息
        List<Map<String, Object>> list = new ArrayList<>();
        // 遍历存放
        for (int i = 0; i < iconArray.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("icon", iconArray[i]);
            item.put("name", starArray[i]);
            list.add(item);
        }
        // 创建适配器
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,
                R.layout.item_simple,
                new String[]{"icon", "name"},
                new int[]{R.id.iv_icon, R.id.tv_name});
        // 找到下拉列表
        sp_simple = findViewById(R.id.sp_icon);
        sp_simple.setAdapter(simpleAdapter);
        sp_simple.setSelection(0);
        sp_simple.setOnItemSelectedListener(this);

        // 基本适配器使用
        sp_base = findViewById(R.id.sp_base);
        // 获取默认列表
        planetList = Planet.getDefaultList();
        // 构建一个列表适配器
        PlanetBaseAdapter planetBaseAdapter = new PlanetBaseAdapter(this, planetList);
        sp_base.setAdapter(planetBaseAdapter);
        sp_base.setSelection(0);
        sp_base.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.sp_dropdown){
            ToastUtil.show(this, "您选择的是" + starArray[position]);
        } else if(parent.getId() == R.id.sp_dialog){
            ToastUtil.show(this, "您选择的是" + starArray[position]);
        } else if(parent.getId() == R.id.sp_icon){
            ToastUtil.show(this, "您选择的是" + starArray[position]);
        } else if(parent.getId() == R.id.sp_base){
            ToastUtil.show(this, "您选择的是" + planetList.get(position).name);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



