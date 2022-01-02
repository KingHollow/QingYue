package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qingyue.entity.Post;
import com.example.qingyue.entity.User;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class activity_aboutSinger extends Activity implements View.OnClickListener {

    String singer;
    String u_like;
    private TextView num_like;
    private TextView singer_name;
    private TextView info;
    private ImageView singer_picture;
    private ImageButton like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_singer);

        Bundle bundle = this.getIntent().getExtras();

        singer = bundle.getString("singer");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                activity_aboutSinger.this.setResult(RESULT_OK, intent);

                //关闭Activity
                activity_aboutSinger.this.finish();
            }
        });

        init();
    }

    private void init() {

        User n_user = (User) getApplication();

        num_like = (TextView) findViewById(R.id.num_like);
        singer_name = (TextView) findViewById(R.id.singer_name);
        info = (TextView) findViewById(R.id.info);
        singer_picture = (ImageView) findViewById(R.id.singer_picture);
        like = (ImageButton) findViewById(R.id.like);

        like.setOnClickListener(this);

        String data="";
        try {
            data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
                    "&singer=" + URLEncoder.encode(singer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String singerJson= PostUtil.doPost("aboutSinger",data);

        try {

            JSONObject jsonObject = new JSONObject(singerJson);

            singer_name.setText(jsonObject.getString("name"));
            if (jsonObject.getString("like").equals("yes")) {
                like.setImageResource(R.drawable.like_song);
                like.setTag(R.drawable.like_song);
                u_like = "yes";
            } else {
                like.setImageResource(R.drawable.unlike_song);
                like.setTag(R.drawable.unlike_song);
                u_like = "no";
            }
            info.setText(jsonObject.getString("info"));
            num_like.setText(jsonObject.getInt("num_like")+"人喜欢");

            singer_picture.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("pic")));


        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        //添加点击喜欢的事件&取消喜欢。。。。判断一下


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like:// 更改喜欢
                if (v.getTag().equals(R.drawable.like_song)) {
                    User n_user = (User) getApplication();
                    String data="";
                    try {
                        data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
                                "&singername=" + URLEncoder.encode(singer_name.getText().toString(), "UTF-8") +
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

                            like.setImageResource(R.drawable.unlike_song);
                            like.setTag(R.drawable.unlike_song);
                            //init();
                        } else {
                            Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
                        }

                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    User n_user = (User) getApplication();
                    String data="";
                    try {
                        data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8") +
                                "&singername=" + URLEncoder.encode(singer_name.getText().toString(), "UTF-8") +
                                "&like=yes";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String likeJson= PostUtil.doPost("chooseSongLike",data);

                    try {

                        JSONObject jsonObject = new JSONObject(likeJson);


                        String result = null;
                        result = jsonObject.getString("Result");

                        if (result.equals("success")) {
                            //
                            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();

                            like.setImageResource(R.drawable.like_song);
                            like.setTag(R.drawable.like_song);
                            //init();
                        } else {
                            Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
                        }

                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                }
                //showTypeDialog();
                break;

        }
    }

    //修改view

//    private void showTypeDialog() {
//        //显示对话框
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        final AlertDialog dialog = builder.create();
//        View view = View.inflate(this, R.layout.dialog_select_like_song, null);
//        TextView yes = (TextView) view.findViewById(R.id.yes);
//        TextView no = (TextView) view.findViewById(R.id.no);
//        yes.setOnClickListener(new View.OnClickListener() {// 在相册中选取
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//                //打开文件
//                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent1, 1);
//                dialog.dismiss();
//            }
//        });
//        no.setOnClickListener(new View.OnClickListener() {// 不修改
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.setView(view);
//        dialog.show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    init();
//                }break;
//
//            default:
//                break;
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

}