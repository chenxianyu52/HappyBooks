package com.shuivy.happylendandreadbooks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.activity.ChatActivity;
import com.shuivy.happylendandreadbooks.models.MessageList;
import com.shuivy.happylendandreadbooks.models.MessageListInfo;
import com.shuivy.happylendandreadbooks.models.MyMessage;
import com.shuivy.happylendandreadbooks.models.MyUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by madhur on 17/01/15.
 */
public class ChatListAdapter extends BaseAdapter {

    private List<MessageListInfo> messages;
    private String guestName;
    private Context mContext;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm");
    private LayoutInflater inflater;
    private String guestToName;

    public ChatListAdapter(List<MessageListInfo> chatMessages, Context context,String guestName,String guestToName) {
        this.guestName = guestName;
        this.messages = chatMessages;
        this.mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.guestToName = guestToName;

    }




    @Override
    public int getCount() {
        if(messages==null){
            return 0;
        }
        return messages.size();
    }

    @Override
    public MessageListInfo getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        ViewHolder holder;

        if (messages.get(position).getUserName().equals(guestName)) {
            if (convertView == null) {
                v = LayoutInflater.from(mContext).inflate(R.layout.item_chat_to, null, false);
                holder = new ViewHolder();

                holder.messageTextView = (TextView) v.findViewById(R.id.message_text);
                holder.timeTextView = (TextView) v.findViewById(R.id.time_text);

                v.setTag(holder);
            } else {
                v = convertView;
                holder = (ViewHolder) v.getTag();

            }

//            holder1.messageTextView.setText(Emoji.replaceEmoji(message.getMessageText(), holder1.messageTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(16)));
//            holder1.timeTextView.setText(SIMPLE_DATE_FORMAT.format(message.getMessageTime()));
            holder.messageTextView.setText(messages.get(position).getContent());
            holder.timeTextView.setText(messages.get(position).getCreatedAt());

        } else {

            if (convertView == null) {
                v = LayoutInflater.from(mContext).inflate(R.layout.item_chat_from, null, false);

                holder = new ViewHolder();

                holder.messageTextView = (TextView) v.findViewById(R.id.message_text);
                holder.timeTextView = (TextView) v.findViewById(R.id.time_text);
                holder.nameTextView = (TextView) v.findViewById(R.id.chat_guest_name);

                v.setTag(holder);

            } else {
                v = convertView;
                holder = (ViewHolder) v.getTag();

            }

            holder.messageTextView.setText(messages.get(position).getContent());
            holder.timeTextView.setText(messages.get(position).getCreatedAt());
            holder.nameTextView.setText(guestToName);
        }
        return v;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolder {
        public TextView messageTextView;
        public TextView timeTextView;
        public TextView nameTextView;
    }
}
