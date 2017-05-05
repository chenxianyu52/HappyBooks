package com.shuivy.happylendandreadbooks.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.models.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by stk on 2016/7/24 0024.
 */
public class LoginActivity extends AppCompatActivity {
    private  ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        dialog.setTitle("登陆中");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.login);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userNameET = (EditText) findViewById(R.id.user_name);
                EditText passwordET = (EditText) findViewById(R.id.password);
                String userName = userNameET.getText().toString();
                String password = passwordET.getText().toString();


                if(!TextUtils.isEmpty(userName)&!TextUtils.isEmpty(password)){
                    dialog.show();
                    final MyUser user = new MyUser();
                    user.setUsername(userName);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {

                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if(e==null){
                                dialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, BookMainActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }else{
                                Toast.makeText(LoginActivity.this,"登录失败"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
