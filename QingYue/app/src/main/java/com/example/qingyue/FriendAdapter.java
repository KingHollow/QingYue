package com.example.qingyue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qingyue.entity.Friend;
import com.example.qingyue.entity.Post;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends BaseAdapter {
    List<Friend> sData = new ArrayList<>();
    private Context mContext;

    public FriendAdapter(List<Friend> sData, Context mContext) {
        this.sData = sData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return sData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.list_chat,null);

        ImageView iv_headshot = view.findViewById(R.id.head_picture);
        TextView tv_username = view.findViewById(R.id.username);
        TextView tv_conversation = view.findViewById(R.id.conversation);
        TextView tv_time = view.findViewById(R.id.time);

        iv_headshot.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(sData.get(i).getHeadshotUrl()));
        tv_username.setText(sData.get(i).getName());
        tv_conversation.setText(sData.get(i).getChatContent());
        tv_time.setText(sData.get(i).getLatestTime());

        return view;
    }
}
