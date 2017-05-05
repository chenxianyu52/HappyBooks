package com.shuivy.happylendandreadbooks.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.activity.LoginActivity;
import com.shuivy.happylendandreadbooks.component.StkMenuLayout;
import com.shuivy.happylendandreadbooks.models.MyUser;
import com.shuivy.happylendandreadbooks.util.ToastUtil;
import com.shuivy.happylendandreadbooks.viewmodel.ListMenu;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by stk on 2016/7/22 0022.
 */
public class UserFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Button logOutButton;
    private List<ListMenu> listMenus = new ArrayList<ListMenu>();
    private View mRootView;
    private Activity mContext;
    private TextView user_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_user, container, false);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }

        return mRootView;
    }

    private void initView() {
        MyUser userinfo = BmobUser.getCurrentUser(MyUser.class);

        user_username = (TextView) mRootView.findViewById(R.id.user_username);
        user_username.setText(userinfo.getUsername());
        logOutButton = (Button) mRootView.findViewById(R.id.to_login_button);
        logOutButton.setOnClickListener(this);
        StkMenuLayout jifen = (StkMenuLayout) mRootView.findViewById(R.id.jifen);
        jifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "“我的积分”功能待后续完善~");
            }
        });
        StkMenuLayout fabu = (StkMenuLayout) mRootView.findViewById(R.id.fabu);
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "“我发布的”功能待后续完善~");
            }
        });
        StkMenuLayout jiechu = (StkMenuLayout) mRootView.findViewById(R.id.jiechu);
        jiechu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "“我借出的”功能待后续完善~");
            }
        });
        StkMenuLayout jiedao = (StkMenuLayout) mRootView.findViewById(R.id.jiedao);
        jiedao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "“我借到的”功能待后续完善~");
            }
        });
        StkMenuLayout zanguo = (StkMenuLayout) mRootView.findViewById(R.id.zanguo);
        zanguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "“我收藏的”功能待后续完善~");
            }
        });
        StkMenuLayout set = (StkMenuLayout) mRootView.findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(mContext, "“设置”功能待后续完善~");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_login_button:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }

}
