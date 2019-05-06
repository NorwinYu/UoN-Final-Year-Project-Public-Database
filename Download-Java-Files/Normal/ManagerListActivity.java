package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.ListOtgruzki;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.Zayavka;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.ZayavkaListAdapter;
import com.example.usk.glotus_final.System.Catalog.Kontragent;
import com.example.usk.glotus_final.System.Catalog.Pochta;
import com.example.usk.glotus_final.System.Encryption.AES;
import com.example.usk.glotus_final.System.connection.Server;
import com.example.usk.glotus_final.System.loginFiles.SignInActivity;
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

public class ManagerListActivity extends AppCompatActivity {

    // Удачи, ребят!

    private ImageButton dobavitZayavku;
    static Server server;
    private EditText etSearch;
    Button btnLoadExtra;
    String search;
    ListView list;
    boolean but=false;
    int skip=0;
    int top=20;
    int p=0;
    ArrayList<ManagerZayavka> mManagerZayavkas;
    SwipeRefreshLayout swipeView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.manager_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.zakaz:
                return true;
            case R.id.otgruz:
                Intent myIntent = new Intent(ManagerListActivity.this, ListOtgruzki.class);
                startActivity(myIntent);
                return true;
            case  R.id.logout:
                Intent intent = new Intent(ManagerListActivity.this, SignInActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_list);
        InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_view);
      //  android.support.v7.app.ActionBar actionBar = getSupportActionBar();
       // actionBar.hide();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        list = (ListView) findViewById(R.id.manager_list);

        //подсказка
        Toast.makeText(getApplicationContext(),
                "Нажмите два раза что бы посмотреть данные", Toast.LENGTH_LONG).show();

        dobavitZayavku = (ImageButton) findViewById(R.id.btn_dobavit_zayavku);
        dobavitZayavku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Добавить заявку работает", Toast.LENGTH_SHORT).show();
                ManagerListAdapter.isnew=true;
                Intent myIntent = new Intent(ManagerListActivity.this, NewReceptionManagerActivity.class);
                startActivity(myIntent);
            }
        });
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
        swipeView.setRefreshing(true);
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
        btnLoadExtra=new Button(this);
        etSearch=findViewById(R.id.etSearch);
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
    public void initlist(final ArrayList<ManagerZayavka> ppp){

        btnLoadExtra.setText("Load More...");


// Adding Load More button to lisview at bottom

        final ManagerListAdapter adapter = new ManagerListAdapter(this, R.layout.manager_list_item_layout, ppp);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.setAdapter(adapter);
                if (but==false){
                    list.addFooterView(btnLoadExtra);
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
    String s="";
    public void searchItem(String textToSearch){
        search=s.toString();

        ArrayList<ManagerZayavka> newww = new ArrayList<>();
        System.out.println(textToSearch);
        for(int i=0;i<mManagerZayavkas.size();i++){
            if(mManagerZayavkas.get(i).getSender().toLowerCase().contains(textToSearch.toLowerCase()) || mManagerZayavkas.get(i).getNumber().toLowerCase().contains(textToSearch.toLowerCase()) || mManagerZayavkas.get(i).getRecept().toLowerCase().contains(textToSearch.toLowerCase())){
                newww.add(mManagerZayavkas.get(i));
            }
        }

        initlist(newww);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refresh() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {


        mManagerZayavkas = new ArrayList<>();


        process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7?$format=json&$filter=DeletionMark%20eq%20false&$orderby=Ref_Key%20desc&$skip=0&$top="+top+"","GET",User.getCredential(),"{}");
        skip=20;
        String json = server.getRes();


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
                System.out.println((String) Kontragent.kontrpreferences.getString(array.getJSONObject(i).getString("Заказчик_Key"),""));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            ManagerZayavka john = null;
            try {
                // String otkuda=getadr(array.getJSONObject(i).getString("Откуда_Key"));
                // String kuda=getadr(array.getJSONObject(i).getString("Куда_Key"));
                // String mened=getadr(array.getJSONObject(i).getString("Менеджер_Key"));




                john = new ManagerZayavka(array.getJSONObject(i).getString("Number"),
                        array.getJSONObject(i).getString("Date"),
                        array.getJSONObject(i).getString("Отправитель"),
                        array.getJSONObject(i).getString("Получатель"),/**/
                        array.getJSONObject(i).getString("Откуда_Key"),
                        array.getJSONObject(i).getString("Куда_Key"),
                        array.getJSONObject(i).getString("Ref_Key"),
                        array.getJSONObject(i).getString("Заказчик_Key"),
                        array.getJSONObject(i).getString("Менеджер_Key"),
                        array.getJSONObject(i).getString("Подразделение_Key"),
                        array.getJSONObject(i).getString("СтатусЗаказа"),
                        array.getJSONObject(i).getString("НаименованиеГруза"),
                        array.getJSONObject(i).getString("СопроводительныйДокумент"),

                        (String) Pochta.pochtakontr.getAll().get("Заказчик_Key"),
                        array.getJSONObject(i).getString("КонтактноеЛицоОтправителя"),
                        array.getJSONObject(i).getString("КонтактноеЛицоПолучатель"),
                        array.getJSONObject(i).getString("ТелефонКонтактногоЛицоОтправителя"),
                        array.getJSONObject(i).getString("ТелефонКонтактногоЛицаПолучателя"),
                        array.getJSONObject(i).getString("АдресОтправителя"),
                        array.getJSONObject(i).getString("АдресПолучателя"),

                        array.getJSONObject(i).getString("ОбъемПлан"),
                        array.getJSONObject(i).getString("ОбъемФакт"),
                        array.getJSONObject(i).getString("ВесПлан"),
                        array.getJSONObject(i).getString("ВесФакт"),
                        array.getJSONObject(i).getString("КоличествоПлан"),
                        array.getJSONObject(i).getString("КоличествоФакт"),
                        array.getJSONObject(i).getString("ВидПеревозки"),
                        array.getJSONObject(i).getString("ЦенаПеревозки"),
                        array.getJSONObject(i).getString("СтатусЗаказа"),
                        array.getJSONObject(i).getString("Комментарий")





                );





            } catch (JSONException e) {
                e.printStackTrace();
            }
            mManagerZayavkas.add(john);



    }
      /* final ManagerListAdapter adapter = new ManagerListAdapter(this, R.layout.manager_list_item_layout, mManagerZayavkas);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.setAdapter(adapter);
                swipeView.setRefreshing(false);
            }
        });*/
      initlist(mManagerZayavkas);





}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showmore(ArrayList<ManagerZayavka> ppp) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (btnLoadExtra.getText().toString().equals("Найти еще")){
           int kk=0;
           int aa=0;
           int yy=skip+p;
            process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7?$format=json&$filter=DeletionMark%20eq%20false&$orderby=Ref_Key%20desc&$skip="+yy+"&$top="+200+"","GET",User.getCredential(),"{}");

            String json = server.getRes();


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
            for(int i = 0; i < array.length(); i++) {
                try {
                    System.out.println((array.getJSONObject(i).getString("Number") +
                            array.getJSONObject(i).getString("Date") +
                            array.getJSONObject(i).getString("Отправитель") +
                            array.getJSONObject(i).getString("Получатель") +
                            array.getJSONObject(i).getString("АдресОтправителя") +
                            array.getJSONObject(i).getString("АдресПолучателя")));
                    System.out.println((String) Kontragent.kontrpreferences.getString(array.getJSONObject(i).getString("Заказчик_Key"), ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ManagerZayavka john = null;
                try {
                    // String otkuda=getadr(array.getJSONObject(i).getString("Откуда_Key"));
                    // String kuda=getadr(array.getJSONObject(i).getString("Куда_Key"));
                    // String mened=getadr(array.getJSONObject(i).getString("Менеджер_Key"));


                    john = new ManagerZayavka(array.getJSONObject(i).getString("Number"),
                            array.getJSONObject(i).getString("Date"),
                            array.getJSONObject(i).getString("Отправитель"),
                            array.getJSONObject(i).getString("Получатель"),/**/
                            array.getJSONObject(i).getString("Откуда_Key"),
                            array.getJSONObject(i).getString("Куда_Key"),
                            array.getJSONObject(i).getString("Ref_Key"),
                            array.getJSONObject(i).getString("Заказчик_Key"),
                            array.getJSONObject(i).getString("Менеджер_Key"),
                            array.getJSONObject(i).getString("Подразделение_Key"),
                            array.getJSONObject(i).getString("СтатусЗаказа"),
                            array.getJSONObject(i).getString("НаименованиеГруза"),
                            array.getJSONObject(i).getString("СопроводительныйДокумент"),

                            (String) Pochta.pochtakontr.getAll().get("Заказчик_Key"),
                            array.getJSONObject(i).getString("КонтактноеЛицоОтправителя"),
                            array.getJSONObject(i).getString("КонтактноеЛицоПолучатель"),
                            array.getJSONObject(i).getString("ТелефонКонтактногоЛицоОтправителя"),
                            array.getJSONObject(i).getString("ТелефонКонтактногоЛицаПолучателя"),
                            array.getJSONObject(i).getString("АдресОтправителя"),
                            array.getJSONObject(i).getString("АдресПолучателя"),

                            array.getJSONObject(i).getString("ОбъемПлан"),
                            array.getJSONObject(i).getString("ОбъемФакт"),
                            array.getJSONObject(i).getString("ВесПлан"),
                            array.getJSONObject(i).getString("ВесФакт"),
                            array.getJSONObject(i).getString("КоличествоПлан"),
                            array.getJSONObject(i).getString("КоличествоФакт"),
                            array.getJSONObject(i).getString("ВидПеревозки"),
                            array.getJSONObject(i).getString("ЦенаПеревозки"),
                            array.getJSONObject(i).getString("СтатусЗаказа"),
                            array.getJSONObject(i).getString("Комментарий")


                    );


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aa+=1;

                if(john.getSender().toLowerCase().contains(search.toLowerCase()) || john.getNumber().toLowerCase().contains(search.toLowerCase()) || john.getRecept().toLowerCase().contains(search.toLowerCase())){
                    ppp.add(john);
                    kk+=1;
                    p=aa;
                    if (kk>5)
                        break;


                }
            }

        }
        else{
            p=0;
            process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7?$format=json&$filter=DeletionMark%20eq%20false&$orderby=Ref_Key%20desc&$skip="+skip+"&$top="+top+"","GET",User.getCredential(),"{}");
            skip+=20;
            String json = server.getRes();


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
                    System.out.println((String) Kontragent.kontrpreferences.getString(array.getJSONObject(i).getString("Заказчик_Key"),""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ManagerZayavka john = null;
                try {
                    // String otkuda=getadr(array.getJSONObject(i).getString("Откуда_Key"));
                    // String kuda=getadr(array.getJSONObject(i).getString("Куда_Key"));
                    // String mened=getadr(array.getJSONObject(i).getString("Менеджер_Key"));




                    john = new ManagerZayavka(array.getJSONObject(i).getString("Number"),
                            array.getJSONObject(i).getString("Date"),
                            array.getJSONObject(i).getString("Отправитель"),
                            array.getJSONObject(i).getString("Получатель"),/**/
                            array.getJSONObject(i).getString("Откуда_Key"),
                            array.getJSONObject(i).getString("Куда_Key"),
                            array.getJSONObject(i).getString("Ref_Key"),
                            array.getJSONObject(i).getString("Заказчик_Key"),
                            array.getJSONObject(i).getString("Менеджер_Key"),
                            array.getJSONObject(i).getString("Подразделение_Key"),
                            array.getJSONObject(i).getString("СтатусЗаказа"),
                            array.getJSONObject(i).getString("НаименованиеГруза"),
                            array.getJSONObject(i).getString("СопроводительныйДокумент"),

                            (String) Pochta.pochtakontr.getAll().get("Заказчик_Key"),
                            array.getJSONObject(i).getString("КонтактноеЛицоОтправителя"),
                            array.getJSONObject(i).getString("КонтактноеЛицоПолучатель"),
                            array.getJSONObject(i).getString("ТелефонКонтактногоЛицоОтправителя"),
                            array.getJSONObject(i).getString("ТелефонКонтактногоЛицаПолучателя"),
                            array.getJSONObject(i).getString("АдресОтправителя"),
                            array.getJSONObject(i).getString("АдресПолучателя"),

                            array.getJSONObject(i).getString("ОбъемПлан"),
                            array.getJSONObject(i).getString("ОбъемФакт"),
                            array.getJSONObject(i).getString("ВесПлан"),
                            array.getJSONObject(i).getString("ВесФакт"),
                            array.getJSONObject(i).getString("КоличествоПлан"),
                            array.getJSONObject(i).getString("КоличествоФакт"),
                            array.getJSONObject(i).getString("ВидПеревозки"),
                            array.getJSONObject(i).getString("ЦенаПеревозки"),
                            array.getJSONObject(i).getString("СтатусЗаказа"),
                            array.getJSONObject(i).getString("Комментарий")





                    );





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









        }}
      /* final ManagerListAdapter adapter = new ManagerListAdapter(this, R.layout.manager_list_item_layout, mManagerZayavkas);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.setAdapter(adapter);
                swipeView.setRefreshing(false);
            }
        });*/






    }
    public void onBackPressed() {
        moveTaskToBack(true);

    }

}
