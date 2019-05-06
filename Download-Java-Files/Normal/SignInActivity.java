package com.example.usk.glotus_final.System.loginFiles;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usk.glotus_final.ManagerApp.ManagerListFiles.ManagerListActivity;
import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.SuperviserListActivity;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.Zayavka;
import com.example.usk.glotus_final.System.Catalog.Adress;
import com.example.usk.glotus_final.System.Catalog.AutoUpdate;
import com.example.usk.glotus_final.System.Catalog.Kontragent;
import com.example.usk.glotus_final.System.Catalog.KontragentNum;
import com.example.usk.glotus_final.System.Catalog.Mdnames;
import com.example.usk.glotus_final.System.Catalog.Pochta;
import com.example.usk.glotus_final.System.Catalog.Podrazd;
import com.example.usk.glotus_final.System.Catalog.Transport;
import com.example.usk.glotus_final.System.Catalog.Update;
import com.example.usk.glotus_final.System.Catalog.UpdateInf;
import com.example.usk.glotus_final.System.Catalog.isOn;
import com.example.usk.glotus_final.System.Encryption.AES;
import com.example.usk.glotus_final.System.connection.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okio.ByteString;


public class SignInActivity extends AppCompatActivity {
    Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String TAG = "SignInActivity";

    //Creating instance of SharedPreferences in order to "remember" user's login and password
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    //Defining all the elements on the page
    private EditText mEmail, mPassword;
    private Button btnLogin;
    private CheckBox mCheckbox;
    private String name = "";
    private String password = "";
    ProgressBar progressBar;
    public static ProgressBar progressBar2;
    ImageView iv_logo_main;
    public static TextView textView;
    static Server server;

    User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Log.d(TAG, "onCreate: started");

