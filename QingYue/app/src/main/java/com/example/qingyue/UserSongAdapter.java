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

import com.example.qingyue.entity.Song;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;

import java.util.ArrayList;

public class UserSongAdapter extends BaseAdapter {

    ArrayList<Song> sData = new ArrayList<>();
    private Context mContext;
    private static final String TestApp="TestApp";

    public UserSongAdapter(ArrayList<Song> sData, Context mContext) {
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

        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_like_song,null);

        ImageView txt_pic = (ImageView) convertView.findViewById(R.id.song_picture);
        TextView txt_name = (TextView) convertView.findViewById(R.id.song_name);
        TextView txt_singer = (TextView) convertView.findViewById(R.id.singer_name);

        txt_pic.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(sData.get(position).getPicString()));
        txt_name.setText(sData.get(position).getName());
        txt_singer.setText(sData.get(position).getSinger());

        return convertView;
    }
}
