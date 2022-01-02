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

public class SongAdapter extends BaseAdapter {

    ArrayList<Song> sData = new ArrayList<>();
    private Context mContext;
    private static final String TestApp="TestApp";

    public SongAdapter(ArrayList<Song> sData, Context mContext) {
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
        ImageButton txt_like = (ImageButton) convertView.findViewById(R.id.song_like);

        Log.d(TestApp,sData.get(position).getName());
        Log.d(TestApp,sData.get(position).getPicString());
        Log.d(TestApp,ImgIOJsonOutputUtils.base64ToBitmap(sData.get(position).getPicString()).toString());

        txt_pic.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(sData.get(position).getPicString()));
        txt_name.setText(sData.get(position).getName());
        txt_singer.setText(sData.get(position).getSinger());
        //song里面加是否喜欢的标注
        //txt_like.setText(sData.get(position).getAddress());
        if (sData.get(position).getLike().equals("yes")) {
            txt_like.setBackgroundResource(R.drawable.like_song);
        } else {
            txt_like.setBackgroundResource(R.drawable.unlike_song);
        }

        return convertView;
    }

    //歌曲喜欢和歌手喜欢也要做到startforresesult，因为用户可能取消喜欢
}

