package com.example.linkmemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.linkmemo.data.bean.UserInfo;
import com.example.linkmemo.data.network.NetworkResponseNew;
import com.example.linkmemo.utils.SharedPreferencesUtil;
import com.google.gson.Gson;


public class LoginActivity extends Activity implements View.OnClickListener {

    private LoginActivityViewModel viewModel = new LoginActivityViewModel();

    private EditText input_userName, input_userPassword;

    private CheckBox checkBox_rememberInfo;

    TextView top_title;

    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView loginBtn = findViewById(R.id.login_btn_submit);
        TextView registerBtn = findViewById(R.id.login_btn_register);
        input_userName = findViewById(R.id.login_input_username);
        input_userPassword = findViewById(R.id.login_input_password);
        checkBox_rememberInfo = findViewById(R.id.login_checkbox_remember);
        top_title = findViewById(R.id.top_title);
        top_title.setText("登录");

        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.login_btn_submit) {

            String password = input_userPassword.getText().toString();
            String username = input_userName.getText().toString();

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "帐号不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            NetworkResponseNew<UserInfo> response = viewModel.login(username, password);
            if (response.getCode() == 200) {

                if (response.getData() == null) {
                    Toast.makeText(this, "帐号密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                App.userInfo = response.getData();

                Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
                // 保存密码
                SharedPreferencesUtil.put(getApplicationContext(), "user", new Gson().toJson(App.userInfo));
                finish();
            } else {
                Toast.makeText(this, "网络错误，请检查网络连接！", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.login_btn_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.back) {
            finish();
        }
    }
}
