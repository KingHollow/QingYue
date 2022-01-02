package com.example.qingyue;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qingyue.entity.Comment;
import com.example.qingyue.entity.Singer;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;

import java.util.ArrayList;

public class CommentAdapter  extends BaseAdapter {

    private static final String TestApp="TestApp";
    ArrayList<Comment> sData = new ArrayList<Comment>();
    private Context mContext;

    public CommentAdapter(ArrayList<Comment> sData, Context mContext) {
        this.sData = sData;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return sData.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_post_comment,null);

        ImageView txt_pic = (ImageView) convertView.findViewById(R.id.head_picture);
        TextView txt_name = (TextView) convertView.findViewById(R.id.user_name);
        TextView txt_time = (TextView) convertView.findViewById(R.id.time);
        TextView txt_comment = (TextView) convertView.findViewById(R.id.content);
        TextView txt_id = (TextView) convertView.findViewById(R.id.comment_id);


        txt_pic.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(sData.get(position).getPicString()));
        txt_name.setText(sData.get(position).getName());
        txt_comment.setText(sData.get(position).getComment());
        txt_time.setText(sData.get(position).getTime());
        txt_id.setText(String.valueOf(sData.get(position).getID()));


        return convertView;
    }
}
