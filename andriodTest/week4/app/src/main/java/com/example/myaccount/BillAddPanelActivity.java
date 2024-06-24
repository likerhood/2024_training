package com.example.myaccount;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.myaccount.DBHelper.BillDBHelper;
import com.example.myaccount.Utils.DateUtil;
import com.example.myaccount.adapter.ViewpagerAdapter;
import com.example.myaccount.entity.IncomeInfo;
import com.example.myaccount.entity.OutComeInfo;

import java.util.Calendar;

public class BillAddPanelActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener, ViewPager.OnPageChangeListener {

    // 标题数据
    private TextView tv_tile;
//    private RadioButton rb_outcome, rb_income;
    private RadioGroup rb_panel;
    private ImageView iv_billdetail, iv_back;
    private TextView showDateDialog;

    // 日历对象
    private Calendar calendar;

    // 数据库对象
    private BillDBHelper mBillDBHelper;

    // 翻页部分
     private ViewPager vp_content;
     // 分类数据
    // 收入
     private String[] mInComeCatogryName;
    // 图标
    private int[] mIncomePic;

    // 支出
    // 支出种类
    private String[] mOutComeCatogryName;
    // 图标
    private int[] mOutcomePic;


    // 编辑框
    private EditText editAmount, editRemarks;
    private Button btnSave;

