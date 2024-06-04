package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_panel;
//    定义几个操作数
//    第一个操作数
    private String firstNum = "";
//    运算符
    private String op = "";
//      第二个操作数
    private String secondNum = "";
//    当前计算结果
    private String result = "";
//    显示文本内容
    private String showText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        tv_panel = findViewById(R.id.tv_calculator_panel);

//        给每个按钮注册监听器
        findViewById(R.id.btn_calculator_cancel).setOnClickListener(this);
        findViewById(R.id.btn_calculator_multiply).setOnClickListener(this);
        findViewById(R.id.btn_calculator_clear).setOnClickListener(this);
        findViewById(R.id.btn_calculator_seven).setOnClickListener(this);
        findViewById(R.id.btn_calculator_eight).setOnClickListener(this);
        findViewById(R.id.btn_calculator_nine).setOnClickListener(this);
        findViewById(R.id.btn_calculator_plus).setOnClickListener(this);
        findViewById(R.id.btn_calculator_four).setOnClickListener(this);
        findViewById(R.id.btn_calculator_divide).setOnClickListener(this);
        findViewById(R.id.btn_calculator_five).setOnClickListener(this);
        findViewById(R.id.btn_calculator_six).setOnClickListener(this);
        findViewById(R.id.btn_calculator_minus).setOnClickListener(this);
        findViewById(R.id.btn_calculator_one).setOnClickListener(this);
        findViewById(R.id.btn_calculator_two).setOnClickListener(this);
        findViewById(R.id.btn_calculator_three).setOnClickListener(this);
        findViewById(R.id.btn_calculator_reciprocal).setOnClickListener(this);
        findViewById(R.id.btn_calculator_zero).setOnClickListener(this);
        findViewById(R.id.btn_calculator_dot).setOnClickListener(this);
        findViewById(R.id.btn_calculator_equal).setOnClickListener(this);
        findViewById(R.id.btn_calculator_sqrt).setOnClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String inputText;
        if(v.getId() == R.id.btn_calculator_sqrt){
            inputText = "√";
        }else{
            inputText = ((TextView) v).getText().toString();
        }

//        计算
        if(v.getId() == R.id.btn_calculator_clear){
            clear();
        }else if(v.getId() == R.id.btn_calculator_cancel){

        }else if(v.getId() == R.id.btn_calculator_divide || v.getId() == R.id.btn_calculator_plus || v.getId() == R.id.btn_calculator_minus || v.getId() == R.id.btn_calculator_multiply){
            // 加减乘除
            op = inputText;
            refreshText(showText + op);

        }else if(v.getId() == R.id.btn_calculator_equal){
            // 等号
            double calculate_result = calculateFour();  // 计算
            refreshOp(String.valueOf(calculate_result));    // 更新
            refreshText(showText + "=" + result);
        }else if(v.getId() == R.id.btn_calculator_sqrt){
            double sqrt_result = Math.sqrt(Double.parseDouble(firstNum));
            refreshOp(String.valueOf(sqrt_result));
            refreshText(showText + "√=" + result);
        }else if(v.getId() == R.id.btn_calculator_reciprocal){
            double ans = 1.0 / Double.parseDouble(firstNum);
            refreshOp(String.valueOf(ans));
            refreshText(showText + "/=" + result);
        }else{          // 其他非运算符
            // 清空上次结果
            if(result.length() > 0 && op.equals("")){
                clear();
            }
            //  无运算符
            if(op.equals("")){
                firstNum = firstNum + inputText;
            }else{
            //  由操作数
                secondNum = secondNum + inputText;
            }

            //  整数并不需要前导0
            if(showText.equals("0") && !inputText.equals(".")){
                refreshText(inputText);
            }else{
                refreshText(showText + inputText);
            }

        }

    }

//    刷新文本显示
    private void refreshText(String text){
        showText = text;
        tv_panel.setText(showText);
    }

//    刷新运算结果
    private void refreshOp(String new_result){
        result = new_result;
        firstNum = result;
        secondNum = "";
        op = "";
    }

//    清空
    private void clear(){
        refreshOp("");
        refreshText("0");
    }

//    加减乘除四则运算
    private double calculateFour(){
        double ans = 0;
        switch (op){
            case "+":
                ans = Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
                break;
            case "-":
                ans = Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
                break;
            case "x":
                ans = Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
                break;
            case "/":
                ans = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
                break;
        }
        return ans;
    }

}