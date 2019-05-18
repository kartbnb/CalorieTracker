package com.example.calorietracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class sign_up extends AppCompatActivity {
    private EditText userfname;
    private EditText userlname;
    private EditText email;
    private TextView tv;
    private EditText weight;
    private EditText height;
    private EditText address;
    private EditText postcode;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Spinner levelOfActivity;
    private EditText stepsPerMile;
    private EditText username;
    private EditText password;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button createAccount;
    private Button login;
    private TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        createAccount = (Button) findViewById(R.id.create_account);
        login = (Button) findViewById(R.id.backtologin);
        tv = (TextView) findViewById(R.id.date);
        userfname = (EditText) findViewById(R.id.fname);
        userlname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        address = (EditText) findViewById(R.id.address);
        postcode = (EditText) findViewById(R.id.postcode);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        levelOfActivity = (Spinner) findViewById(R.id.level_of_activity);
        stepsPerMile = (EditText) findViewById(R.id.steps_per_mile);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        List<String> loa = new ArrayList<String>();
        loa.add("Choose your level of activity");
        loa.add("1");
        loa.add("2");
        loa.add("3");
        loa.add("4");
        loa.add("5");
        final ArrayAdapter<String> loaAdapeter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, loa);
        loaAdapeter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelOfActivity.setAdapter(loaAdapeter);
        status = (TextView) findViewById(R.id.textView);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateCredential createCredential = new CreateCredential();
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                if (!(username.getText().toString().isEmpty()) && !(password.getText().toString().isEmpty()) && !(userfname.getText().toString().isEmpty())
                        && !(userlname.getText().toString().isEmpty()) && !(email.getText().toString().isEmpty()) && !(tv.getText().toString().equals("Date of birth"))
                        && isNumeric(height.getText().toString()) && isNumeric(weight.getText().toString()) && isNumeric(postcode.getText().toString()) && postcode.getText().toString().length() == 4
                        && !(radioButton.getText().toString().isEmpty()) && isNumeric(stepsPerMile.getText().toString())) {
                    String passwordhash = new String();
                    passwordhash = md5(password.getText().toString());
                    createCredential.execute(username.getText().toString()
                            , passwordhash
                            , userfname.getText().toString()
                            , userlname.getText().toString()
                            , email.getText().toString()
                            , tv.getText().toString()
                            , height.getText().toString()
                            , weight.getText().toString()
                            , radioButton.getText().toString()
                            , address.getText().toString()
                            , postcode.getText().toString()
                            , levelOfActivity.getSelectedItem().toString()
                            , stepsPerMile.getText().toString());
                } else {
                    Toast.makeText(sign_up.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                }
                //Intent intent = new Intent(sign_up.this,
                //       MainActivity.class);
                //startActivity(intent);

                /*
                Credential credential = new Credential(1, username.getText().toString(), password.getText().toString());
                try{
                    char gender;
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(tv.getText().toString());
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);

                    if (radioButton.getText().toString().equals("Male")) {
                        gender = 'M';
                    } else {
                        gender = 'F';
                    }

                    Usertable usertable = new Usertable(credential, 1, userfname.getText().toString(), userlname.getText().toString(), email.getText().toString(),date, Integer.parseInt(height.getText().toString()), Integer.parseInt(weight.getText().toString()),
                            gender, address.getText().toString(),Integer.parseInt(postcode.getText().toString()),Integer.parseInt(levelOfActivity.getSelectedItem().toString()),Integer.parseInt(stepsPerMile.getText().toString()));
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
                    String stringUserJson = gson.toJson(usertable);
                    status.setText(stringUserJson);

                }catch(Exception e){
                    status.setText("nothing happened");
                }
                */
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up.this, MainActivity.class);
                startActivity(intent);
            }
        });


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(sign_up.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                if(year<=c.get(Calendar.YEAR) && month<=c.get(Calendar.MONTH) && dayOfMonth<=c.get(Calendar.DAY_OF_MONTH)) {
                    month = month + 1;
                    String date = dayOfMonth + "/" + month + "/" + year;
                    tv.setText(date);
                }else{
                    Toast.makeText(sign_up.this, "Please enter a valid date", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    public static String md5(String password) {
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


    private class CreateCredential extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = new String();
            if (RestClient.findByUsername(params[0]).equals("[]")) {
                int i = RestClient.findBigid(RestClient.findAllCredential());
                Credential credential = new Credential(i, params[0], params[1]);
                RestClient.createCredential(credential);

                try {

                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(params[5]);
                    char gender;
                    if (params[8].equals("Male")) {
                        gender = 'M';
                    } else {
                        gender = 'F';
                    }
                    Usertable usertable = new Usertable(credential, i
                            , params[2]
                            , params[3]
                            , params[4]
                            , date
                            , Integer.parseInt(params[6])
                            , Integer.parseInt(params[7])
                            , gender
                            , params[9]
                            , Integer.parseInt(params[10])
                            , Integer.parseInt(params[11])
                            , Integer.parseInt(params[12]));

                    RestClient.createUsertable(usertable);
                    result = "Account Created";
                } catch (Exception e) {

                }
            } else {
                result = params[0] + " has already been used.";
            }
            return result;

        }

        protected void onPostExecute(String result) {
            Toast.makeText(sign_up.this, result, Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
/*
    private class CreateUsertable extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                char gender = 'n';
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(params[4]);
                if(params[7].equals("Male")){
                    gender = 'M';
                }else {
                    gender = 'F';
                }
                Usertable usertable = new Usertable(Integer.parseInt(params[0])
                        , params[1]
                        , params[2]
                        , params[3]
                        , date
                        , Integer.parseInt(params[5])
                        , Integer.parseInt(params[6])
                        , gender
                        , params[8]
                        , Integer.parseInt(params[9])
                        , Integer.parseInt(params[10])
                        , Integer.parseInt(params[11]));
                RestClient.createUsertable(usertable);
            }catch(Exception e){

            }
            return null;
        }



    }
*/


}