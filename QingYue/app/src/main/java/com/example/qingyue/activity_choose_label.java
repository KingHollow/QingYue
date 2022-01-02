package com.example.qingyue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;
import com.mysql.fabric.xmlrpc.base.Array;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class activity_choose_label extends Activity implements CompoundButton.OnCheckedChangeListener {  //此处使用View.OnClickListener接口

    private Button label_1,label_2,label_3,label_4,label_5,label_6,label_7,label_8,label_9,label_10;
    private Button label_11,label_12,label_13,label_14,label_15,label_16,label_17,label_18,label_19,label_20;
    ArrayList<String> selected = new ArrayList<>();//用来存储已选取项的集合对象


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_label);

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_choose_label.this.finish();
            }
        });

        Bundle bundle = this.getIntent().getExtras();

        List<String> n_Labels = new ArrayList<>();
        n_Labels = bundle.getStringArrayList("Labels");

        init();
    }


    private void init() {
        /**【所有复选框id数组】**/
        int chk_id [] = {R.id.label_1 ,R.id.label_2 ,R.id.label_3, R.id.label_4,
                R.id.label_5 , R.id.label_6 , R.id.label_7 , R.id.label_8 , R.id.label_9,R.id.label_10,
                R.id.label_11 ,R.id.label_12 ,R.id.label_13, R.id.label_14,
                R.id.label_15 , R.id.label_16 , R.id.label_17 , R.id.label_18 , R.id.label_19,R.id.label_20};
        /**【循环为所有复选框注册监听事件】**/
        for(int id : chk_id){
            CheckBox chk = findViewById(id);
            chk.setOnCheckedChangeListener(this);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked){                      //选项被选取
            if (selected.size() == 3) {
                Toast.makeText(getApplicationContext(), "最多选择三个标签", Toast.LENGTH_LONG).show();
            } else {
                selected.add(compoundButton.getHint().toString());   //添加到集合中
            }
        } else {                             //选项被取消
            selected.remove(compoundButton.getHint().toString());//从集合中取消
        }
    }

    public void modify(View view){

        for (int i = 0;i < 3;i++) {
            if (selected.size() < 3) {
                selected.add("0");
            } else {
                break;
            }
        }

        User n_user =(User) getApplication();

        String data="";
        try {
            data = "?username="+ URLEncoder.encode(n_user.getUserName(), "UTF-8")+
                    "&label1="+ URLEncoder.encode(selected.get(0).isEmpty()?"0":selected.get(0), "UTF-8")+
                    "&label2="+ URLEncoder.encode(selected.get(1).isEmpty()?"0":selected.get(1), "UTF-8")+
                    "&label3="+ URLEncoder.encode(selected.get(2).isEmpty()?"0":selected.get(2), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String userJson = PostUtil.doPost("LabelChoose",data);

        try {

            JSONObject jsonObject = new JSONObject(userJson);

            String result = null;
            result = jsonObject.getString("Result");
            //Looper.prepare();
            if (result.equals("success")) {
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                //数据是使用Intent返回
                Intent intent = new Intent();

                activity_choose_label.this.setResult(RESULT_OK, intent);

                //关闭Activity
                activity_choose_label.this.finish();

            } else {
                Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
            }
            //Looper.loop();

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }


    }
}