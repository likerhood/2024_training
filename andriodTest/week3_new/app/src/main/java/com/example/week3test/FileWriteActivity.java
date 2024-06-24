package com.example.week3test;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.week3test.utils.FileUtil;
import com.example.week3test.utils.ToastUtil;

import java.io.File;

public class FileWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name, et_age, et_height, et_weight;
    private CheckBox ck_married;
    private TextView tv_txt;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_write);
        et_name = findViewById(R.id.filetest_name);
        et_age = findViewById(R.id.filetest_age);
        et_height = findViewById(R.id.filetest_height);
        et_weight = findViewById(R.id.filetest_weight);
        ck_married = findViewById(R.id.filetest_married);
        tv_txt = findViewById(R.id.filetest_tv_show);

        findViewById(R.id.file_write_save).setOnClickListener(this);
        findViewById(R.id.file_write_read).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // 获得编辑框的内容
        String name, age, height, weight;
        name = et_name.getText().toString();
        age = et_age.getText().toString();
        height = et_height.getText().toString();
        weight = et_weight.getText().toString();


        if (v.getId() == R.id.file_write_save){
            // 拼接一段文本
            StringBuilder sb = new StringBuilder();
            sb.append("姓名：").append(name);
            sb.append("\n年龄：").append(age);
            sb.append("\n身高：").append(height);
            sb.append("\n体重：").append(weight);
            sb.append("\n婚否：").append(ck_married.isChecked() ? "是" : "否");

            // System.currentTimeMillis() 返回当前时间的毫秒数，用作文件名，确保文件名唯一性。
            // getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) 是 Android 提供的方法，用于获取应用的外部文件目录，指定了文件存储的目录为 Downloads。
            String fileName = System.currentTimeMillis() + ".txt";
            String directory = null;
            // 外部存储私有空间
            directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
            // 外部存储公共空间
            directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            // 内部存储空间,data目录下面
            directory = getFilesDir().toString();

            path = directory + File.separatorChar + fileName;
            // 日志输出
            Log.d("ning", path);
            FileUtil.saveText(path, sb.toString());
            ToastUtil.show(this, "保存成功");

        }else if(v.getId() == R.id.file_write_read){
            tv_txt.setText(FileUtil.openText(path));
        }
    }
}