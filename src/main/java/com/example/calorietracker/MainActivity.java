package com.example.calorietracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btnLogin;
    private Button btnSignup;

    private AlarmManager alarmMgr;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnSignup = (Button) findViewById(R.id.signup);
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(this, TimeService.class);
        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, sign_up.class);
                startActivity(intent);
        //        CreateCredential createCredential = new CreateCredential();
//
  //              if (!(username.getText().toString().isEmpty()) &&
    //                    !(password.getText().toString().isEmpty())) {
      //              createCredential.execute(username.getText().toString(), password.getText().toString());
          //      }


            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FindByid findByid = new FindByid();


                FindByUsername findUser = new FindByUsername();
                if (!(username.getText().toString().isEmpty()) &&
                        !(password.getText().toString().isEmpty())){

                    String pass = password.getText().toString();
                    String passwordhash = md5(pass);

                    findUser.execute(username.getText().toString(), passwordhash);


                    //findByid.execute(username.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this,"Please enter login detail", Toast.LENGTH_SHORT).show();
                }

                //Intent intent = new Intent(MainActivity.this, Home.class);
                //startActivity(intent);
            }
        });




    }

    public static String md5(String password){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] input = password.getBytes();
            byte[] output = md.digest(input);
            //将md5处理后的output结果利用Base64转成原有的字符串,不会乱码
            String str = Base64.encodeToString(input, Base64.DEFAULT);
//			String str = new String(output); //原有转换
            return str;
        } catch (NoSuchAlgorithmException e) {
            //System.out.println("密码加密失败");
            return "";
        }
    }


    private class SearchImage extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String...params){
            return NDBAPI.search(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //TextView tv= (TextView) findViewById(R.id.tv);
            TextView image = (TextView)findViewById(R.id.image);
            //image.setText(GoogleSearchAPI.getImage(result));
            image.setText(result);
        }
    }






    private class FindByUsername extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = new String();
            String userdetail = RestClient.findByUsername(params[0]);
            try {
                if (userdetail.equals("[]") || userdetail.equals("")) {
                    result = "User does not exist";
                } else {
                    if (RestClient.checkPassword(userdetail, params[1])) {

                        JSONArray jsonArray = new JSONArray(userdetail);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        int userid = jsonObject.getInt("userid");
                        result = RestClient.findByUserid(userid);
                    } else {
                        result = "Login Failed.";
                    }
                }
            } catch (Exception e){}
            return result;
        }


        protected void onPostExecute(String result) {
            if(result.equals("Login Failed.")) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            } else if(result.equals("User does not exist")){
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(MainActivity.this, Home.class);

                SharedPreferences spMyUnits = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor eMyUnits = spMyUnits.edit();
                int userid = RestClient.getUserid(result);
                String firstname = RestClient.getFirstName(result);

                eMyUnits.putInt("userid", userid);
                eMyUnits.putString("fname", firstname);
                eMyUnits.apply();
                /*
                Bundle bundle=new Bundle();
                bundle.putString("detail", result);
                intent.putExtras (bundle);
                */
                startActivity(intent);
            }

        }
    }

}
