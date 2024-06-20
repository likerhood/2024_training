package com.example.myapplication;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private TimePicker tp_time;
    private TextView tv_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        findViewById(R.id.btn_date_timeok).setOnClickListener(this);
        findViewById(R.id.btn_time_selector).setOnClickListener(this);
        tp_time = findViewById(R.id.dp_time);
        tp_time.setIs24HourView(true);
        tv_time = findViewById(R.id.tv_timeshow);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_date_timeok){
            String desc = String.format("您选择的时间是%d时%d分",tp_time.getHour(), tp_time.getMinute());
            tv_time.setText(desc);
        }else if (v.getId() == R.id.btn_time_selector){
            // 获取当前时间
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            // 构建时间对话框
            TimePickerDialog dialog = new TimePickerDialog(this,this, hour, minute, true);

            dialog.show();
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String desc = String.format("您选择的时间是%d时%d分",hourOfDay, minute);
        tv_time.setText(desc);
    }
}