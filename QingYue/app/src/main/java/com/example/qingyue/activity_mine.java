package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;
import com.example.qingyue.utils.ImgIOJsonOutputUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

public class activity_mine extends Activity implements View.OnClickListener {


    private ImageButton head_picture;
    private ImageButton infomodify;
    private ImageButton labelmodify;
    private Button checklist;

    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private TextView nickname;
    //判断一下，显示哪个性别
    private ImageView sex;
    private String u_sex = "男";
    private TextView region;
    private TextView signature;
    private TextView label_1;
    private TextView label_2;
    private TextView label_3;
    private static final String TestApp="TestApp";

    private RelativeLayout LL01,LL02,LL03;

    private TextView song_num;
    private TextView singer_num;
    private TextView post_num;
    private ImageView cover_song;
    private ImageView cover_singer;



//    Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
//        if (bt != null) {
//        @SuppressWarnings("deprecation")
//        Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
//        touxiang.setImageDrawable(drawable);
//    } else {
//        /**
//         * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
//         *
//         */
//    }

    List<String> u_Labels = new ArrayList<>();

    String now;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        init();
    }

    private void init() {

        User n_user = (User) getApplication();
        now = n_user.getUserName();

        head_picture = (ImageButton) findViewById(R.id.head_picture);
        infomodify = (ImageButton) findViewById(R.id.btn_edit);
        labelmodify = (ImageButton) findViewById(R.id.label_modify);

        nickname = findViewById(R.id.Nickname);
        //判断一下，显示哪个性别
        sex = findViewById(R.id.sex);
        region = findViewById(R.id.region);
        signature = findViewById(R.id.signature);
        label_1 = findViewById(R.id.label_1);
        label_2 = findViewById(R.id.label_2);
        label_3 = findViewById(R.id.label_3);

        LL01 = (RelativeLayout) findViewById(R.id.rel_song);
        LL02 = (RelativeLayout) findViewById(R.id.rel_singer);
        LL03 = (RelativeLayout) findViewById(R.id.rel_post);


        song_num = findViewById(R.id.song_num);
        singer_num = findViewById(R.id.singer_num);
        post_num = findViewById(R.id.post_num);
        cover_song = findViewById(R.id.cover_song);
        cover_singer = findViewById(R.id.cover_singer);

        //必须为按钮设置(this)监听器

        //head_pic modify
        /*
        点击头像进行更换头像
         */
        head_picture.setOnClickListener(this);
        infomodify.setOnClickListener(this);
        labelmodify.setOnClickListener(this);
        LL01.setOnClickListener(this);
        LL02.setOnClickListener(this);
        LL03.setOnClickListener(this);

        //await

        //set


        String data = "";
        try {
            data = "?username=" + URLEncoder.encode(now, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String userJson = PostUtil.doPost("MineInit", data);


        try {

            JSONObject jsonObject = new JSONObject(userJson);

            JSONArray Labels = jsonObject.getJSONArray("Labels");

            //Looper.prepare();

            nickname.setText(jsonObject.getString("Nickname"));
            if (jsonObject.getString("Sex").equals("女")) {
                sex.setBackgroundResource(R.drawable.woman);
                u_sex = "女";
            } else {
                sex.setBackgroundResource(R.drawable.man);
                u_sex = "男";
            }
            region.setText(jsonObject.getString("Region"));
            signature.setText(jsonObject.getString("Signature"));
            if (Labels.getString(0).isEmpty()) {
                label_1.setVisibility(View.INVISIBLE);
            }
            if (Labels.getString(1).isEmpty()) {
                label_2.setVisibility(View.INVISIBLE);
            }
            if (Labels.getString(2).isEmpty()) {
                label_3.setVisibility(View.INVISIBLE);
            }
            label_1.setText(Labels.getString(0));
            label_2.setText(Labels.getString(1));
            label_3.setText(Labels.getString(2));
            song_num.setText(jsonObject.getInt("SongLikedNum") + "首歌曲");
            singer_num.setText(jsonObject.getInt("SingerLikedNum") + "个歌手");
            post_num.setText(jsonObject.getInt("PostNum") + "篇帖子");

            head_picture.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("Headshot")));
            cover_song.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("SongPicLatestLiked")));
            cover_singer.setImageBitmap(ImgIOJsonOutputUtils.base64ToBitmap(jsonObject.getString("SingerPicLatestLiked")));

            u_Labels.add(Labels.getString(0));
            u_Labels.add(Labels.getString(1));
            u_Labels.add(Labels.getString(2));

            //notify

            //Looper.loop();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_picture:// 更换头像
                showTypeDialog();
                break;
            case R.id.btn_edit://信息修改
                Intent intent1 = new Intent(getApplicationContext(),activity_infoModify.class);
                Bundle bundle1 = new Bundle();//该类用作携带数据
                bundle1.putString("nickname", nickname.getText().toString());
                bundle1.putString("sex", u_sex);
                bundle1.putString("region", region.getText().toString());
                bundle1.putString("signature", signature.getText().toString());
                intent1.putExtras(bundle1);//附带上额外的数据
                startActivityForResult(intent1,4);
                break;
            case R.id.label_modify:// 标签修改
                Intent intent2 = new Intent(getApplicationContext(),activity_choose_label.class);
                Bundle bundle2 = new Bundle();//该类用作携带数据
                bundle2.putStringArrayList("Labels", (ArrayList<String>) u_Labels);
                intent2.putExtras(bundle2);//附带上额外的数据
                startActivityForResult(intent2,5);
                break;
            case R.id.rel_song:// 喜欢的歌
                startActivityForResult(new Intent(getApplicationContext(), activity_LikeSong.class),6);
                break;
            case R.id.rel_singer:// 喜欢的歌手
                startActivityForResult(new Intent(getApplicationContext(), activity_LikeSinger.class),7);
                break;
            case R.id.rel_post:// 我的帖子
                startActivityForResult(new Intent(getApplicationContext(), activity_MinePost.class),8);
                break;
        }
    }

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView no_update = (TextView) view.findViewById(R.id.no_update);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        no_update.setOnClickListener(new View.OnClickListener() {// 不修改
            @Override
            public void onClick(View v) {
//                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
//                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
//            case 2:
//                if (resultCode == RESULT_OK) {
//                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
//                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
//                }
//
//                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    User n_user =(User) getApplication();
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        String u_headshot = ImgIOJsonOutputUtils.bitmapToBase64(head);
                        String n_data = "";
                        try {
                            n_data = "?username=" + URLEncoder.encode(n_user.getUserName(), "UTF-8")+
                                    "&userheadshot=" + URLEncoder.encode(u_headshot, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String headUpdate = PostUtil.doPost("HeadUpdate", n_data);

                        try {

                            JSONObject jsonObject = new JSONObject(headUpdate);

                            String result = null;
                            result = jsonObject.getString("Result");
                            Looper.prepare();
                            if (result.equals("success")) {
                                Toast.makeText(getApplicationContext(), "头像修改成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "头像修改失败", Toast.LENGTH_LONG).show();
                            }
                            Looper.loop();

                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                        }
                        //setPicToView(head);// 保存在SD卡中
                        head_picture.setImageBitmap(head);// 用ImageButton显示出来
                    }
                }
                break;

            case 4: //修改信息
            case 5: // 标签修改
            case 6: //查看歌曲
            case 7: //查看歌手
            case 8: //查看帖子
                if (resultCode == RESULT_OK) {
                    User n_user = (User) getApplication();
                    now = n_user.getUserName();
                    init();
                }

                break;

            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

//    private void setPicToView(Bitmap mBitmap) {
//        String sdStatus = Environment.getExternalStorageState();
//        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//            return;
//        }
//        FileOutputStream b = null;
//        File file = new File(path);
//        file.mkdirs();// 创建文件夹
//        String fileName = path + "head.jpg";// 图片名字
//        try {
//            b = new FileOutputStream(fileName);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 关闭流
//                b.flush();
//                b.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }






//    //friends list
//    public void check_list(View view) {
//
//        startActivity(new Intent(getApplicationContext(), activity_friendsList.class));
//
//    }
//
//    //song liked
//    public void song_liked(View view) {
//
//        startActivity(new Intent(getApplicationContext(), activity_LikeSong.class));
//
//    }
//
//    //singer liked
//    public void singer_liked(View view) {
//
//        startActivity(new Intent(getApplicationContext(), activity_LikeSinger.class));
//
//    }
//
//    //my post
//    public void myPost(View view) {
//
//        startActivity(new Intent(getApplicationContext(), activity_MinePost.class));
//
//    }

    //homepage
    public void homepage(View view) {

        startActivity(new Intent(getApplicationContext(), activity_forums_square.class));
        activity_mine.this.finish();

    }

    //chat
    public void chat(View view) {

        startActivity(new Intent(getApplicationContext(), activity_chat.class));
        activity_mine.this.finish();

    }

    //recommend
    public void recommend(View view) {

        startActivity(new Intent(getApplicationContext(), activity_RecommendFriend.class));
        activity_mine.this.finish();

    }


}