        //Initializing page elements
        mEmail = (EditText) findViewById(R.id.et_login);
        mPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_sign_in);
        mCheckbox = (CheckBox) findViewById(R.id.cb_remember_me);
        iv_logo_main =(ImageView) findViewById(R.id.iv_logo_main);
        progressBar=findViewById(R.id.progressBar);
        progressBar2=findViewById(R.id.progressBar2);
        textView=(TextView) findViewById(R.id.textView);

        textView.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


        //create a new objects by getting the data from login form
        user=new User(mEmail.getText().toString(),
                mPassword.getText().toString());

        //connect with server

        // For saving user data
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferences = getSharedPreferences("mydatabase", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        checkSharedPreferences();

        // Сделай метод для входа в систему loginUser()
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: login");
                name = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                loginUser(view);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(netIsAvailable()==false){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignInActivity.this,"Ошибка соединения с интернетом!",Toast.LENGTH_SHORT).show();
                            progressing(true);
                        }
                    });
                }
            }
        }).start();


    }

    private void loginUser(View view){
        Log.d(TAG, "loginUser: started");

        // Короче, не трогай. Это для сохранения введенных ранее логина и пароля.
        // Проверяет чекбокс, если да, то сохраняет. Если ранее сохранял, то вытаскивает из памяти.
        // Если не то и не другое, то просто просит заполнить поля.

        // OK

        if(mCheckbox.isChecked()){
            mEditor.putString(getString(R.string.checkbox), "True");
            mEditor.commit();

            name = mEmail.getText().toString();

            mEditor.putString(getString(R.string.email), name);
            mEditor.commit();

            password = mPassword.getText().toString();

            mEditor.putString(getString(R.string.password), password);
            mEditor.commit();
        } else {
            //set a checkbox when the application starts
            mEditor.putString(getString(R.string.checkbox), "False");
            mEditor.commit();
            //save the email
            mEditor.putString(getString(R.string.email), "");
            mEditor.commit();
            //save the password
            mEditor.putString(getString(R.string.password), "");
            mEditor.commit();
        }

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "loginUser: " + name + " " + password);

        //тут продолжи код ( что делать должен? )
        progressing(false);
        textView.setText("Вход в систему");
        progressBar2.setProgress(10);

       /* SignInActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                progressing(false);

            }});
        new Thread(new Runnable() {
            @Override
            public void run() {
                progressing(false);
            }
        }).start();
*/
/*
       // server=new Server("http://185.209.23.53/InfoBase/odata/standard.odata",getCredential(name,password));
        server.setUrl("http://185.209.23.53/InfoBase/odata/standard.odata?$format=json");
        server.setCredential(getCredential(name,password));
        System.out.println(server.get());
        System.out.println(server.getAns());
        user.setCred(getCredential(name,password));

        check(server.getStatus());*/
        //
        new Thread(new Runnable() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                /*server.setUrl("http://185.209.23.53/InfoBase/odata/standard.odata?$format=json");
                server.setCredential(getCredential(name,password));
                System.out.println(server.get());
                System.out.println(server.getAns());
                user.setCred(getCredential(name,password));*/
                User.username=name;
                User.password=password;


                try {
                    process("http://185.209.23.53/InfoBase/odata/standard.odata/Catalog_%D0%9F%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D0%B8?$format=json&$filter=Code%20eq%20%27"+
                            URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8.toString())+"%27&$select=%D0%A0%D0%BE%D0%BB%D1%8C%D0%92%D0%A1%D0%B8%D1%81%D1%82%D0%B5%D0%BC%D0%B5","GET",User.getCredential(),"{}");
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
                System.out.println(server.getRes());
                System.out.println("-----------------");
                try {
                    check(server.getStatus());
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String process(String url, String way, String cred, String data) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String body=url+","+way+","+cred+"*---*" +data;
        System.out.println(body);
        String string = AES.aesEncryptString(body, "1234567890123456");
        body="data="+string;
        System.out.println(body);
        server = new Server("http://185.209.23.53/odata/demoaes.php",null, body);
        return server.post();
    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void check(Integer status) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if(status==-1)
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SignInActivity.this,"Ошибка соединения с интернетом!",Toast.LENGTH_SHORT).show();
                    progressing(true);
                }
            });
        else if(status==0 || !server.res.contains("value"))
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SignInActivity.this,"Не правильный логин или пароль!",Toast.LENGTH_SHORT).show();
                    progressing(true);

                }
            });
        else if(status==1) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SignInActivity.this, "Вы вошли в систему!", Toast.LENGTH_SHORT).show();

                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("Авторизовано");
                    progressBar2.setProgress(20);
                }
            });

                Adress.adresspreferences=PreferenceManager.getDefaultSharedPreferences(this);
                Adress.adresspreferences=getSharedPreferences("adres", Context.MODE_PRIVATE);

                Kontragent.kontrpreferences=PreferenceManager.getDefaultSharedPreferences(this);
                Kontragent.kontrpreferences=getSharedPreferences("kontr", Context.MODE_PRIVATE);

                KontragentNum.kontrnumpreferences=PreferenceManager.getDefaultSharedPreferences(this);
                KontragentNum.kontrnumpreferences=getSharedPreferences("kontnum", Context.MODE_PRIVATE);

                Mdnames.mdpreferences=PreferenceManager.getDefaultSharedPreferences(this);
                Mdnames.mdpreferences=getSharedPreferences("mdnames", Context.MODE_PRIVATE);

                Podrazd.pdpreferences=PreferenceManager.getDefaultSharedPreferences(this);
                Podrazd.pdpreferences=getSharedPreferences("podrazd", Context.MODE_PRIVATE);

                Transport.trpreferences=PreferenceManager.getDefaultSharedPreferences(this);
                Transport.trpreferences=getSharedPreferences("transport", Context.MODE_PRIVATE);

                Pochta.pochtakontr=PreferenceManager.getDefaultSharedPreferences(this);
                Pochta.pochtakontr=getSharedPreferences("pochta", Context.MODE_PRIVATE);

                isOn.preferences=PreferenceManager.getDefaultSharedPreferences(this);
                isOn.preferences=getSharedPreferences("ison", Context.MODE_PRIVATE);

                UpdateInf.lastupdate =PreferenceManager.getDefaultSharedPreferences(this);
                UpdateInf.lastupdate =getSharedPreferences("lastupdate", Context.MODE_PRIVATE);


                  if(isOn.preferences.getAll().get("refresh")==null) {
                            System.out.println("aaa");
                        new Update().getCatalogs();
                        isOn.preferences.edit().putString("refresh", "true").commit();
                        new AutoUpdate().fupdate();

                   }
                   else {
                       new AutoUpdate().update();
                   }

            String role=getrole();
            ///problema 1c нету Данные Роль в Системе
            // Роль в системе нету данные
            if(name.contains("Saadat")){
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                System.out.println(User.getCredential());
                System.out.println(server.getRes());
                finish();
                Intent myIntent = new Intent(SignInActivity.this, ManagerListActivity.class);
                startActivity(myIntent);
            }
            else{
            finish();
                Intent myIntent = new Intent(SignInActivity.this, SuperviserListActivity.class);
                startActivity(myIntent);}
        }
    }
    public String getrole(){
        ///problema 1c нету Данные Роль в Системе

        return  "";
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    private void checkSharedPreferences(){
        Log.d(TAG, "checkSharedPreferences: started");
        String checkbox = mPreferences.getString(getString(R.string.checkbox),"False");
        String email = mPreferences.getString(getString(R.string.email),"");
        String password = mPreferences.getString(getString(R.string.password), "");
        mEmail.setText(email);
        mPassword.setText(password);
        if(checkbox.equals("True")){
            mCheckbox.setChecked(true);
        } else {
            mCheckbox.setChecked(false);
        }
    }

    public void progressing(Boolean action){
        //in set False will show ProgressBar
        if(action==false)
        {
            mEmail.setVisibility(View.INVISIBLE);
            mPassword.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.INVISIBLE);
            mCheckbox.setVisibility(View.INVISIBLE);

            textView.setVisibility(View.INVISIBLE);
            progressBar2.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        //in set True will login form
        else
        {
            mCheckbox.setVisibility(View.VISIBLE);
            mEmail.setVisibility(View.VISIBLE);
            mPassword.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);

            textView.setVisibility(View.INVISIBLE);
            progressBar2.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
    public String getCredential(String login,String pass){
        try {
            String usernameAndPassword =  login+ ":" + pass;
            byte[] bytes = usernameAndPassword.getBytes("UTF-8");
            String encoded = ByteString.of(bytes).base64();
            return "Basic " + encoded;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();



        }
    }   
}
