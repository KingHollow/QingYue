package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class activity_infoModify extends Activity {

    public RadioGroup mRadioGroup1;
    public RadioButton mRadio1, mRadio2;
    EditText nickname;
    EditText user_location;
    EditText user_signature;
    String sex = "男";

    String o_nickname;
    String o_sex;
    String o_region;
    String o_signature;

    ImageButton btn_back;
    private static final String TestApp="TestApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_modify);
        Bundle bundle = this.getIntent().getExtras();

        o_nickname = bundle.getString("nickname");
        o_sex = bundle.getString("sex");
        o_region = bundle.getString("region");
        o_signature = bundle.getString("signature");

        Log.d(TestApp, o_sex);


        btn_back = (ImageButton)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_infoModify.this.finish();
            }
        });

        nickname = findViewById(R.id.nickname);
        user_location = findViewById(R.id.user_location);
        user_signature = findViewById(R.id.user_signature);
        mRadioGroup1 = (RadioGroup) findViewById(R.id.sex);
        mRadio1 = (RadioButton) findViewById(R.id.woman);
        mRadio2 = (RadioButton) findViewById(R.id.man);

        //为单选按钮组添加事件监听
        mRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton RB=(RadioButton) findViewById(i);//获取被选择的单选按钮
                Log.i("单选按钮","你的选择是："+RB.getText());
                sex = RB.getText().toString();
            }
        });


    }

    public void modify(View view){


        String cnickname = nickname.getText().toString();
        String cregion = user_location.getText().toString();
        String csignature = user_signature.getText().toString();
        String csex = sex;
        User n_user =(User) getApplication();

        Log.d(TestApp, csex);
        Log.d(TestApp, n_user.getUserName());

        String data="";
        try {
            data = "?username="+ URLEncoder.encode(n_user.getUserName(), "UTF-8")+
                    "&nickname="+ URLEncoder.encode(cnickname.isEmpty()?o_nickname:cnickname, "UTF-8")+
                    "&sex="+ URLEncoder.encode(csex.isEmpty()?o_sex:csex, "UTF-8")+
                    "&region="+ URLEncoder.encode(cregion.isEmpty()?o_region:cregion, "UTF-8")+
                    "&signature="+ URLEncoder.encode(csignature.isEmpty()?o_signature:csignature, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String userJson = PostUtil.doPost("InfoModify",data);

        try {

            JSONObject jsonObject = new JSONObject(userJson);


            String result = null;
            result = jsonObject.getString("Result");
            //Looper.prepare();
            if (result.equals("success")) {
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("nickname", cnickname);
                intent.putExtra("sex", csex);
                intent.putExtra("region", cregion);
                intent.putExtra("signature", csignature);
                //设置返回数据
                activity_infoModify.this.setResult(RESULT_OK, intent);

                //关闭Activity
                activity_infoModify.this.finish();

            } else {
                Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
            }
            //Looper.loop();

        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }


    }
}