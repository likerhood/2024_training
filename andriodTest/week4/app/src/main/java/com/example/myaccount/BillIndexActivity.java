package com.example.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.myaccount.adapter.ListViewAdapter;
import com.example.myaccount.entity.IncomeInfo;
import com.example.myaccount.entity.ListItem;
import com.example.myaccount.entity.OutComeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BillIndexActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private BillDBHelper mBillDBHelper;
    // 标题数据
    private TextView tv_tile;
    //    private RadioButton rb_outcome, rb_income;
    private RadioGroup rb_panel;
    private ImageView iv_billdetail, iv_back;

    private ListView lv_bill_recent;

    // 账单数据
    private List<ListItem> itemList;
    private List<OutComeInfo> outcomList;
    private List<IncomeInfo> incomList;
    private Button btn_ordered_change;
    private ListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_index);
//        数据库打开
        mBillDBHelper = MyApplication.getInstance().mBillDBHelper;
        mBillDBHelper.openReadLink();
        mBillDBHelper.openWriteLink();

        findViewById(R.id.bill_record).setOnClickListener(this);
        btn_ordered_change = findViewById(R.id.btn_ordered_change);
        btn_ordered_change.setOnClickListener(this);

        setTitle();

        // 跳转事件
        iv_billdetail.findViewById(R.id.iv_billdetail);
        iv_billdetail.setOnClickListener(this);

        // 最近账单
        setRecentBill();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecentBill();
    }

    private void setRecentBill() {

        lv_bill_recent = findViewById(R.id.lv_bill_recent);
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
            String date = String.format("%d-%02d-%02d",mYear,mMonth, mDate);

            ListItem info = new ListItem(id, pic,category,date,desc,money);
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
            String date = String.format("%d-%02d-%02d",mYear,mMonth, mDate);

            ListItem info = new ListItem(id,pic,category,date,desc,money);
            itemList.add(info);
        }

//        Toast.makeText(this, String.valueOf(itemList.size()),Toast.LENGTH_SHORT).show();
//        Log.d("ning", String.valueOf(itemList.size()));

        TextView bill_recent_show = findViewById(R.id.tv_current_bill_show);

        if (itemList.size() != 0){
            lv_bill_recent.setVisibility(View.VISIBLE);
            bill_recent_show.setVisibility(View.GONE);
        } else {
            lv_bill_recent.setVisibility(View.GONE);
            bill_recent_show.setVisibility(View.VISIBLE);
            return;
        }

        // 对 itemList 进行按日期从新到旧排序
        Collections.sort(itemList, new Comparator<ListItem>() {
            @Override
            public int compare(ListItem o1, ListItem o2) {
                return o2.date.compareTo(o1.date);
            }
        });

        adapter = new ListViewAdapter(this,itemList);
        lv_bill_recent.setAdapter(adapter);
        lv_bill_recent.setOnItemClickListener(this);
        setListViewHeightBasedOnChildren(lv_bill_recent);

        // 设置长按删除事件
        lv_bill_recent.setOnItemLongClickListener(this);

    }

    private void setTitle() {
        // 找到变量
        tv_tile = findViewById(R.id.tv_title);
        iv_billdetail = findViewById(R.id.iv_billdetail);
        iv_back = findViewById(R.id.iv_back);
        rb_panel = findViewById(R.id.rb_panel);

        // 设置标题
        tv_tile.setText("小青账");
        tv_tile.setVisibility(View.VISIBLE);
        rb_panel.setVisibility(View.GONE);
        iv_back.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v.getId() == R.id.bill_record){
            intent.setClass(BillIndexActivity.this, BillAddPanelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if (v.getId() == R.id.iv_billdetail){
            intent.setClass(BillIndexActivity.this, BillDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_ordered_change){
            String txt1 = "按日期";
            String txt2 = "按金额";
            String currentTxt = btn_ordered_change.getText().toString();
            if(currentTxt == txt1){
                btn_ordered_change.setText(txt2);
                // 对 itemList 进行按金额从大到小排序
                Collections.sort(itemList, new Comparator<ListItem>() {
                    @Override
                    public int compare(ListItem o1, ListItem o2) {
                        return Double.compare(o2.account, o1.account);
                    }
                });

//                lv_bill_recent.removeAllViews();
                ListViewAdapter adapter = new ListViewAdapter(this,itemList);
                lv_bill_recent.setAdapter(adapter);
                lv_bill_recent.setOnItemClickListener(this);
                setListViewHeightBasedOnChildren(lv_bill_recent);


            }else {
                btn_ordered_change.setText(txt1);
                // 对 itemList 进行按日期从新到旧排序
                Collections.sort(itemList, new Comparator<ListItem>() {
                    @Override
                    public int compare(ListItem o1, ListItem o2) {
                        return o2.date.compareTo(o1.date);
                    }
                });
//                lv_bill_recent.removeAllViews();
                adapter = new ListViewAdapter(this,itemList);
                lv_bill_recent.setAdapter(adapter);
                lv_bill_recent.setOnItemClickListener(this);
                setListViewHeightBasedOnChildren(lv_bill_recent);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
    protected void onDestroy() {
        super.onDestroy();
        mBillDBHelper.closeLink();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ListItem info = itemList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(BillIndexActivity.this);
        builder.setMessage("是否删除该记录?");
        builder.setPositiveButton("是",(dialog, which) -> {
            // 移除当前视图
            itemList.remove(position);
            // 将适配器设置为全局的，通知适配器数据发生改变
            adapter.notifyDataSetChanged();
            // 删除该商品
            deleteBillInfo(info);
        });
        builder.setNegativeButton("否",null);
        builder.create().show();
        return true;
    }

    private void deleteBillInfo(ListItem info) {
        String category = info.name;
        int id = info.id;
        boolean isIncome = MyApplication.getInstance().incomeCategoryMap.containsKey(category);
        if (isIncome){
            mBillDBHelper.deleteIncomeInfoById(id);
        }else {
            mBillDBHelper.deleteOutcomeInfoById(id);
        }

    }
}