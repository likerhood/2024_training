package com.example.week3test.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.week3test.dao.BookDao;
import com.example.week3test.enity.BookInfo;

// entities 表示该数据库多少表格，version表示数据库版本号
// exportSchema表示是否到处数据库信息json，建议为false，true好需要指定json，这是为了方便调试
@Database(entities = {BookInfo.class}, version = 1, exportSchema = false)
public abstract class BookDatebase extends RoomDatabase {
    // 获取持久化数据
    public abstract BookDao bookDao();

}
