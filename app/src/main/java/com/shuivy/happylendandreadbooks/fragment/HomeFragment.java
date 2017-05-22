package com.shuivy.happylendandreadbooks.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.activity.BookDetailActivity;
import com.shuivy.happylendandreadbooks.activity.BookMainActivity;
import com.shuivy.happylendandreadbooks.activity.BookSearchActivity;
import com.shuivy.happylendandreadbooks.adapter.BookListAdapter;
import com.shuivy.happylendandreadbooks.adapter.MyViewPagerAdapter;
import com.shuivy.happylendandreadbooks.database.MyDataBaseHelper;
import com.shuivy.happylendandreadbooks.models.BookInfo;
import com.shuivy.happylendandreadbooks.models.BookInfoList;
import com.shuivy.happylendandreadbooks.models.BookInfoS;
import com.shuivy.happylendandreadbooks.util.BitmapToBase64;
import com.shuivy.happylendandreadbooks.util.ListUtility;
import com.shuivy.happylendandreadbooks.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by stk on 2016/7/22 0022.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    private View mRootView;
    private Activity mContext;
    private LayoutInflater mLayoutInflater;
    private int[] mImageIdArray;
    private static final int TIME = 2000;
    private List<View> mLayouts;
    private List<View> mDots;
    private ViewPager mViewPager;
    private int mCount = 3;
    private Handler mHandler = new Handler();
    private int itemPosition;
    private List<BookInfoList> allBooks = new ArrayList<>();
    //private ArrayList<BookInfo> allBooks;
    private BookListAdapter bookListAdapter;
    private ScrollView sv;
    private ImageView search;
    private static final int DETAILREQUEST = 1;
    private ListView listView;
    private ProgressDialog dialog;
    private TextView gengduo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        dialog = new ProgressDialog(mContext);
        dialog.setTitle("加载中");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_home, null);
            mLayoutInflater = inflater;
            initView();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }

        return mRootView;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        //allBooks = MyDataBaseHelper.getInstance(getActivity()).getAllBooks();
