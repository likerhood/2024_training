package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LifeCircle extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 打印
        Log.d(TAG, "lifeCircle onCreate");
        setContentView(R.layout.activity_life_circle);
        Button bt = findViewById(R.id.iv_back1);
        bt.setOnClickListener(this);

        Button toCalculator = findViewById(R.id.bnt_to_calculator);
        toCalculator.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v.getId() == R.id.bnt_to_calculator){
            intent.setClass(LifeCircle.this, CalculatorActivity.class);
            startActivity(intent);
        }else{
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打印
        Log.d(TAG, "lifeCircle onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 打印
        Log.d(TAG, "lifeCircle onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 打印
        Log.d(TAG, "lifeCircle onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        // 打印
        Log.d(TAG, "lifeCircle onStop");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 打印
        Log.d(TAG, "lifeCircle onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 打印
        Log.d(TAG, "lifeCircle onRestart");
    }
}