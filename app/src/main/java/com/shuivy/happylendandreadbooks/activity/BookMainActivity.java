package com.shuivy.happylendandreadbooks.activity;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.component.CustomViewPager;
import com.shuivy.happylendandreadbooks.fragment.HomeFragment;
import com.shuivy.happylendandreadbooks.fragment.MarketFragment;
import com.shuivy.happylendandreadbooks.fragment.MessageFragment;
import com.shuivy.happylendandreadbooks.fragment.UserFragment;
import com.shuivy.happylendandreadbooks.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanshenquan on 2016/7/2.
 */
public class BookMainActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog dialog;
    private static long EXIT_TIME = 0;
    private Context mContext;


    //定义FragmentManager对象
    //FragmentManager fragmentManager;

    private RelativeLayout home_item;
    private RelativeLayout find_item;
    private RelativeLayout message_item;
    private RelativeLayout user_item;

    private ImageView home_image;
    private ImageView find_image;
    private ImageView message_image;
    private ImageView user_image;
    private ImageView publish_image;
    private CustomViewPager mViewPager;
    private List<Fragment> mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_main);

        //fragmentManager = getFragmentManager();
        initData();
        initView();//初始化界面

    }

    private void initData() {


            mFragment =new ArrayList<>();
            mFragment.add(new HomeFragment());
            mFragment.add(new MarketFragment());
            mFragment.add(new MessageFragment());
            mFragment.add(new UserFragment());


    }

    /**
     * 初始化界面
     */
    private void initView() {
        mContext = getApplicationContext();
        home_image = (ImageView) findViewById(R.id.home_image);
        find_image = (ImageView) findViewById(R.id.find_image);
        message_image = (ImageView) findViewById(R.id.message_image);
        user_image = (ImageView) findViewById(R.id.user_image);

        home_item = (RelativeLayout) findViewById(R.id.home_item);
        find_item = (RelativeLayout) findViewById(R.id.find_item);
        message_item = (RelativeLayout) findViewById(R.id.message_item);
        user_item = (RelativeLayout) findViewById(R.id.user_item);
        publish_image = (ImageView) findViewById(R.id.publish_image);




        home_item.setOnClickListener(this);
        find_item.setOnClickListener(this);
        message_item.setOnClickListener(this);
        user_item.setOnClickListener(this);
        publish_image.setOnClickListener(this);
        mViewPager = (CustomViewPager) findViewById(R.id.mViewPager);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                if(position==2){
                    this.notifyDataSetChanged();
                }
                return mFragment.get(position);
            }
            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }


        });
        mViewPager.setPagingEnabled(false);
        mViewPager.setCurrentItem(0);
        //默认去进入时显示第一个页面
//        selectItem(0);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_item:
                home_image.setImageResource(R.mipmap.comui_tab_home_selected2);
                find_image.setImageResource(R.mipmap.comui_tab_find);
                message_image.setImageResource(R.mipmap.comui_tab_message);
                user_image.setImageResource(R.mipmap.comui_tab_person);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.find_item:

                find_image.setImageResource(R.mipmap.comui_tab_find_selected2);
                home_image.setImageResource(R.mipmap.comui_tab_home);
                message_image.setImageResource(R.mipmap.comui_tab_message);
                user_image.setImageResource(R.mipmap.comui_tab_person);
                mViewPager.setCurrentItem(1);

                //selectItem(1);
                break;
            case R.id.message_item:
                message_image.setImageResource(R.mipmap.comui_tab_message_selected2);
                home_image.setImageResource(R.mipmap.comui_tab_home);
                find_image.setImageResource(R.mipmap.comui_tab_find);
                user_image.setImageResource(R.mipmap.comui_tab_person);
                mViewPager.setCurrentItem(2);



                //selectItem(2);
                break;
            case R.id.user_item:
                user_image.setImageResource(R.mipmap.comui_tab_person_selected2);
                home_image.setImageResource(R.mipmap.comui_tab_home);
                find_image.setImageResource(R.mipmap.comui_tab_find);
                message_image.setImageResource(R.mipmap.comui_tab_message);
                mViewPager.setCurrentItem(3);
                //selectItem(3);
                break;
            case R.id.publish_image:
                Intent intent = new Intent(BookMainActivity.this, PublishActivity.class);
                    startActivity(intent);
                    //BookMainActivity.this.finish();
                //selectItem(4);
                break;
        }
    }

//    public void selectItem(int index) {
//        try {
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
////            unselectOthers();
//            switch (index) {
//                case 0:
//                    home_image.setImageResource(R.mipmap.comui_tab_home_selected2);
//                    homeFragment = new HomeFragment();
//                    transaction.replace(R.id.main_container, homeFragment);
//                    break;
//                case 1:
//                    find_image.setImageResource(R.mipmap.comui_tab_find_selected2);
//                    findFragment = new MarketFragment();
//                    transaction.replace(R.id.main_container, findFragment);
//                    break;
//                case 2:
//                    message_image.setImageResource(R.mipmap.comui_tab_message_selected2);
//                    messageFragment = new MessageFragment();
//                    transaction.replace(R.id.main_container, messageFragment);
//                    break;
//                case 3:
//                    user_image.setImageResource(R.mipmap.comui_tab_person_selected2);
//                    userFragment = new UserFragment();
//                    transaction.replace(R.id.main_container, userFragment);
//                    break;
//                case 4:
//                    Intent intent = new Intent(BookMainActivity.this, PublishActivity.class);
//                    startActivity(intent);
//                    //BookMainActivity.this.finish();
//                    break;
//            }
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void unselectOthers() {
//        home_image.setImageResource(R.mipmap.comui_tab_home);
//        find_image.setImageResource(R.mipmap.comui_tab_find);
//        message_image.setImageResource(R.mipmap.comui_tab_message);
//        user_image.setImageResource(R.mipmap.comui_tab_person);
//    }

    /**
     * 点击退出按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            //清除动画
            getWindow().getDecorView().clearAnimation();
            if ((System.currentTimeMillis() - EXIT_TIME) > 2000) {
                ToastUtil.showToast(mContext, "再按一次退出程序~");
                EXIT_TIME = System.currentTimeMillis();
            } else {
                BookMainActivity.this.finish();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
