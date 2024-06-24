package com.example.myaccount;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myaccount.DBHelper.BillDBHelper;
import com.example.myaccount.Utils.DateUtil;
import com.example.myaccount.adapter.ListViewAdapter;
import com.example.myaccount.entity.IncomeInfo;
import com.example.myaccount.entity.ListItem;
import com.example.myaccount.entity.OutComeInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BillDetailActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView tv_tile;
    //    private RadioButton rb_outcome, rb_income;
    private RadioGroup rb_panel;
    private ImageView iv_billdetail, iv_back;
    private BillDBHelper mBillDBHelper;
    private ListView lv_bill_detail;

    // 账单数据
    private List<ListItem> itemList;
    private List<OutComeInfo> outcomList;
    private List<IncomeInfo> incomList;

    private Button btn_ordered_change;

    // 结余展示
    private TextView tv_month_outcome, tv_month_income;

    private ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_detail);

        // 初始化数据库
        mBillDBHelper = MyApplication.getInstance().mBillDBHelper;
        mBillDBHelper.openReadLink();
        mBillDBHelper.openWriteLink();

        // 初始化标题
        setTitle();


        // 跳转事件
        iv_billdetail.findViewById(R.id.iv_billdetail);
        iv_billdetail.setOnClickListener(this);
        btn_ordered_change = findViewById(R.id.btn_detail_ordered);
        btn_ordered_change.setOnClickListener(this);

        // 展示账单
        showInitBillDetail();

        // 计算月收入，月结余
        showAccount();

    }


    @Override
    protected void onResume() {
        super.onResume();
        showInitBillDetail();
        showAccount();
    }

    private void showAccount() {
        tv_month_outcome = findViewById(R.id.tv_month_outcome);
        tv_month_income = findViewById(R.id.tv_month_income);

        // 获取当前年份和月份
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        double totalIncome = 0;
        double totalOutcome = 0;

        // 计算本月的收入和支出
        for (IncomeInfo income : incomList) {
            if (income.mYear == currentYear && income.mMonth == currentMonth) {
                totalIncome += income.money;
            }
        }

        for (OutComeInfo outcome : outcomList) {
            if (outcome.mYear == currentYear && outcome.mMonth == currentMonth) {
                totalOutcome += outcome.money;
            }
        }

        // 计算结余
//        double balance = totalIncome - totalOutcome;

        // 显示结果
        tv_month_income.setText(String.format("本月收入: %.2f", totalIncome));
        tv_month_outcome.setText(String.format("本月支出: %.2f", totalOutcome));

    }


    private void setTitle() {
        // 找到变量
        tv_tile = findViewById(R.id.tv_title);
        iv_billdetail = findViewById(R.id.iv_billdetail);
        iv_back = findViewById(R.id.iv_back);
        rb_panel = findViewById(R.id.rb_panel);

        // 设置标题
        tv_tile.setText("账单详情");
        tv_tile.setVisibility(View.VISIBLE);
        rb_panel.setVisibility(View.GONE);
        iv_back.setImageResource(R.drawable.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_billdetail.setVisibility(View.GONE);

    }

    private void showInitBillDetail() {

        lv_bill_detail = findViewById(R.id.lv_billdetail);
        // 查询所有的支出和收入信息
        itemList = new ArrayList<>();
        outcomList = mBillDBHelper.queryAllOutcomeInfo();
        incomList = mBillDBHelper.queryAllIncomeInfo();
        // 支出
        for (OutComeInfo outcome : outcomList) {
            double money = outcome.money;
            String category = outcome.category;
            String desc = outcome.desc;
            int mYear = outcome.mYear;
            int mMonth = outcome.mMonth;
            int mDate = outcome.mDate;
            int id = outcome.id;

            int pic = MyApplication.getInstance().outcomeCategoryMap.get(category);
            String date = String.format("%d-%02d-%02d", mYear, mMonth, mDate);

            ListItem info = new ListItem(id, pic, category, date, desc, money);
            itemList.add(info);

        }

        // 收入
        for (IncomeInfo income : incomList) {
            double money = income.money;
            String category = income.category;
            String desc = income.desc;
            int mYear = income.mYear;
            int mMonth = income.mMonth;
            int mDate = income.mDate;
            int id = income.id;

            int pic = MyApplication.getInstance().incomeCategoryMap.get(category);
            String date = String.format("%d-%02d-%02d", mYear, mMonth, mDate);

            ListItem info = new ListItem(id, pic, category, date, desc, money);
            itemList.add(info);
        }

        Toast.makeText(this, String.valueOf(itemList.size()), Toast.LENGTH_SHORT).show();
        Log.d("ning", String.valueOf(itemList.size()));

//        Toast.makeText(this, String.valueOf(itemList.size()),Toast.LENGTH_SHORT).show();
//        Log.d("ning", String.valueOf(itemList.size()));
        // 调整ListView高度
        adapter = new ListViewAdapter(this, itemList);
        lv_bill_detail.setAdapter(adapter);
        lv_bill_detail.setOnItemClickListener(this);

        setListViewHeightBasedOnChildren(lv_bill_detail);

        // 设置长按删除事件
        lv_bill_detail.setOnItemLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.btn_detail_ordered) {
            String txt1 = "按日期";
            String txt2 = "按金额";
            String currentTxt = btn_ordered_change.getText().toString();
            if (currentTxt == txt1) {
                btn_ordered_change.setText(txt2);
                // 对 itemList 进行按金额从大到小排序
                Collections.sort(itemList, new Comparator<ListItem>() {
                    @Override
                    public int compare(ListItem o1, ListItem o2) {
                        return Double.compare(o2.account, o1.account);
                    }
                });

//                lv_bill_recent.removeAllViews();
                ListViewAdapter adapter = new ListViewAdapter(this, itemList);
                lv_bill_detail.setAdapter(adapter);
                lv_bill_detail.setOnItemClickListener(this);
                setListViewHeightBasedOnChildren(lv_bill_detail);


            } else {
                btn_ordered_change.setText(txt1);
                // 对 itemList 进行按日期从新到旧排序
                Collections.sort(itemList, new Comparator<ListItem>() {
                    @Override
                    public int compare(ListItem o1, ListItem o2) {
                        return o2.date.compareTo(o1.date);
                    }
                });
//                lv_bill_recent.removeAllViews();
                ListViewAdapter adapter = new ListViewAdapter(this, itemList);
                lv_bill_detail.setAdapter(adapter);
                lv_bill_detail.setOnItemClickListener(this);
                setListViewHeightBasedOnChildren(lv_bill_detail);
            }

        }
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ListItem info = itemList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否删除该记录?");
        builder.setPositiveButton("是", (dialog, which) -> {
            // 移除当前视图
            itemList.remove(position);
            // 将适配器设置为全局的，通知适配器数据发生改变
            adapter.notifyDataSetChanged();
            // 删除该商品
            deleteBillInfo(info);
        });
        builder.setNegativeButton("否", null);
        builder.create().show();
        return true;
    }

    private void deleteBillInfo(ListItem info) {
        String category = info.name;
        int id = info.id;
        boolean isIncome = MyApplication.getInstance().incomeCategoryMap.containsKey(category);
        if (isIncome) {
            mBillDBHelper.deleteIncomeInfoById(id);
        } else {
            mBillDBHelper.deleteOutcomeInfoById(id);
        }

    }

}