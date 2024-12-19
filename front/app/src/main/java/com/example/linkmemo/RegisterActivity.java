package com.example.linkmemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.linkmemo.data.network.NetworkResponseNew;


public class RegisterActivity extends Activity implements View.OnClickListener {

    EditText userName, password, rePassword, nickName;

    private RegisterActivityViewModel viewModel = new RegisterActivityViewModel();

    TextView submit,top_title;

    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.register_input_username);
        password = findViewById(R.id.register_input_password);
        rePassword = findViewById(R.id.register_reinput_password);
        nickName = findViewById(R.id.register_input_nickname);
        submit = findViewById(R.id.register_btn_submit);
        top_title = findViewById(R.id.top_title);
        top_title.setText("注册");

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        submit.setOnClickListener(view -> {

            if (TextUtils.isEmpty(userName.getText().toString())) {
                Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password.getText().toString())) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(nickName.getText().toString())) {
                Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.getText().toString().equals(rePassword.getText().toString())) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            NetworkResponseNew<String> response = viewModel.register(userName.getText().toString(), password.getText().toString(), nickName.getText().toString());
            if (response != null && response.getCode() == 200 && TextUtils.isEmpty(response.getMessage())) {
                Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                finish();
            } else if (response != null && response.getCode() == 200 && !TextUtils.isEmpty(response.getMessage())) {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show();
            }

        });

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }
    }
}
