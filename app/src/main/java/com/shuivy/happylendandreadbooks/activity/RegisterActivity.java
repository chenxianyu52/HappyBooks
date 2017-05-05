package com.shuivy.happylendandreadbooks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.models.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名：HappyBooks
 * 包名：com.shuivy.happylendandreadbooks.activity
 * 文件名：RegisterActivity
 * 创建者：陈贤煜
 * 创建时间：2017/4/13 22:05
 * 描述：TODO
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText p_username;
    private EditText p_password1;
    private EditText p_password2;
    private Button p_register;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        init();
    }

    private void init() {
        p_username = (EditText) findViewById(R.id.p_username);
        p_password1= (EditText) findViewById(R.id.p_password1);
        p_password2 = (EditText) findViewById(R.id.p_password2);
        p_register = (Button) findViewById(R.id.p_register);
        p_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.p_register:
                String username = p_username.getText().toString().trim();
                String password1 = p_password1.getText().toString().trim();
                String password2 = p_password2.getText().toString().trim();
                if(!TextUtils.isEmpty(username)&!TextUtils.isEmpty(password1)
                        &!TextUtils.isEmpty(password2)){
                    if(password1.equals(password2)){
                        MyUser myuser = new MyUser();
                        myuser.setUsername(username);
                        myuser.setPassword(password1);
                        myuser.signUp(new SaveListener<MyUser>() {

                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this,"注册失败"+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this,"两次密码不一致",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this,"输入不能为空",Toast.LENGTH_LONG).show();
                }
        }
    }
}
