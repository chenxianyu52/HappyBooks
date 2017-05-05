package com.shuivy.happylendandreadbooks.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.models.MyMessage;
import com.shuivy.happylendandreadbooks.models.MyMessageInfo;
import com.shuivy.happylendandreadbooks.util.MessageListBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sqhan on 2016/7/28.
 */
public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyMessageInfo> messList = new ArrayList<>();
    private LayoutInflater inflater;
    //private MessageListBuilder messageListBuilder;
    private static final int COUNT = 10;


//    private HashMap<String, Integer> guestProtraits = new HashMap<>();
//
//    private String[] nickName = new String[]{
//            "爱书宝贝",
//            "小小书虫",
//            "读书如此简单",
//            "Hello Kitty",
//            "启动追一个梦",
//            "我是小猫咪",
//            "随风飞舞~",
//            "白马王子",
//            "校园时代",
//            "暗夜小精灵",
//    };
//    private String[] signature = new String[]{
//            "上海大学见面哦，见面聊~",
//            "你的书挺好看的，以后多多交流",
//            "很高兴见到你",
//            "^_^",
//            "你的书能便宜点吗",
//            "呀。",
//            "你好呀，朋友",
//            "hi，你好呀，我们的书换着看怎么样",
//            "书读百遍其义自见",
//            "哈哈",
//    };

    public MessageAdapter(Context mContext,List<MyMessageInfo> messList) {
        this.mContext = mContext;
//        guestProtraits.put("1", R.mipmap.share_person_1);
//        guestProtraits.put("2", R.mipmap.share_person_2);
//        guestProtraits.put("3", R.mipmap.share_person_3);
//        guestProtraits.put("4", R.mipmap.share_person_4);
//        guestProtraits.put("5", R.mipmap.share_person_5);
//        messageListBuilder = new MessageListBuilder(mContext);
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messList = messList;
    }

    @Override
    public int getCount() {
        return messList.size();
    }

    @Override
    public MyMessageInfo getItem(int i) {
        return messList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Log.d("position", i + "");

        ViewHolder holder;
        MyMessageInfo message = getItem(i);
//        Log.d("stkdebug_adapter", message.getGuestName());
//        Log.d("stkdebug_adapter", message.getContent());
        //Log.d("stkdebug_adapter", message.getDate() + "");
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_head_image);
            holder.nickNameTextView = (TextView) convertView.findViewById(R.id.list_item_nick_name);
            holder.latestChatTextView = (TextView) convertView.findViewById(R.id.list_item_latest_chat);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageResource(R.mipmap.share_person_1);
        holder.nickNameTextView.setText(message.getGuestToName());
        holder.latestChatTextView.setText(message.getContent());
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView nickNameTextView;
        TextView latestChatTextView;
    }

}
