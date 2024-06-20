package com.example.myapplication;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RadioHorizontalActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView rb_tvresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_horizontal);
        RadioGroup rb_gender = findViewById(R.id.rg_gender);
        rb_tvresult = findViewById(R.id.rb_tvresult);
        rb_gender.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_male){
            rb_tvresult.setText("你是一个帅气的男孩");
        }else if( checkedId == R.id.rb_female){
            rb_tvresult.setText("你是一个可爱的女孩");
        }
    }
}