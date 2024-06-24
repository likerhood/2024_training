package com.example.myaccount.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myaccount.entity.IncomeInfo;
import com.example.myaccount.entity.OutComeInfo;

import java.util.ArrayList;
import java.util.List;

public class BillDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bill.db";
    //账单信息表
    private static final String TABLE_OUTCOME_INFO = "outCome_info";
    private static final String TABLE_INCOME_INFO = "inCome_info";
    private static final int DB_VERSION = 1;
    private static BillDBHelper mHelper = null;         // 单例模式
    // 数据库的读写需要基于锁来保证安全性，所以先创建两个实例
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;
    public BillDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static BillDBHelper getInstance(Context context){
        if (mHelper == null){
            mHelper =new BillDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink(){
        if (mRDB == null || !mRDB.isOpen()){
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink(){
        if (mWDB == null || !mWDB.isOpen()){
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink(){
        if (mRDB != null && !mRDB.isOpen()){
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && !mWDB.isOpen()){
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建支出信息表
        String sqlOutCome = "CREATE TABLE IF NOT EXISTS " + TABLE_OUTCOME_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " year INTEGER NOT NULL," +
                " month INTEGER NOT NULL," +
                " date INTEGER NOT NULL," +
                " category VARCHAR NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR);";
        db.execSQL(sqlOutCome);

        // 创建收入信息表
        String sqlInCome = "CREATE TABLE IF NOT EXISTS " + TABLE_INCOME_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " year INTEGER NOT NULL," +
                " month INTEGER NOT NULL," +
                " date INTEGER NOT NULL," +
                " category VARCHAR NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR);";
        db.execSQL(sqlInCome);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // 查询所有支出信息
    public List<OutComeInfo> queryAllOutcomeInfo(){

        String sql = "select * from " + TABLE_OUTCOME_INFO;
        List<OutComeInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()){
            OutComeInfo info = new OutComeInfo();
            info.id = cursor.getInt(0);
            info.mYear = cursor.getInt(1);
            info.mMonth = cursor.getInt(2);
            info.mDate = cursor.getInt(3);
            info.category = cursor.getString(4);
            info.money = cursor.getDouble(5);
            info.desc = cursor.getString(6);
            list.add(info);
        }
        cursor.close();
        return list;

    }

    // 查询所有收入信息
    public List<IncomeInfo> queryAllIncomeInfo(){

        String sql = "select * from " + TABLE_INCOME_INFO;
        List<IncomeInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()){
            IncomeInfo info = new IncomeInfo();
            info.id = cursor.getInt(0);
            info.mYear = cursor.getInt(1);
            info.mMonth = cursor.getInt(2);
            info.mDate = cursor.getInt(3);
            info.category = cursor.getString(4);
            info.money = cursor.getDouble(5);
            info.desc = cursor.getString(6);
            list.add(info);
        }
        cursor.close();
        return list;

    }


    // 插入支出信息
    public long insertOutcomeInfo(OutComeInfo info) {
        ContentValues values = new ContentValues();
        values.put("year", info.mYear);
        values.put("month", info.mMonth);
        values.put("date", info.mDate);
        values.put("category", info.category);
        values.put("amount", info.money);
        values.put("remark", info.desc);
        return mWDB.insert(TABLE_OUTCOME_INFO, null, values);
    }

    // 插入收入信息
    public long insertIncomeInfo(IncomeInfo info) {
        ContentValues values = new ContentValues();
        values.put("year", info.mYear);
        values.put("month", info.mMonth);
        values.put("date", info.mDate);
        values.put("category", info.category);
        values.put("amount", info.money);
        values.put("remark", info.desc);
        return mWDB.insert(TABLE_INCOME_INFO, null, values);
    }

    // 查询指定月份的收入和支出信息，并按金额从大到小排序
    public List<OutComeInfo> queryOutcomeInfoByMonth(int year, int month) {
        List<OutComeInfo> list = new ArrayList<>();

        // 查询指定月份的支出信息
        String sqlOutCome = "SELECT * FROM " + TABLE_OUTCOME_INFO +
                " WHERE year = ? AND month = ? ORDER BY amount DESC";
        Cursor cursorOutCome = mRDB.rawQuery(sqlOutCome, new String[]{String.valueOf(year), String.valueOf(month)});
        while (cursorOutCome.moveToNext()) {
            OutComeInfo info = new OutComeInfo();
            info.id = cursorOutCome.getInt(0);
            info.mYear = cursorOutCome.getInt(1);
            info.mMonth = cursorOutCome.getInt(2);
            info.mDate = cursorOutCome.getInt(3);
            info.category = cursorOutCome.getString(4);
            info.money = cursorOutCome.getDouble(5);
            info.desc = cursorOutCome.getString(6);
            list.add(info);
        }
        cursorOutCome.close();

        // 对列表进行排序
//        list.sort((o1, o2) -> {
//            double amount1 = o1 instanceof OutComeInfo ? ((OutComeInfo) o1).money : ((IncomeInfo) o1).money;
//            double amount2 = o2 instanceof OutComeInfo ? ((OutComeInfo) o2).money : ((IncomeInfo) o2).money;
//            return Double.compare(amount2, amount1); // 从大到小排序
//        });

        return list;
    }

    // 查询指定月份的收入和支出信息，并按金额从大到小排序
    public List<IncomeInfo> queryIncomeInfoByMonth(int year, int month) {
        List<IncomeInfo> list = new ArrayList<>();

        // 查询指定月份的支出信息
        String sql = "SELECT * FROM " + TABLE_INCOME_INFO +
                " WHERE year = ? AND month = ? ORDER BY amount DESC";
        Cursor cursor = mRDB.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month)});
        while (cursor.moveToNext()) {
            IncomeInfo info = new IncomeInfo();
            info.id = cursor.getInt(0);
            info.mYear = cursor.getInt(1);
            info.mMonth = cursor.getInt(2);
            info.mDate = cursor.getInt(3);
            info.category = cursor.getString(4);
            info.money = cursor.getDouble(5);
            info.desc = cursor.getString(6);
            list.add(info);
        }
        cursor.close();

        // 对列表进行排序
//        list.sort((o1, o2) -> {
//            double amount1 = o1 instanceof OutComeInfo ? ((OutComeInfo) o1).money : ((IncomeInfo) o1).money;
//            double amount2 = o2 instanceof OutComeInfo ? ((OutComeInfo) o2).money : ((IncomeInfo) o2).money;
//            return Double.compare(amount2, amount1); // 从大到小排序
//        });

        return list;
    }


    // 根据商品名字删除购物车信息
    public void deleteIncomeInfoById(int id) {
        mWDB.delete(TABLE_INCOME_INFO, "_id=?", new String[]{String.valueOf(id)});
    }


    public void deleteOutcomeInfoById(int id) {
        mWDB.delete(TABLE_OUTCOME_INFO, "_id=?", new String[]{String.valueOf(id)});

    }
}
