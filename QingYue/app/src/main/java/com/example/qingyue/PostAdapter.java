package com.example.qingyue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qingyue.entity.Post;
import com.example.qingyue.entity.Singer;
import com.example.qingyue.entity.Song;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends BaseAdapter {

    List<Post> sData = new ArrayList<>();
    private Context mContext;



    public PostAdapter(List<Post> sData, Context mContext) {
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

        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_post,null);

        ImageView txt_headpic = (ImageView) convertView.findViewById(R.id.head_picture);
        TextView txt_postid = (TextView) convertView.findViewById(R.id.postid);
        TextView txt_poster = (TextView) convertView.findViewById(R.id.username);
        TextView txt_time = (TextView) convertView.findViewById(R.id.time);
        TextView txt_content = (TextView) convertView.findViewById(R.id.post_content);
        TextView txt_text = convertView.findViewById(R.id.type);

        TextView txt_repostnum = (TextView) convertView.findViewById(R.id.repost_num);
        TextView txt_commentnum = (TextView) convertView.findViewById(R.id.comment_num);
        TextView txt_likenum = (TextView) convertView.findViewById(R.id.like_num);

        View line = (View) convertView.findViewById(R.id.line);
        LinearLayout operation = (LinearLayout) convertView.findViewById(R.id.operation);


        //转发的原贴
        RelativeLayout o_post = (RelativeLayout) convertView.findViewById(R.id.o_post);
        TextView txt_o_id = (TextView) convertView.findViewById(R.id.o_postid);
        TextView o_poster = (TextView) convertView.findViewById(R.id.author_username);
        TextView o_content = (TextView) convertView.findViewById(R.id.original_content);

        //引用歌曲
        RelativeLayout song = (RelativeLayout) convertView.findViewById(R.id.song);
        ImageView song_picture = (ImageView) convertView.findViewById(R.id.song_picture);
        TextView song_name = (TextView) convertView.findViewById(R.id.song_name);
        TextView singer_name = (TextView) convertView.findViewById(R.id.singer_name);


        //引用歌手
        RelativeLayout singer = (RelativeLayout) convertView.findViewById(R.id.singer);
        ImageView singer_picture = (ImageView) convertView.findViewById(R.id.singer_picture);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        int type = sData.get(position).getType();

        txt_headpic.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(sData.get(position).getHeadpic()));
        int id = sData.get(position).getID();
        txt_postid.setText(String.valueOf(id));
        txt_poster.setText(sData.get(position).getPoster());
        txt_time.setText(sData.get(position).getTime());
        txt_content.setText(sData.get(position).getContent());
        txt_text.setText(String.valueOf(type));

        int r_n = sData.get(position).getReposts();
        int c_n = sData.get(position).getComments();
        int l_n = sData.get(position).getLikes();

        txt_repostnum.setText(String.valueOf(r_n));
        txt_commentnum.setText(String.valueOf(c_n));
        txt_likenum.setText(String.valueOf(l_n));


        if (type == 1) {
            song.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            operation.setVisibility(View.VISIBLE);
            song_picture.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(sData.get(position).getCardImg()));
            song_name.setText(sData.get(position).getCardTitle());
            singer_name.setText(sData.get(position).getCardContent());
        } else if (type == 2) {
            singer.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            operation.setVisibility(View.VISIBLE);
            singer_picture.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(sData.get(position).getCardImg()));
            name.setText(sData.get(position).getCardTitle());
        } else if (type == 3) {
            o_post.setVisibility(View.VISIBLE);
            int o_id = sData.get(position).get_o_ID();
            txt_o_id.setText(String.valueOf(o_id));
            o_poster.setText(sData.get(position).getRepostedName());
            o_content.setText(sData.get(position).getRepostedContent());
        } else {
            line.setVisibility(View.VISIBLE);
            operation.setVisibility(View.VISIBLE);
        }


        return convertView;
    }
}