//        bookListAdapter.notifyDataSetChanged();
////        sv.smoothScrollTo(0,0);
//    }

    private void initView() {
        dialog.show();
        mImageIdArray = new int[]{
                R.mipmap.book_viewpager1,
                R.mipmap.book_viewpager2,
                R.mipmap.book_viewpager3,
        };
        initIndex();
        search = (ImageView)mRootView.findViewById(R.id.main_search_input);
        gengduo = (TextView) mRootView.findViewById(R.id.gengduo);
        sv = (ScrollView) mRootView.findViewById(R.id.home_sv);
        listView = (ListView) mRootView.findViewById(R.id.list_publish_book);
        //allBooks = MyDataBaseHelper.getInstance(getActivity()).getAllBooks();
//        bookListAdapter = new BookListAdapter(getActivity(), allBooks);
//        listView.setAdapter(bookListAdapter);
        BmobQuery<BookInfo> query= new BmobQuery<BookInfo>();
        query.order("-createDate");
        query.findObjects(new FindListener<BookInfo>() {
            @Override
            public void done(List<BookInfo> list, BmobException e) {
                if(e==null){

                    for(int i=0;i<list.size();i++){
                        BookInfo book = list.get(i);
                        BookInfoList booklist= new BookInfoList();
                        booklist.setTitle(book.getTitle());
                        booklist.setTime(book.getCreateDate());
                        booklist.setLocation(book.getLocation());
                        booklist.setPublishType(book.getPublishType());
                        booklist.setImg(new BitmapToBase64().base64ToBitmap(book.getImg()));
                        booklist.setObjectId(book.getObjectId());
                        allBooks.add(booklist);

                    }
                    //Toast.makeText(getActivity(),list.get(1).getTitle(),Toast.LENGTH_SHORT).show();

                    bookListAdapter =  new BookListAdapter(getActivity(),allBooks);
                    listView.setAdapter(bookListAdapter);

                    dialog.dismiss();
                }else{
                    Log.i("bmob","失败:"+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        ListUtility.setListViewHeightBasedOnChildren(listView);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BookSearchActivity.class);
                intent.putExtra("flag",0);
                getActivity().startActivity(intent);
            }
        });

        gengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),BookSearchActivity.class);
                intent.putExtra("flag",1);
                getActivity().startActivity(intent);

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookInfoList book = allBooks.get(i);
                Intent intent = new Intent();
                intent.putExtra("objectId",book.getObjectId());

              /*  BookInfoS book = new BookInfoS();
                book.setTitle(temp.getTitle());
                book.setDes(temp.getDes());
                book.setCreateDate(temp.getCreateDate());
                book.setPublishType(temp.getPublishType());
//                ToastUtil.showToast(getActivity().getApplicationContext(),book.getTitle());
                Intent intent = new Intent();
                intent.setClass(getActivity(),BookDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book",book);*/
                intent.setClass(getActivity(),BookDetailActivity.class);
                //intent.putExtra("title",book.getTitle());
                startActivityForResult(intent,DETAILREQUEST);

            }
        });
    }


    private void initIndex() {
        viewPager();//首页viewpager
        mHandler.postDelayed(runnableForViewPager, TIME);//viewpager轮播
        threeBody();//三体点击
    }

    /**
     * 首页上方的viewpager图片展示
     */
    private void viewPager() {
        ViewGroup viewGroup = (ViewGroup) mRootView.findViewById(R.id.viewGroup);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);
        mViewPager.setFocusable(true);// sv和lv冲突2
        mViewPager.setFocusableInTouchMode(true);
        mViewPager.requestFocus();
        //mCount是订单数量，是从订单处得到的数据，我们默认设为3

        mLayouts = new ArrayList<>();
        mDots = new ArrayList<>();
        for (int i = 0; i < mCount; i++) {
            //下面两句必须放在for里面
            View layoutView = mLayoutInflater.inflate(R.layout.viewpager_item, null);
            View dotView = mLayoutInflater.inflate(R.layout.dot, null);

            ImageView imageView = (ImageView) layoutView.findViewById(R.id.viewpager_image);
            imageView.setImageResource(mImageIdArray[i]);
            if (i == 0) {
                dotView.setBackgroundResource(R.drawable.dot_select);
            } else {
                dotView.setBackgroundResource(R.drawable.dot_no_select);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.setMargins(20, 0, 20, 0);
            viewGroup.addView(dotView, layoutParams);

            mLayouts.add(layoutView);
            mDots.add(dotView);

        }

        mViewPager.setAdapter(new MyViewPagerAdapter(mLayouts));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mCount; i++) {
                    if (i == position) {
                        mDots.get(i).setBackgroundResource(R.drawable.dot_select);
                    } else {
                        mDots.get(i).setBackgroundResource(R.drawable.dot_no_select);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * ViewPager的定时器
     */
    Runnable runnableForViewPager = new Runnable() {
        @Override
        public void run() {
            try {
                itemPosition++;
                mHandler.postDelayed(this, TIME);
                mViewPager.setCurrentItem(itemPosition % mCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 智能推荐三体点击事件
     */
    private void threeBody() {
        final RelativeLayout r_1 = (RelativeLayout) mRootView.findViewById(R.id.r_1);
        final RelativeLayout r_2 = (RelativeLayout) mRootView.findViewById(R.id.r_2);
        final RelativeLayout r_3 = (RelativeLayout) mRootView.findViewById(R.id.r_3);
//        final TextView t_1 = (TextView) mRootView.findViewById(R.id.t_1);
//        final TextView t_2 = (TextView) mRootView.findViewById(R.id.t_2);
//        final TextView t_3 = (TextView) mRootView.findViewById(R.id.t_3);

        View.OnTouchListener threeBody = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (view.getId()) {
                    case R.id.r_1: {
                        threeBodyOnTouchEvent(motionEvent, r_1);
                        break;
                    }
                    case R.id.r_2: {
                        threeBodyOnTouchEvent(motionEvent, r_2);
                        break;
                    }
                    case R.id.r_3: {
                        threeBodyOnTouchEvent(motionEvent, r_3);
                        break;
                    }

                }
                return true;
            }
        };

        r_1.setOnTouchListener(threeBody);
        r_2.setOnTouchListener(threeBody);
        r_3.setOnTouchListener(threeBody);

    }

    /**
     * 三体触摸动画事件
     */
    private void threeBodyOnTouchEvent(MotionEvent motionEvent, View view) {
        int duration = 150;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //按下时缩小
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.93f, 1.0f, 0.93f, Animation.RELATIVE_TO_SELF
                        , 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(duration);
                scaleAnimation.setFillAfter(true);
                view.startAnimation(scaleAnimation);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                //移出当前view即取消点击时恢复原样
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.93f, 1.0f, 0.93f, 1.0f, Animation.RELATIVE_TO_SELF
                        , 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(duration);
                scaleAnimation.setFillAfter(true);
                view.startAnimation(scaleAnimation);
                break;
            }

            case MotionEvent.ACTION_UP: {
                //松开时放大
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.93f, 1.0f, 0.93f, 1.0f, Animation.RELATIVE_TO_SELF
                        , 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(duration);
                scaleAnimation.setFillAfter(true);
                view.startAnimation(scaleAnimation);

                ToastUtil.showToast(mContext, "后续添加功能~");
                break;
            }
        }
    }

    /**
     * 计算
     */
    private void threeBodyInit(){

    }
}
