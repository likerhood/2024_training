package com.example.myapplication;

import static androidx.appcompat.widget.ViewUtils.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Utils.ViewUtil;
import com.example.myapplication.database.LoginDBHelper;
import com.example.myapplication.enity.LoginInfo;

import java.util.Random;

public class LoginMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, View.OnFocusChangeListener {

    private TextView tv_password;
    private EditText et_password,et_phone;
    private Button btn_forget, btn_login;
    private CheckBox ck_remember;
    private RadioButton rb_password, rb_verifycode;
    private ActivityResultLauncher<Intent> register;
    // 默认密码
    private  String mPassword = "123456";
    private SharedPreferences preferences;
    // 验证码
    private String mVerifycode;
    // 数据库管理
    private LoginDBHelper mHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_login_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        // 在单选组件里面设置事件监听
        RadioGroup rb_login = findViewById(R.id.rb_login);
        // 设置监听器
        rb_login.setOnCheckedChangeListener(this);

        // 设置文本事件监听
        et_phone = findViewById(R.id.et_login_phone);
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));

        // 忘记密码和获取验证码事件监听
        btn_forget.setOnClickListener(this);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);

        // 忘记密码界面和登录界面之间的数据传递
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == LoginMainActivity.RESULT_OK){
                    mPassword = intent.getStringExtra("new_password");
                }
            }
        });

        // 登录按钮的事件监听
        btn_login = findViewById(R.id.btn_login_demo);
        btn_login.setOnClickListener(this);

        // 记住密码
        // preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // reload();

        // 密码编辑框事件监听
        et_password.setOnFocusChangeListener(this);

    }

    private void reload() {
//        boolean isChecked = preferences.getBoolean("isRemember", false);
//        if (isChecked){
//            String phone, password;
//            phone = preferences.getString("phone", "");
//            password = preferences.getString("password", "");
//            et_phone.setText(phone);
//            et_password.setText(password);
//            ck_remember.setChecked(isChecked);
//        }

        LoginInfo info = mHelper.queryTop();
        if(info != null && info.remember){
            et_phone.setText(info.phone);
            et_password.setText(info.password);
            ck_remember.setChecked(info.remember);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = LoginDBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();

        reload();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_password) {
            tv_password.setText(getString(R.string.login_password));
            et_password.setHint(getString(R.string.input_password));
            btn_forget.setText(getString(R.string.forget_password));
            ck_remember.setVisibility(TextView.VISIBLE);
        }else if (checkedId == R.id.rb_verifycode) {
            tv_password.setText(getString(R.string.verifycode));
            et_password.setHint(getString(R.string.get_verifycode));
            btn_forget.setText(getString(R.string.get_verifycode));
            ck_remember.setVisibility(TextView.GONE);
        }
    }

    // 按钮点击事件监听
    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if (phone.length() < 11){
            Toast.makeText(this, "请输入正确手机号",Toast.LENGTH_SHORT).show();
            return;
        }

        if(v.getId() == R.id.btn_forget){
            // 选择密码方式校验，此时点击按钮是忘记密码，所以点击之后跳转到找回密码页面
            if (rb_password.isChecked()){
                // 携带手机号码跳转到忘记密码页面
                Intent intent = new Intent(LoginMainActivity.this, LoginForgetActivity.class);
                intent.putExtra("phone", phone);
                register.launch(intent);
            } else if (rb_verifycode.isChecked()) {
                // 生成6位随机验证码
                mVerifycode = String.format("%06d", new Random().nextInt(999999));
                // 弹出提醒对话框，提示用户记住六位验证码
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号"+phone+",本次验证码是"+mVerifycode+"请输入验证码");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } else if (v.getId() == R.id.btn_login_demo) {
            // 密码登录界面
            if (rb_password.isChecked()){
                if (!mPassword.equals(et_password.getText().toString())){
                    Toast.makeText(this,"请输入正确密码",Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (ck_remember.isChecked()){
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("phone", et_phone.getText().toString());
//                    editor.putString("password", et_password.getText().toString());
//                    editor.putBoolean("isRemember", ck_remember.isChecked());
//                    editor.commit();
//                }

                Toast.makeText(this,"密码正确",Toast.LENGTH_SHORT).show();

                // 保存到数据库
                LoginInfo info = new LoginInfo();
                info.phone = et_phone.getText().toString();
                info.password = et_password.getText().toString();
                info.remember = ck_remember.isChecked();
                //Toast.makeText(this,info.toString(),Toast.LENGTH_SHORT).show();
                mHelper.save(info);

                // 登录成功
                LoginSuccess();
            } else if (rb_verifycode.isChecked()) {     // 验证码登录
                if (!mVerifycode.equals(et_password.getText().toString())){
                    Toast.makeText(this,"请输入正确验证码",Toast.LENGTH_SHORT).show();
                    return;
                }

                // 登录成功
                LoginSuccess();

            }
        }

    }

    // 登录成功，跳转到新页面
    private void LoginSuccess() {
        // 登录成功之后就不需要返回到登录界面了，所以需要设置启动模式
        Intent intent = new Intent(this, LoginSuccessActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("username", et_phone.getText().toString());
        startActivity(intent);
    }

    // 当密码输入框获取焦点之后，根据输入的号码，查询出对应的密码，自动填入
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v.getId() == R.id.et_password && hasFocus){
            LoginInfo info = mHelper.queryByPhone(et_phone.getText().toString());
            // 如果根据电话号码查出来了密码
            if(info != null){
                et_password.setText(info.password);
                ck_remember.setChecked(info.remember);
            }else{
                et_password.setText("");
                ck_remember.setChecked(false);
            }
        }
    }

    // 输入到达长度之后自动隐藏
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        public HideTextWatcher(EditText v, int maxLength) {
            mView = v;
            mMaxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == mMaxLength){
                ViewUtil.hideOneInputMethod(LoginMainActivity.this,mView);
            }

        }
    }
}