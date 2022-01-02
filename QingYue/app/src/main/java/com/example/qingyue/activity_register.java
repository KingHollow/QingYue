package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class activity_register extends Activity {

    EditText username = null;
    EditText password = null;
    EditText repassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.et_Rgname);
        password = findViewById(R.id.et_Repassword);
        repassword = findViewById(R.id.et_Repassword1);

    }


    public void register(View view){


        String cusername = username.getText().toString();
        String cpassword = password.getText().toString();
        String crepassword = repassword.getText().toString();



        new Thread(){
            @Override
            public void run() {

                String data="";
                try {
                    data = "?username="+ URLEncoder.encode(cusername, "UTF-8")+
                            "&password="+ URLEncoder.encode(cpassword, "UTF-8")+
                            "&repassword="+ URLEncoder.encode(crepassword, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String userJson = PostUtil.doPost("Register",data);

                try {

                    JSONObject jsonObject = new JSONObject(userJson);

                    String result = null;
                    result = jsonObject.getString("Result");
                    Looper.prepare();


                    if(result.equals("failed"))
                    {
                        Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();

                    }
                    if(result.equals("usernamerepeat"))
                    {
                        Toast.makeText(getApplicationContext(),"该账号已经存在，请换一个账号",Toast.LENGTH_LONG).show();

                    }
                    if(result.equals("success"))
                    {
                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplication(),MainActivity.class));
                        activity_register.this.finish();

                    }
                    if(result.equals("notequal"))
                    {
                        Toast.makeText(getApplicationContext(),"密码不一致",Toast.LENGTH_LONG).show();

                    }
                    if(result.equals("tooshort"))
                    {
                        Toast.makeText(getApplicationContext(),"密码应大于等于六位",Toast.LENGTH_LONG).show();

                    }
                    Looper.loop();

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }





            }
        }.start();


    }

}
