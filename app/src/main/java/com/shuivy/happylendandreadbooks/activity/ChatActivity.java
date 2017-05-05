package com.shuivy.happylendandreadbooks.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.adapter.ChatListAdapter;
import com.shuivy.happylendandreadbooks.models.MessageList;
import com.shuivy.happylendandreadbooks.models.MessageListInfo;
import com.shuivy.happylendandreadbooks.models.MyMessage;
import com.shuivy.happylendandreadbooks.models.MyUser;
import com.shuivy.happylendandreadbooks.util.ChatListBuilder;
import com.shuivy.happylendandreadbooks.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by stk on 2016/8/7 0007.
 */
public class ChatActivity extends AppCompatActivity {
    EditText inputEditText;
    ImageView sendImageView;
    TextView guestNameTextView;
    TextView titleTextView;
    List<MessageListInfo> chatMessages = new ArrayList<>();
    String guestName = MyUser.getCurrentUser(MyUser.class).getUsername();
    String guestToName;
    ChatListAdapter chatListAdapter;
    ListView chatListView;
    private String messageId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        messageId = intent.getStringExtra("messageId");
        guestToName = intent.getStringExtra("toName");

//        ChatListBuilder messageListBuilder = new ChatListBuilder(this);
//        chatMessages = (ArrayList<MyMessage>) messageListBuilder.getMessages(guestCode);
        if("".equals(messageId)){
            chatMessages=null;
        }else{
            BmobQuery<MessageList> query = new BmobQuery<MessageList>();
            query.addWhereEqualTo("messageId", messageId);
            query.findObjects(new FindListener<MessageList>() {
                @Override
                public void done(List<MessageList> list, BmobException e) {
                    if(e==null){
                        if(list!=null) {
                            for (int i = 0; i < list.size(); i++) {
                                MessageList messagelist = list.get(i);
                                MessageListInfo mf = new MessageListInfo();
                                mf.setContent(messagelist.getContent());
                                mf.setCreatedAt(messagelist.getCreatedAt());
                                mf.setUserName(messagelist.getUserName());
                                chatMessages.add(mf);
                            }
                        }else{
                            chatMessages=null;
                        }
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }

                }

            });
        }
        chatListView = (ListView) findViewById(R.id.chat_list_view);
        Log.i("信息",guestName);
        Log.i("接收",guestToName);
        chatListAdapter = new ChatListAdapter(chatMessages, this,guestName,guestToName);
        chatListView.setAdapter(chatListAdapter);



        titleTextView = (TextView) findViewById(R.id.chat_header_text);
        titleTextView.setText("聊天信息");
//        titleTextView.setText(guestName);


        inputEditText = (EditText) findViewById(R.id.input_edit_text);
        sendImageView = (ImageView) findViewById(R.id.send_image_view);

        final EditText input_edit_text = (EditText) findViewById(R.id.input_edit_text);
        input_edit_text.setFocusable(true);
        input_edit_text.setFocusableInTouchMode(true);
        input_edit_text.requestFocus();
        ImageView biaoqing = (ImageView) findViewById(R.id.biaoqing);
        biaoqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(getApplicationContext(), "表情功能后续添加哦~");
            }
        });

        sendImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String guestCode = muser.getObjectId();
//                String guestName = muser.getUsername();
                String text = inputEditText.getText().toString();

                //message.setType(0)
                if (text.trim().length() == 0)
                    return;
                update(messageId,text);

//                message.setDate(new Date().getTime());
//                chatMessages.add(message);
//                if (message.save(ChatActivity.this)) {
//                    if (chatListAdapter != null)
//                        chatListAdapter.notifyDataSetChanged();
//                    inputEditText.setText("");
//                    setResult(1);
//                }

            }
        });

        findViewById(R.id.header_left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity.this.finish();
            }
        });


    }

    private void update(String objectId,String content) {
        Log.i("bmob messageId", objectId);
        MyMessage ms = new MyMessage();
        ms.setContent(content);
        ms.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "更新成功");
                } else {
                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        addMessageList(guestName, content, objectId);
    }

    private void addMessageList(final String userName, final String content11, String objId){
        final MessageList ml = new MessageList();
        ml.setUserName(userName);
        ml.setContent(content11);
        ml.setMessageId(objId);
        ml.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    MessageListInfo info = new MessageListInfo();
                    info.setUserName(userName);
                    info.setContent(content11);
                    info.setCreatedAt(new Date().toString());
                    chatMessages.add(info);
                    chatListAdapter.notifyDataSetChanged();
                    inputEditText.setText("");
                    setResult(1);

                    ToastUtil.showToast(ChatActivity.this,"创建数据成功");

                    //toast("创建数据成功：" + objectId);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
