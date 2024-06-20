package com.example.week3test;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.week3test.database.BillDBHelper;
import com.example.week3test.utils.BillInfo;
import com.example.week3test.utils.DateUtil;
import com.example.week3test.utils.ToastUtil;

import java.util.Calendar;

public class BillAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView tv_date;
    private Calendar calendar;
    private EditText et_remark, et_amount;
    private RadioGroup rg_type;
    private BillDBHelper mDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);
        // 标题
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_title.setText("请填写账单");
        tv_option.setText("账单列表");

        // 显示日期
        calendar = Calendar.getInstance();
        tv_date = findViewById(R.id.tv_data);
        tv_date.setText(DateUtil.getDate(calendar));

        //弹出日期对话框事件
        tv_date.setOnClickListener(this);

        // 保存数据按钮
        rg_type = findViewById(R.id.rg_type);
        et_remark = findViewById(R.id.et_remark);
        et_amount = findViewById(R.id.et_count);
        // 保存按钮
        findViewById(R.id.btn_save).setOnClickListener(this);

        // 点击事件，跳转到添加账单界面
        tv_option.setOnClickListener(this);
        findViewById(R.id.iv_bakc).setOnClickListener(this);

        // 获得数据库帮助器实例
        mDBHelper = BillDBHelper.getInstance(this);
        mDBHelper.openReadLink();
        mDBHelper.openWriteLink();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_data){
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        } else if (v.getId() == R.id.btn_save) {
            // 保存订单信息
            BillInfo bill = new BillInfo();
            bill.date = tv_date.getText().toString();
            bill.type = rg_type.getCheckedRadioButtonId() == R.id.rb_income ?
                    BillInfo.BILL_TYPE_INCOME : BillInfo.BILL_TYPE_COST;
            bill.remark = et_remark.getText().toString();
            bill.amount = Double.parseDouble(et_amount.getText().toString());

            // 添加账单
            if (mDBHelper.save(bill) > 0){
                ToastUtil.show(this, "添加账单成功");
            }
        } else if (v.getId() == R.id.tv_option) {
            Intent intent = new Intent(this, BillPagerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (v.getId() == R.id.iv_bakc) {
            finish();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 设置文本显示
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_date.setText(DateUtil.getDate(calendar));
    }

    //@Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mDBHelper.closeLink();
//    }
}