    private View currenSelectCategory1, currenSelectCategory2;
    private String outcomeCategory,incomeCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add_panel);

        //数据库打开
        mBillDBHelper = MyApplication.getInstance().mBillDBHelper;
        mBillDBHelper.openReadLink();
        mBillDBHelper.openWriteLink();

        // 标题
        setTitle();

        // 跳转事件
        iv_billdetail.findViewById(R.id.iv_billdetail);
        iv_billdetail.setOnClickListener(this);
        // 添加账单
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        // 展示日期
        setDateButton();

        // 设置翻页panel
        setBillViewPanel();


    }

    private void setBillViewPanel() {
        // 单选按钮默认是支出
        rb_panel.check(R.id.radio_outcome); // 默认选中支出
        vp_content = findViewById(R.id.vp_content);

        // 获得数据
        mInComeCatogryName = IncomeInfo.mInComeCatogryName;
        mIncomePic = IncomeInfo.mIncomePic;
        mOutComeCatogryName = OutComeInfo.mOutComeCatogryName;
        mOutcomePic = OutComeInfo.mOutcomePic;

        // 设置viewpager
        ViewpagerAdapter adapter = new ViewpagerAdapter(this,mInComeCatogryName, mIncomePic, mOutComeCatogryName,mOutcomePic);
        vp_content.setAdapter(adapter);
        // 添加页面变更监听器
        vp_content.addOnPageChangeListener(this);

        currenSelectCategory1 = MyApplication.getInstance().currentSelectCategory1;
        currenSelectCategory2 = MyApplication.getInstance().currentSelectCategory2;

        outcomeCategory = MyApplication.getInstance().cate1;
        incomeCategory = MyApplication.getInstance().cate2;
//        Toast.makeText(this, "hhh"+outcomeCategory, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "hhh"+incomeCategory, Toast.LENGTH_SHORT).show();
    }

    private void setTitle() {
        // 找到变量
        tv_tile = findViewById(R.id.tv_title);
        iv_billdetail = findViewById(R.id.iv_billdetail);
        iv_back = findViewById(R.id.iv_back);
        rb_panel = findViewById(R.id.rb_panel);

        // 设置标题
        tv_tile.setVisibility(View.GONE);
        rb_panel.setVisibility(View.VISIBLE);
        iv_back.setImageResource(R.drawable.iv_back);
        iv_back.setVisibility(View.VISIBLE);

        // 监听事件
        iv_back.setOnClickListener(this);
        rb_panel.setOnCheckedChangeListener(this);

        // 初始化日期弹出框
        setDateButton();
    }

    private void setDateButton() {
        showDateDialog=findViewById(R.id.tv_date);
        // 显示日期
        calendar = Calendar.getInstance();
        showDateDialog.setText(DateUtil.getDate(calendar));
        // 日期对话框
        //弹出日期对话框事件
        showDateDialog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v.getId() == R.id.iv_billdetail){
            intent.setClass(BillAddPanelActivity.this, BillDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if(v.getId() == R.id.iv_back){
            finish();
        } else if(v.getId() == R.id.tv_date){
            DatePickerDialog dialog = new DatePickerDialog((Context) this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        } else if(v.getId() == R.id.btn_save){
            saveBillInfo();
        }
    }

    private void saveBillInfo() {
        editAmount = findViewById(R.id.edit_amount);
        editRemarks = findViewById(R.id.edit_remarks);

        // 获取用户输入的数据
        String amountStr = editAmount.getText().toString();
        String remarks = editRemarks.getText().toString();
        String dateStr = showDateDialog.getText().toString();

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }

        // 解析金额
        double amount = Double.parseDouble(amountStr);

        // 解析日期
        // 这里假设日期格式为 "yyyy-MM-dd"
        String[] dateParts = dateStr.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);


        // 如果是支出
        if(rb_panel.getCheckedRadioButtonId() == R.id.radio_outcome){
            addOutcomeInfo(amountStr,MyApplication.getInstance().cate1,remarks, year, month, day);
        }else if (rb_panel.getCheckedRadioButtonId() == R.id.radio_income){
            addIncomeInfo(amountStr,MyApplication.getInstance().cate1,remarks, year, month, day);
        }

    }

    private void addIncomeInfo(String amountStr, String category, String remarks, int year, int month, int day) {

        IncomeInfo  incomeInfo= new IncomeInfo(Double.parseDouble(amountStr),remarks,category, year, month, day);
        // 插入到数据库

        long result = mBillDBHelper.insertIncomeInfo(incomeInfo);
        // 根据插入结果显示提示信息
        if (result != -1) {
            Toast.makeText(this, "支出信息保存成功", Toast.LENGTH_SHORT).show();
            // 清空输入框
            editAmount.setText("");
            editRemarks.setText("");
            Log.d("ning", incomeInfo.toString());
        } else {
            Toast.makeText(this, "保存失败，请重试", Toast.LENGTH_SHORT).show();
        }

    }

    private void addOutcomeInfo(String amountStr, String category, String remarks, int year, int month, int day) {
        OutComeInfo outComeInfo = new OutComeInfo(Double.parseDouble(amountStr),remarks, category,year, month, day);
        // 插入到数据库
        long result = mBillDBHelper.insertOutcomeInfo(outComeInfo);
        // 根据插入结果显示提示信息
        if (result != -1) {
            Toast.makeText(this, "支出信息保存成功", Toast.LENGTH_SHORT).show();
            // 清空输入框
            editAmount.setText("");
            editRemarks.setText("");
            Log.d("ning", outComeInfo.toString());
        } else {
            Toast.makeText(this, "保存失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.radio_income) {
            vp_content.setCurrentItem(1); // 设置 ViewPager 显示收入页面
        } else if (checkedId == R.id.radio_outcome) {
            vp_content.setCurrentItem(0); // 设置 ViewPager 显示支出页面
        }

        updateBillCategoryPanel(checkedId);
    }

    private void updateBillCategoryPanel(int checkedId) {
        Toast.makeText(this,"这是"+String.valueOf(checkedId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 设置文本显示
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showDateDialog.setText(DateUtil.getDate(calendar));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //1收入0支出
        if (position == 0){
            rb_panel.check(R.id.radio_outcome); // 默认选中支出
        } else if (position == 1) {
            rb_panel.check(R.id.radio_income); // 默认选中支出
        }
        Toast.makeText(this, "支出类别"+String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库连接
        mBillDBHelper.closeLink();
    }


}