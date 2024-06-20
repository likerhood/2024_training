package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private DatePicker dp_date;
    private TextView tv_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        findViewById(R.id.btn_date_spinnerok).setOnClickListener(this);
        findViewById(R.id.btn_date_select).setOnClickListener(this);
        dp_date = findViewById(R.id.dp_date);
        tv_date = findViewById(R.id.tv_dateshow);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_date_spinnerok){
            String desc = String.format("你选择的日期是%s年%s月%s日",dp_date.getYear(), dp_date.getMonth() + 1, dp_date.getDayOfMonth());
            tv_date.setText(desc);
        }else if(v.getId() == R.id.btn_date_select){
            // 获取当前日期
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this, this, year,month,day);
            // 显示日期对话框
            dialog.show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc = String.format("你选择的日期是%s年%s月%s日",year, month + 1, dayOfMonth);
        tv_date.setText(desc);
    }
}