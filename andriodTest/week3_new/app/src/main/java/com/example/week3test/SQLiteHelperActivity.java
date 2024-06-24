package com.example.week3test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.week3test.database.UserDBHelper;
import com.example.week3test.enity.User;
import com.example.week3test.utils.ToastUtil;

import java.util.List;

public class SQLiteHelperActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name, et_age, et_height, et_weight;
    private CheckBox ck_married;
    private UserDBHelper mHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_helper);
        et_name = findViewById(R.id.sqltest_name);
        et_age = findViewById(R.id.sqltest_age);
        et_height = findViewById(R.id.sqltest_height);
        et_weight = findViewById(R.id.sqltest_weight);
        ck_married = findViewById(R.id.sqltest_married);

        // 按钮监听
        findViewById(R.id.sqltest_add).setOnClickListener(this);
        findViewById(R.id.sqltest_delete).setOnClickListener(this);
        findViewById(R.id.sqltest_query).setOnClickListener(this);
        findViewById(R.id.sqltest_update).setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助实例
        mHelper = UserDBHelper.getInstance(this);
        // 打开读写链接
        mHelper.openReadLink();
        mHelper.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭数据库链接
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        // 获得编辑框的内容
        String name, age, height, weight;
        name = et_name.getText().toString();
        age = et_age.getText().toString();
        height = et_height.getText().toString();
        weight = et_weight.getText().toString();

        User user = null;

        if (v.getId() == R.id.sqltest_add){
            user = new User(name,
                    Integer.parseInt(age),
                    Long.parseLong(height),
                    Float.parseFloat(weight),
                    ck_married.isChecked());

            if (mHelper.insert(user) > 0){
                ToastUtil.show(this, "添加成功");
            }

        }else if(v.getId() == R.id.sqltest_delete){
            if (mHelper.deleteByName(name) > 0){
                ToastUtil.show(this, "删除成功");
            }
        } else if (v.getId() == R.id.sqltest_query) {
            // 查询所有
            // List<User> list = mHelper.queryAll();
            List<User> list = mHelper.queryByName(name);
            for(User u : list){
                Log.d("ning",u.toString());
            }


        } else if (v.getId() == R.id.sqltest_update) {
            user = new User(name,
                    Integer.parseInt(age),
                    Long.parseLong(height),
                    Float.parseFloat(weight),
                    ck_married.isChecked());

            if (mHelper.update(user) > 0){
                ToastUtil.show(this, "修改成功");
            }
        }


    }
}