package com.example.qingyue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qingyue.entity.User;
import com.example.qingyue.utils.PostUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.widget.Button;


public class MainActivity extends Activity {

    Button btn_login;
    Button btn_reg;
    private static final String TestApp="TestApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reg(View view) {

        startActivity(new Intent(getApplicationContext(), activity_register.class));

    }


    public void login(View view) {

        EditText EditTextusername = (EditText) findViewById(R.id.et_username);
        EditText EditTextpassword = (EditText) findViewById(R.id.et_password);

        new Thread() {
            @Override
            public void run() {

                String data = "";
                try {
                    data = "?username=" + URLEncoder.encode(EditTextusername.getText().toString(), "UTF-8") +
                            "&password=" + URLEncoder.encode(EditTextpassword.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d(TestApp,data);

                String userJson = PostUtil.doPost("Login", data);


                try {

                    JSONObject jsonObject = new JSONObject(userJson);


                    String result = null;
                    result = jsonObject.getString("Result");
                    Looper.prepare();
                    if (result.equals("success")) {
                        //
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();

                        User n_user =(User)getApplication();
                        n_user.setUserName(EditTextusername.getText().toString());

                        startActivity(new Intent(getApplication(),activity_forums_square.class));
                        MainActivity.this.finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
                    }
                    Looper.loop();

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
                /*
                 */

            }
        }.start();


    }

}


