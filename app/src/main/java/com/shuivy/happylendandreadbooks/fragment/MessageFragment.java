package com.shuivy.happylendandreadbooks.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.activity.ChatActivity;
import com.shuivy.happylendandreadbooks.adapter.MessageAdapter;
import com.shuivy.happylendandreadbooks.models.MyMessage;
import com.shuivy.happylendandreadbooks.models.MyMessageInfo;
import com.shuivy.happylendandreadbooks.models.MyUser;
import com.shuivy.happylendandreadbooks.util.MessageListBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by stk on 2016/7/22 0022.
 */
public class MessageFragment extends android.support.v4.app.Fragment {
    private View mRootView;
    private Activity mContext;
    private List<MyMessageInfo> messList ;;
    ListView messageListView;
    MessageAdapter messageAdapter;
    String userId = MyUser.getCurrentUser().getObjectId();
    String toName;

    @Override
    public void onStart() {
        super.onStart();
        messList= new ArrayList<MyMessageInfo>();
        messageListView = (ListView) mRootView.findViewById(R.id.message_list);
        String bql="select * from MyMessage where guestCode = ? or guestToCode = ? ";
        BmobQuery<MyMessage> query=new BmobQuery<MyMessage>();
        query.setPreparedParams(new Object[]{userId,userId});
        query.setSQL(bql);
        Log.i("smile", bql);
        query.doSQLQuery(new SQLQueryListener<MyMessage>(){

            @Override
            public void done(BmobQueryResult<MyMessage> result, BmobException e) {
                if(e ==null){
                    List<MyMessage> list = (List<MyMessage>) result.getResults();
                    if(list!=null && list.size()>0){
                        for(int i=0;i<list.size();i++){
                            MyMessage mm = list.get(i);
                            MyMessageInfo mi = new MyMessageInfo();
                            if(userId.equals(mm.getGuestCode())){
                                toName=mm.getGuestToName();
                                mi.setGuestToCode(mm.getGuestToCode());
                                mi.setGuestToName(toName);

                            }else{
                                toName = mm.getGuestName();
                                mi.setGuestToCode(mm.getGuestCode());
                                mi.setGuestToName(toName);
                            }
                            mi.setObjId(mm.getObjectId());
                            mi.setContent(mm.getContent());
                            messList.add(mi);

                        }
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                    messageAdapter = new MessageAdapter(mContext,messList);
                    messageListView.setAdapter(messageAdapter);
                    messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MyMessageInfo myinfo = messList.get(position);
                            Intent intent = new Intent(mContext, ChatActivity.class);
                            intent.putExtra("messageId", myinfo.getObjId());
                            intent.putExtra("toName",myinfo.getGuestToName());
                            Log.i("toName",myinfo.getGuestToName());
                            startActivityForResult(intent, 1);
                        }
                    });
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_message, container, false);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }

        return mRootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Log.d("stkdebug_result_code", requestCode + "");
            messageAdapter = new MessageAdapter(mContext,messList);
            messageListView.setAdapter(messageAdapter);
        }
    }

    private void initView() {
//        messageListBuilder = new MessageListBuilder(mContext);
//        messList = messageListBuilder.getMessages();






    }


}
