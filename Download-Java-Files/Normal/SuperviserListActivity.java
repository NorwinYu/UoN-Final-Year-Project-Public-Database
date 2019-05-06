package com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.ExpedPage;
import com.example.usk.glotus_final.System.Catalog.Adress;
import com.example.usk.glotus_final.System.Catalog.AutoUpdate;
import com.example.usk.glotus_final.System.Catalog.Mdnames;
import com.example.usk.glotus_final.System.Encryption.AES;
import com.example.usk.glotus_final.System.connection.Server;
import com.example.usk.glotus_final.System.loginFiles.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SuperviserListActivity extends AppCompatActivity {
    // Это форма со списком заявок для супервайзера

    // Log.d are for testing
    private static final String TAG = "SuperviserListActivity";
    // List for zayavkas
    private ArrayList<Zayavka> mZayavkas;

    SwipeRefreshLayout swipeView;
    static Server server;
    TextView etSearch;
    ListView mListView;
    int skip=0;
    int top=20;
    boolean but=false;
    String search=null;
    Button btnLoadExtra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superviser_list);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        etSearch=findViewById(R.id.etSearch);
        mListView = (ListView) findViewById(R.id.superviser_list);
        // Declaring listView
        ListView list = (ListView) findViewById(R.id.superviser_list);
        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_view);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        btnLoadExtra = new Button(this);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            refresh();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        //mZayavkas.add(new Zayavka("0", "Glotus"));
        swipeView.setRefreshing(true);
        Log.d(TAG, "onCreate: Started successfully");
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    refresh();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        /*ZayavkaListAdapter adapter = new ZayavkaListAdapter(this, R.layout.superviser_list_item_layout, mZayavkas);
        list.setAdapter(adapter);*/
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("/"+s.toString()+"/");
                search=s.toString();
                if (s.toString().equals(""))
                    btnLoadExtra.setText("Показать еще");
                else
                    btnLoadExtra.setText("Найти еще");
                searchItem(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                search=s.toString();
                if (s.toString().equals(""))
                    btnLoadExtra.setText("Показать еще");
                else
                    btnLoadExtra.setText("Найти еще");

            }
        });

        }
    public void searchItem(String textToSearch){
        search=s.toString();

        ArrayList<Zayavka> newww = new ArrayList<>();
        System.out.println(textToSearch);
        for(int i=0;i<mZayavkas.size();i++){
            if(mZayavkas.get(i).getSender().toLowerCase().contains(textToSearch.toLowerCase()) || mZayavkas.get(i).getNumber().toLowerCase().contains(textToSearch.toLowerCase()) || mZayavkas.get(i).getRecept().toLowerCase().contains(textToSearch.toLowerCase())){
                newww.add(mZayavkas.get(i));
            }
        }

       initlist(newww);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refresh() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        new AutoUpdate().update();
        // Declaring new array. Сам потом добавь туда заявки. Посмотри сам класс. Сделай констракторы еще.
        mZayavkas = new ArrayList<>();
        //ArrayList<Orders> peopleList = new ArrayList<>();


        System.out.println(User.cred);
        process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7?$format=json&$filter=DeletionMark%20eq%20false&$orderby=Ref_Key%20desc&$skip=0&$top="+top+"","GET",User.getCredential(),"{}");
        skip=20;
        String json = server.getRes();
        System.out.println(json);

        System.out.println(json);

        JSONArray array = null;
        JSONObject jsonObj=null;
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array = jsonObj.getJSONArray("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < array.length(); i++){
            try {
                System.out.println((array.getJSONObject(i).getString("Number")+
                        array.getJSONObject(i).getString("Date")+
                        array.getJSONObject(i).getString("Отправитель")+
                        array.getJSONObject(i).getString("Получатель")+
                        array.getJSONObject(i).getString("АдресОтправителя")+
                        array.getJSONObject(i).getString("АдресПолучателя")));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Zayavka john = null;
            try {
               // String otkuda=getadr(array.getJSONObject(i).getString("Откуда_Key"));
               // String kuda=getadr(array.getJSONObject(i).getString("Куда_Key"));
                // String mened=getadr(array.getJSONObject(i).getString("Менеджер_Key"));

                



                john = new Zayavka(array.getJSONObject(i).getString("Number"),
                        array.getJSONObject(i).getString("Date"),
                        array.getJSONObject(i).getString("Отправитель"),
                        array.getJSONObject(i).getString("Получатель"),/**/
                        (String) Adress.adresspreferences.getAll().get(array.getJSONObject(i).getString("Откуда_Key")),
                        (String) Adress.adresspreferences.getAll().get(array.getJSONObject(i).getString("Куда_Key")),
                        array.getJSONObject(i).getString("Ref_Key"),
                        array.getJSONObject(i).getString("Заказчик_Key"),
                        (String) Mdnames.mdpreferences.getAll().get(array.getJSONObject(i).getString("Менеджер_Key")),
                        array.getJSONObject(i).getString("Подразделение_Key"),array.getJSONObject(i).getString("СтатусЗаказа"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            mZayavkas.add(john);
        }
      /* for(int i=0;i<10;i++){
           Zayavka a= new Zayavka("a","a","a","a","a","a","a","a","a","a");
           mZayavkas.add(a);
       }*/

        initlist(mZayavkas);


    }
    String s="";

    public void initlist(final ArrayList<Zayavka> ppp){

        btnLoadExtra.setText("Load More...");


// Adding Load More button to lisview at bottom

        final ZayavkaListAdapter adapter = new ZayavkaListAdapter(this, R.layout.superviser_list_item_layout, ppp);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.setAdapter(adapter);
                if (but==false){
                    mListView.addFooterView(btnLoadExtra);
                    but=true;}

                btnLoadExtra.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View arg0) {
                        try {
                            showmore(ppp);
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        }


                        adapter.notifyDataSetChanged();



                    }
                });
                swipeView.setRefreshing(false);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showmore(ArrayList<Zayavka> ppp) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
       //ArrayList<Orders> peopleList = new ArrayList<>();


        System.out.println(User.cred);
        process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7?$format=json&$filter=DeletionMark%20eq%20false&$orderby=Ref_Key%20desc&$skip="+skip+"&$top="+top+"","GET",User.getCredential(),"{}");
        skip+=20;
        String json = server.getRes();
        System.out.println(json);

        System.out.println(json);

        JSONArray array = null;
        JSONObject jsonObj=null;
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array = jsonObj.getJSONArray("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < array.length(); i++){
            try {
                System.out.println((array.getJSONObject(i).getString("Number")+
                        array.getJSONObject(i).getString("Date")+
                        array.getJSONObject(i).getString("Отправитель")+
                        array.getJSONObject(i).getString("Получатель")+
                        array.getJSONObject(i).getString("АдресОтправителя")+
                        array.getJSONObject(i).getString("АдресПолучателя")));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Zayavka john = null;
            try {
                // String otkuda=getadr(array.getJSONObject(i).getString("Откуда_Key"));
                // String kuda=getadr(array.getJSONObject(i).getString("Куда_Key"));
                // String mened=getadr(array.getJSONObject(i).getString("Менеджер_Key"));





                john = new Zayavka(array.getJSONObject(i).getString("Number"),
                        array.getJSONObject(i).getString("Date"),
                        array.getJSONObject(i).getString("Отправитель"),
                        array.getJSONObject(i).getString("Получатель"),/**/
                        (String) Adress.adresspreferences.getAll().get(array.getJSONObject(i).getString("Откуда_Key")),
                        (String) Adress.adresspreferences.getAll().get(array.getJSONObject(i).getString("Куда_Key")),
                        array.getJSONObject(i).getString("Ref_Key"),
                        array.getJSONObject(i).getString("Заказчик_Key"),
                        (String) Mdnames.mdpreferences.getAll().get(array.getJSONObject(i).getString("Менеджер_Key")),
                        array.getJSONObject(i).getString("Подразделение_Key"),array.getJSONObject(i).getString("СтатусЗаказа"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (btnLoadExtra.getText().toString().equals("Найти еще")){
            if(john.getSender().toLowerCase().contains(search.toLowerCase()) || john.getNumber().toLowerCase().contains(search.toLowerCase()) || john.getRecept().toLowerCase().contains(search.toLowerCase())){
                ppp.add(john);
            }}
            else {
                ppp.add(john);

            }
        }
      /* for(int i=0;i<10;i++){
           Zayavka a= new Zayavka("a","a","a","a","a","a","a","a","a","a");
           mZayavkas.add(a);
       }*/





    }
    public void onBackPressed() {
        moveTaskToBack(true);

    }


}
