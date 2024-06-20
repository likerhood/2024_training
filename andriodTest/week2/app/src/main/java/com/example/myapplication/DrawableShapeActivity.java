package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DrawableShapeActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View v_content;
    private TextView sw_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_shape);
        v_content = findViewById(R.id.v_content);
        findViewById(R.id.btn_rect).setOnClickListener(this);
        findViewById(R.id.btn_oval).setOnClickListener(this);
        // v_content 默认背景为圆角矩形
        v_content.setBackgroundResource(R.drawable.shape_rect_gold);

        // 设置checkbox的监听
        CheckBox ck_system = findViewById(R.id.ck_system);
        CheckBox ck_custom = findViewById(R.id.ck_custom);

        ck_system.setOnCheckedChangeListener(this);
        ck_custom.setOnCheckedChangeListener(this);

        sw_result = findViewById(R.id.sw_tvresult);
        Switch sw_status = findViewById(R.id.sw_status);
        sw_status.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_rect){
            v_content.setBackgroundResource(R.drawable.shape_rect_gold);
        } else if (v.getId() == R.id.btn_oval) {
            v_content.setBackgroundResource(R.drawable.shap_oval_rose);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.ck_system || buttonView.getId() == R.id.ck_custom){
            String desc = String.format("您%s了这个checkbox", isChecked ? "勾选" : "取消勾选");
            buttonView.setText(desc);
        } else if (buttonView.getId() == R.id.sw_status) {
            String desc = String.format("Switch按钮的状态是%s", isChecked ? "开" : "观");
            sw_result.setText(desc);
        }

    }
}