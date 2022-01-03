package com.example.qingyue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qingyue.entity.RcmdUser;
import com.example.qingyue.entity.Singer;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;

import java.util.ArrayList;
import java.util.List;

public class RcmdFriendAdapter extends BaseAdapter {
    List<RcmdUser> sData = new ArrayList<>();
    private Context mContext;

    public RcmdFriendAdapter(List<RcmdUser> sData, Context mContext){
        this.sData = sData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return sData.size();
    }

    @Override
    public Object getItem(int i) {
        return sData.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.list_friend_rcmd,null);

        ImageView iv_headpic = view.findViewById(R.id.head_picture);
        TextView tv_username = view.findViewById(R.id.username);
        TextView tv_similarity = view.findViewById(R.id.similarity);
        TextView tv_singerlike = view.findViewById(R.id.singer_like);
        TextView tv_labels = view.findViewById(R.id.labels);

        iv_headpic.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(sData.get(i).getHeadPicUrl()));
        tv_username.setText(sData.get(i).getName());
        tv_labels.setText(sData.get(i).getLabels());
        tv_similarity.setText(sData.get(i).getSimilarity());
        tv_singerlike.setText(sData.get(i).getSingerLike());

        return view;
    }
}
