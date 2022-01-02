package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qingyue.entity.Singer;
import com.example.qingyue.entity.Song;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class activity_LikeSinger extends Activity {

    ArrayList<Singer> singerdatalist = new ArrayList<Singer>();
    private ImageButton like;
    private TextView singer;
    RelativeLayout relativeLayout1,relativeLayout2,kid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_singer);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("like", "否");

                //设置返回数据
                activity_LikeSinger.this.setResult(RESULT_OK, intent);

                //关闭Activity
                activity_LikeSinger.this.finish();
            }
        });

        init();


    }

    private void init() {

        User n_user = (User) getApplication();

        if(singerdatalist != null){
            singerdatalist.clear();
        } else {
            singerdatalist = new ArrayList<Singer>();
        }
        String data="";
        try {
            data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String singerJson= PostUtil.doPost("singerLike",data);

        try {

            JSONArray jsonArray = new JSONArray(singerJson);

            for(int i = 0;i < jsonArray.length();i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Singer singer = new Singer();

                singer.setPicString(jsonObject.getString("picString"));
                singer.setName(jsonObject.getString("name"));
                singer.setLike(jsonObject.getString("like"));
                singerdatalist.add(singer);
            }

            ListView singerList = (ListView) findViewById(R.id.list_like_singer);

            SingerAdapter adapter = new SingerAdapter( singerdatalist, activity_LikeSinger.this);
            singerList.setAdapter(adapter);


        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void aboutSinger(View view) {

        singer = view.findViewById(R.id.singer_name);

        Intent intent = new Intent(getApplicationContext(),activity_aboutSinger.class);
        Bundle bundle = new Bundle();//该类用作携带数据
        bundle.putString("singer", singer.getText().toString());
        intent.putExtras(bundle);//附带上额外的数据
        startActivityForResult(intent,1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    this.init();
                }

                break;

            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Dislike(View view) {

        showTypeDialog(view);

    }

    private void showTypeDialog(View view) {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View vd = View.inflate(this, R.layout.dialog_select_dislike_song, null);
        TextView yes = (TextView) vd.findViewById(R.id.tv_yes);
        TextView no = (TextView) vd.findViewById(R.id.no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                relativeLayout2 =  (RelativeLayout)(view.getParent());
                like = relativeLayout2.findViewById(R.id.singer_like);
                singer = relativeLayout2.findViewById(R.id.singer_name);

                User n_user = (User) getApplication();
                String data="";
                try {
                    data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
                            "&singername=" + URLEncoder.encode(singer.getText().toString(), "UTF-8") +
                            "&like=no";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String likeJson= PostUtil.doPost("chooseSingerLike",data);

                try {

                    JSONObject jsonObject = new JSONObject(likeJson);


                    String result = null;
                    result = jsonObject.getString("Result");

                    if (result.equals("success")) {
                        //
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();

                        like.setBackgroundResource(R.drawable.unlike_song);
                        Intent intent = new Intent();

                        activity_LikeSinger.this.setResult(RESULT_OK, intent);
                        init();
                    } else {
                        Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
                    }

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {// 不修改
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(vd);
        dialog.show();
    }
}