package com.shuivy.happylendandreadbooks.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.database.MyDataBaseHelper;
import com.shuivy.happylendandreadbooks.models.BookInfo;
import com.shuivy.happylendandreadbooks.models.BookInfoS;
import com.shuivy.happylendandreadbooks.models.MessageList;
import com.shuivy.happylendandreadbooks.models.MyMessage;
import com.shuivy.happylendandreadbooks.models.MyUser;
import com.shuivy.happylendandreadbooks.models.User;
import com.shuivy.happylendandreadbooks.util.BitmapToBase64;
import com.shuivy.happylendandreadbooks.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title;
    private TextView des;
    private TextView type;
    private TextView time;
    private ImageView img;
    private Button sub;
    private TextView username;
    private ImageView chat_message;
    private String userId;
    private String user_name;
    private String dateStr;
    private MyUser mUser= MyUser.getCurrentUser(MyUser.class);
    private String messageId1="";
    private String guestToName="";
    String userId1=mUser.getObjectId(); //客户端的用户id
    String userName1=mUser.getUsername();//
    private String messageId2="";
    private ProgressDialog dialog;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        dialog = new ProgressDialog(this);
        dialog.setTitle("加载中");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        initView();
    }

    public void initView(){
        dialog.show();

        img = (ImageView)findViewById(R.id.detail_imageView);
        title = (TextView) findViewById(R.id.detail_title);
        des = (TextView) findViewById(R.id.detail_description);
        type = (TextView) findViewById(R.id.detail_type);
        time = (TextView) findViewById(R.id.detail_time);
        sub = (Button)findViewById(R.id.detail_submit);
        chat_message = (ImageView) findViewById(R.id.message_image);
        chat_message.setOnClickListener(this);
        username = (TextView) findViewById(R.id.detail_username);
        final Intent intent = getIntent();
        String objectId = intent.getStringExtra("objectId");
//        handle=new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                Bundle b = msg.getData();
//                messageId2 = b.getString("messageId");
//
//
//            }
//        };




      //  BookInfoS book = (BookInfoS) intent.getSerializableExtra("book");
        //BookInfo book = MyDataBaseHelper.getInstance(this).getBook(titleStr);
        BmobQuery<BookInfo> query = new BmobQuery<BookInfo>();
        query.getObject(objectId, new QueryListener<BookInfo>() {

            @Override
            public void done(BookInfo book, BmobException e) {
                if(e==null){
                    long createDate = book.getCreateDate();
                    long curDate = new Date().getTime();
                    int secs = (int)(curDate-createDate)/1000;
                    int mins = secs/60;
                    int hours = mins/60;
                    int days = hours/24;
                    if(days>=1){
                        dateStr = days + "天前发布";
                    }else if(hours>=1){
                        dateStr = hours + "小时前发布";
                    }else{
                        dateStr = mins + "分钟前发布";
                    }
                    userId = book.getUserId(); //书的用户id
                    user_name=book.getUsername(); //书的用户名
                    title.setText(book.getTitle());
                    des.setText(book.getDes());
                    type.setText(book.getPublishType());
                    username.setText(user_name);
                    img.setImageBitmap(new BitmapToBase64().base64ToBitmap(book.getImg()));
                    time.setText(dateStr);
                    isInBmob(userId1,userId);
                    isInBmob(userId,userId1);
                    dialog.dismiss();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });





        //img.setImageBitmap(book.getImg());

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("status",100);
                setResult(RESULT_OK,intent);
                ToastUtil.showToast(getApplicationContext(),"已经通知该书友~");
                finish();
            }
        });
//        ToastUtil.showToast(getApplicationContext(),book.getTitle());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.message_image:
//                String userName1 = mUser.getUsername();
                if(!userId.equals(userId1)) {

                    //ToastUtil.showToast(this,messageId1);
                    if(!"".equals(messageId1)){
                        intent1 = new Intent(BookDetailActivity.this, ChatActivity.class);
                        intent1.putExtra("messageId",messageId1);
                        intent1.putExtra("toName",guestToName);
                        Log.i("toName",guestToName);
                        startActivity(intent1);
                        finish();



                    }else{
                        saveInMessage(userId1,userName1,userId,user_name);
//                        ToastUtil.showToast(BookDetailActivity.this,messageId2);
//                        intent1.putExtra("messageId",messageId2);
//                        intent1.putExtra("toName",user_name);
//
//
//                        ToastUtil.showToast(BookDetailActivity.this,messageId2);


                        //Log.i("messageId2",messageId2);
                    }

                }else {
                    ToastUtil.showToast(this,"不能和自己聊天");
                }
                break;

        }


    }
    public void saveInMessage(String userid1,String username1,String userid2,String username2){
        MyMessage ms = new MyMessage();
        ms.setGuestCode(userid1);
        ms.setContent("");
        ms.setGuestName(username1);
        ms.setGuestToCode(userid2);
        ms.setGuestToName(username2);
        ms.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){


//                                    Message ms= new Message();
//                                    Bundle b = new Bundle();
//                                    b.putString("messageId",s);
//                                    ms.setData(b);
//                                    handle.sendMessage(ms);
                    Intent intent2 = new Intent(BookDetailActivity.this,ChatActivity.class);
                    intent2.putExtra("messageId",s);
                    intent2.putExtra("toName",user_name);
                    startActivity(intent2);
                    finish();

                    //ToastUtil.showToast(BookDetailActivity.this,messageId2);
                }else{
                    ToastUtil.showToast(BookDetailActivity.this,"保存失败");
                }
            }




        });
    }
    public void  isInBmob(String userId1,String userId2){
        BmobQuery<MyMessage> query1 = new BmobQuery<MyMessage>();
        Log.i("userId1",userId1);
        Log.i("userId2",userId2);
        query1.addWhereEqualTo("guestCode", userId1);
        BmobQuery<MyMessage> query2 = new BmobQuery<MyMessage>();
        query2.addWhereEqualTo("guestToCode", userId2);
        List<BmobQuery<MyMessage>> andQuerys = new ArrayList<BmobQuery<MyMessage>>();
        andQuerys.add(query1);
        andQuerys.add(query2);
        BmobQuery<MyMessage> and = new BmobQuery<MyMessage>();
        and.and(andQuerys);
        and.findObjects(new FindListener<MyMessage>() {
            @Override
            public void done(List<MyMessage> object, BmobException e) {
                if(e==null){
                    Log.i("bomb 成功", String.valueOf(object.size()));
                    if (object.size()>=1&&"".equals(messageId1)) {
                        messageId1 =object.get(0).getObjectId();
                        guestToName=object.get(0).getGuestToName();
                    }
                    Log.i("bomb message", messageId1);
                    Log.i("bomb guestToName", guestToName);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }

            }

        });

    }


}


