package com.example.week3test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.week3test.dao.BookDao;
import com.example.week3test.enity.BookInfo;
import com.example.week3test.utils.ToastUtil;

import java.util.List;

public class RoomWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name, et_author, et_press, et_price;
    private TextView rm_show;
    private BookDao bookDao;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_write);

        // 事件监听
        et_name = findViewById(R.id.roomtest_name);
        et_author = findViewById(R.id.roomtest_author);
        et_press = findViewById(R.id.roomtest_press);
        et_price = findViewById(R.id.roomtest_price);
        rm_show = findViewById(R.id.room_tv_show);

        // 按钮
        findViewById(R.id.roomtest_add).setOnClickListener(this);
        findViewById(R.id.roomtest_delete).setOnClickListener(this);
        findViewById(R.id.roomtest_update).setOnClickListener(this);
        findViewById(R.id.roomtest_query).setOnClickListener(this);

        // 获取数据库
        bookDao = MyApplication.getInstance().getBookDB().bookDao();

    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();

        if(v.getId() == R.id.roomtest_add){
            // 声明一个书籍类
            BookInfo b1 = new BookInfo();
            b1.setName(name);
            b1.setAuthor(author);
            b1.setPress(press);
            b1.setPrice(price);
            // 添加
            bookDao.insert(b1);
            ToastUtil.show(this,"添加成功");

        } else if (v.getId() == R.id.roomtest_delete) {
            BookInfo b = bookDao.queryByName(name);
            bookDao.delete(b);
            ToastUtil.show(this,"删除成功");

        }else if (v.getId() == R.id.roomtest_query) {
            List<BookInfo> list = bookDao.queryAll();
            StringBuilder sb = new StringBuilder();
            for (BookInfo b : list) {
                sb.append(b.toString());
            };
            rm_show.setText(sb.toString());

        }else if (v.getId() == R.id.roomtest_update) {

            BookInfo b = bookDao.queryByName(name);
            if(b == null){
                ToastUtil.show(this,"没有这个书籍");
                return;
            }
            // 声明一个书籍类
            BookInfo b1 = new BookInfo();
            b1.setId(b.getId());
            b1.setName(name);
            b1.setAuthor(author);
            b1.setPress(press);
            b1.setPrice(price);
            // 添加
            bookDao.update(b1);
            ToastUtil.show(this,"更新成功");
        }
    }
}