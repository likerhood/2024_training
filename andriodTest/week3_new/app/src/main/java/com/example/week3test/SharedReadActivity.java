package com.example.week3test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.week3test.R;

public class SharedReadActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name, et_age, et_height, et_weight; // 输入框
    private CheckBox ck_married;
    private SharedPreferences preferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_read);
        et_name = findViewById(R.id.text_name);
        et_age = findViewById(R.id.text_age);
        et_height = findViewById(R.id.text_height);
        et_weight = findViewById(R.id.text_weight);
        ck_married = findViewById(R.id.text_married);

        findViewById(R.id.text_saved).setOnClickListener(this);
        // 获取共享参数对象
        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        reload();
    }

    private void reload() {
        String name;
        name = preferences.getString("name", null);

        if(name != null){

            int age = preferences.getInt("age", 0);
            float weight = preferences.getFloat("weight", 0);
            float height = preferences.getFloat("height", 0);
            boolean isMarried = preferences.getBoolean("married", false);

            et_name.setText(name);
            et_age.setText(String.valueOf(age));
            et_height.setText(String.format("%.2f",height));
            et_weight.setText(String.format("%.2f",weight));
            ck_married.setChecked(isMarried);

        }
    }

    @Override
    public void onClick(View v) {
        String name, age, height, weight;
        name = et_name.getText().toString();
        age = et_age.getText().toString();
        height = et_height.getText().toString();
        weight = et_weight.getText().toString();

        // 将数据存放到共享参数中
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.putInt("age", Integer.parseInt(age));
        editor.putFloat("height", Float.parseFloat(height));
        editor.putFloat("weight", Float.parseFloat(weight));
        editor.putBoolean("married", ck_married.isChecked());
        // 提交
        editor.apply();
        Toast.makeText(this, "数据已经保存", Toast.LENGTH_SHORT).show();
    }
}