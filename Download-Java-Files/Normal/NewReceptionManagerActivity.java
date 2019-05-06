package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CaptureResult;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.Reception;
import com.example.usk.glotus_final.System.Catalog.Adress;
import com.example.usk.glotus_final.System.Catalog.Kontragent;
import com.example.usk.glotus_final.System.Catalog.KontragentNum;
import com.example.usk.glotus_final.System.Catalog.Pochta;
import com.example.usk.glotus_final.System.Catalog.Status;
import com.example.usk.glotus_final.System.Catalog.Vidperevoz;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;

public class NewReceptionManagerActivity extends AppCompatActivity {

    private RelativeLayout rlZakazchik, rlPoluchatel, rlOtpravitel;
    private Button showZakazchik, showPoluchatel, showOtpravitel,btn_dokumenty, btn_sohranit, btn_otmenit;
    private TextView tv_code,tv_date,et_z_pochta,
            //новые поля стоимости
            tv_s_kurs, tv_stavkands, tv_poryadok, tv_costtranp, tv_sebestoimost,
            tv_obshcost;
    private EditText   et_z_dogovor, et_z_otprav,et_p_adres,
            et_z_otkuda, et_z_adres, et_z_kontakt, et_z_telefon, et_p_poluch, et_date_edit,
            et_p_kolich, et_p_ves, et_p_obiem, et_f_kolich, et_f_ves, et_f_obiem, et_info,stoim,
             et_dostavka, et_stoimost,et_kommentar,  rkuda, rotkuda,et_p_kontakt,et_p_telefon,stom;

    // новые поля стоимости
    private Spinner spin_valuta, spin_vidoplaty,et_status,et_vid;
    private DatePickerDialog.OnDateSetListener mDateListener;

    private AutoCompleteTextView autoComplete_zakazchik, autoComplete_otkuda, autoComplete_kuda;

    private List<String> rlistkuda = new ArrayList<String>();
    private ArrayList<String> rkontr = new ArrayList<String>();
    private ArrayList<String> kontr = new ArrayList<String>();

    private ArrayAdapter<String> kudaAdapter;
    private ArrayAdapter<String> otkudaAdapter;
    private ArrayAdapter<String> zakazchikAdapter;

    private ReceptionData toReception;
    String ttt="";

    private List<String> listkuda = new ArrayList<String>();
    Calendar cal=Calendar.getInstance();
    int yearQ = cal.get(Calendar.YEAR);
    int monthQ = cal.get(Calendar.MONTH);
    int dayQ = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_newreception);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        ScrollView scrollView=findViewById(R.id.scrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

            }
        });
        findView();
        setData();
        showZakazchik.setOnClickListener(clickZakaz);
        showPoluchatel.setOnClickListener(clickPoluch);
        showOtpravitel.setOnClickListener(clickOtprav);
//      btn_dokumenty.setOnClickListener(clickDocumenty);
        btn_sohranit.setOnClickListener(clickSohranit);
        btn_otmenit.setOnClickListener(clickOtmena);
        autoComplete_zakazchik.setOnItemClickListener(setP);

        autoComplete_zakazchik.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!kontr.contains(autoComplete_zakazchik.getText().toString())) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewReceptionManagerActivity.this, "Такого заказчика не существует!", Toast.LENGTH_SHORT).show();

                        }
                    });
                    ColorStateList colorStateList = ColorStateList.valueOf(RED);
                    autoComplete_zakazchik.setBackgroundTintList(colorStateList);
                }
                else{

                    ColorStateList colorStateList = ColorStateList.valueOf(BLACK);
                    autoComplete_zakazchik.setBackgroundTintList(colorStateList);
                }


            }
        });
        autoComplete_kuda.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!listkuda.contains(autoComplete_kuda.getText().toString())) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewReceptionManagerActivity.this, "Такого адреса не существует!", Toast.LENGTH_SHORT).show();

                        }
                    });
                    ColorStateList colorStateList = ColorStateList.valueOf(RED);
                    autoComplete_kuda.setBackgroundTintList(colorStateList);
                }
                else{

                    ColorStateList colorStateList = ColorStateList.valueOf(BLACK);
                    autoComplete_kuda.setBackgroundTintList(colorStateList);
                }

            }
        });
        autoComplete_otkuda.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!listkuda.contains(autoComplete_otkuda.getText().toString())) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewReceptionManagerActivity.this, "Такого адреса не существует!", Toast.LENGTH_SHORT).show();

                        }
                    });
                    ColorStateList colorStateList = ColorStateList.valueOf(RED);
                    autoComplete_otkuda.setBackgroundTintList(colorStateList);
                }
                else{

                    ColorStateList colorStateList = ColorStateList.valueOf(BLACK);
                    autoComplete_otkuda.setBackgroundTintList(colorStateList);
                }

            }
        });


        et_date_edit.setInputType(InputType.TYPE_NULL);
        et_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewReceptionManagerActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateListener,yearQ,monthQ,dayQ
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                yearQ=year;
                monthQ=month;
                dayQ=dayOfMonth;
                month=month+1;
                String m = null;
                if (month<=9)
                    m="0"+month;
                else
                    m+=String.valueOf(month);
                String d=null;
                if (dayOfMonth<=9)
                    d="0"+dayOfMonth;
                else
                    d= String.valueOf(dayOfMonth);
                ttt=year+"-"+m+"-"+d+"T";
                et_date_edit.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };





    }

    public void setData(){
        tv_code.setText("code");
        tv_date.setText("date");

        int i=0;
        int zk=0;
        for (Map.Entry<String, ?> entry :Kontragent.kontrpreferences.getAll().entrySet()){
            rkontr.add((String) entry.getKey());
             kontr.add((String) entry.getValue());
             i++;
        }
        zakazchikAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,kontr);
        autoComplete_zakazchik.setAdapter(zakazchikAdapter);

        int kda=0,oda=0;
        i=0;
        for (Map.Entry<String, ?> entry : Adress.adresspreferences.getAll().entrySet()) {
            System.out.println((String) entry.getValue());
            listkuda.add((String) entry.getValue());
            rlistkuda.add((String)entry.getKey());
            if (((String)entry.getKey()).equals(RefKeys.KudaKey))
                kda=i;
            if (((String)entry.getKey()).equals(RefKeys.OkudaKey))
                oda=i;
            i++;
        }
        kudaAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,listkuda);
        autoComplete_kuda.setAdapter(kudaAdapter);

        otkudaAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,listkuda);
        autoComplete_otkuda.setAdapter(otkudaAdapter);

        autoComplete_kuda.setText(" ");
        autoComplete_otkuda.setText(" ");
        autoComplete_zakazchik.setText(" ");

//      rkuda.setText("");
//      rotkuda.setText("");
        et_z_pochta.setText("");
//      et_z_dogovor.setText("");
        et_z_otprav.setText("");
        et_z_otkuda.setText("");
        et_z_adres.setText("");
        et_z_kontakt.setText("");
        et_z_telefon.setText("");
        et_p_poluch.setText("");
        et_date_edit.setText("");
        //et_p_kolich.setText("");
        et_p_ves.setText("");
        et_p_obiem.setText("");
        //et_f_kolich.setText("");
        //et_f_ves.setText("");
        /*et_f_obiem.setText("");
        et_info.setText("");
        et_vid.setText("");
        et_dostavka.setText("");
//      et_stoimost.setText("");
        et_status.setText("");
        et_kommentar.setText("");
        et_p_adres.setText("");*/

        //новые поля
        ArrayAdapter status=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Status.statusy);
        et_status.setAdapter(status);
        ArrayAdapter vidy= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Vidperevoz.vidy);
        et_vid.setAdapter(vidy);

        tv_s_kurs.setText("");
        tv_stavkands.setText("");
        tv_poryadok.setText("");
        tv_costtranp.setText("");
        tv_sebestoimost.setText("");
        tv_obshcost.setText("");

        //spinner data..
    }

    public void findView(){
        rlZakazchik = findViewById(R.id.rl_zakazchik);
        rlOtpravitel = findViewById(R.id.rl_otpravitel);
        rlPoluchatel = findViewById(R.id.rl_poluchatel);
        showOtpravitel = findViewById(R.id.btn_otpravitel);
        showPoluchatel = findViewById(R.id.btn_poluchatel);
        showZakazchik = findViewById(R.id.btn_zakazchik);
        btn_dokumenty = findViewById(R.id.btn_dokumenty);
        btn_sohranit = findViewById(R.id.btn_sohranit);
        btn_otmenit = findViewById(R.id.btn_otmenit);

        et_p_telefon = findViewById(R.id.et_p_telefon);
        et_p_kontakt = findViewById(R.id.et_p_kontakt);
        tv_code=findViewById(R.id.tv_code);
        tv_date=findViewById(R.id.tv_date);
        tv_poryadok=findViewById(R.id.tv_poryadok);
        tv_costtranp=findViewById(R.id.tv_costtranp);
        tv_sebestoimost=findViewById(R.id.tv_sebestoimost);
        tv_obshcost=findViewById(R.id.tv_obshcost);
        tv_s_kurs=findViewById(R.id.tv_s_kurs);
        tv_stavkands=findViewById(R.id.tv_stavkands);
        autoComplete_zakazchik=findViewById(R.id.autoComplete_zakazchik);
        autoComplete_otkuda=findViewById(R.id.autoComplete_otkuda);
        autoComplete_kuda=findViewById(R.id.autoComplete_kuda);
        stoim=findViewById(R.id.stoim);

        et_z_pochta=findViewById(R.id.et_z_pochta);

        et_z_otprav=findViewById(R.id.et_z_otprav);
        et_z_otkuda=findViewById(R.id.et_z_otkuda);
        et_z_adres=findViewById(R.id.et_z_adres);
        et_z_kontakt=findViewById(R.id.et_z_kontakt);
        et_z_telefon=findViewById(R.id.et_z_telefon);
        et_p_poluch=findViewById(R.id.et_p_poluch);
        et_p_adres=findViewById(R.id.et_p_adres);
        et_date_edit=findViewById(R.id.et_date_edit);
        et_p_kolich=findViewById(R.id.et_p_kolich);
        et_p_ves=findViewById(R.id.et_p_ves);
        et_p_obiem=findViewById(R.id.et_p_obiem);
        et_f_kolich=findViewById(R.id.et_f_kolich);
        et_f_ves=findViewById(R.id.et_f_ves);
        et_f_obiem=findViewById(R.id.et_f_obiem);
        et_info=findViewById(R.id.et_info);
        et_vid=findViewById(R.id.et_vid);
        et_dostavka=findViewById(R.id.et_dostavka);
//      et_stoimost=findViewById(R.id.et_stoimost);
        et_status=findViewById(R.id.et_status);
        et_kommentar=findViewById(R.id.et_kommentar);

        tv_s_kurs=findViewById(R.id.tv_s_kurs);
        tv_stavkands=findViewById(R.id.tv_stavkands);
        tv_poryadok=findViewById(R.id.tv_poryadok);
        tv_costtranp=findViewById(R.id.tv_costtranp);
        tv_sebestoimost=findViewById(R.id.tv_sebestoimost);
        tv_obshcost=findViewById(R.id.tv_obshcost);

        spin_valuta=findViewById(R.id.spin_valuta);
        spin_vidoplaty=findViewById(R.id.spin_vidopltaty);

    }


    private AdapterView.OnItemClickListener setP = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            et_z_pochta.setText((CharSequence) Pochta.pochtakontr.getAll().get(rkontr.get(kontr.indexOf(autoComplete_zakazchik.getText().toString()))));
            System.out.println(rkontr.get(kontr.indexOf(autoComplete_zakazchik.getText().toString())));
        }

    };

    private View.OnClickListener clickZakaz = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(rlZakazchik.getVisibility()==View.VISIBLE)
                rlZakazchik.setVisibility(rlZakazchik.GONE);
            else
                rlZakazchik.setVisibility(rlZakazchik.VISIBLE);

        }
    };

    private View.OnClickListener clickOtprav = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(rlOtpravitel.getVisibility()==View.VISIBLE)
                rlOtpravitel.setVisibility(View.GONE);
            else
                rlOtpravitel.setVisibility(rlOtpravitel.VISIBLE);

        }
    };

    private View.OnClickListener clickPoluch = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (rlPoluchatel.getVisibility()==View.VISIBLE)
                rlPoluchatel.setVisibility(View.GONE);
            else
                rlPoluchatel.setVisibility(rlPoluchatel.VISIBLE);
        }
    };

    private View.OnClickListener clickDocumenty = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };

    private View.OnClickListener clickSohranit = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            String keyKuda=null,keyOtkuda=null,keyZakaz=null;
            try{
                keyKuda=rlistkuda.get(listkuda.indexOf(autoComplete_kuda.getText().toString()));
            }
            catch (Exception e){
                keyKuda="00000000-0000-0000-0000-000000000000";
                autoComplete_kuda.setText("");

            }
            try{
                keyOtkuda=rlistkuda.get(listkuda.indexOf(autoComplete_otkuda.getText().toString()));
            }
            catch (Exception e){
                keyOtkuda="00000000-0000-0000-0000-000000000000";
                autoComplete_otkuda.setText("");
            }
            try{
                keyZakaz=rkontr.get(kontr.indexOf(autoComplete_zakazchik.getText().toString()));
            }
            catch (Exception e){
                keyZakaz="00000000-0000-0000-0000-000000000000";
                autoComplete_zakazchik.setText("");
            }
            if (!ttt.contains("Т"))
            {
                monthQ+=1;
                String m = null;
                if (monthQ<=9)
                    m="0"+monthQ;
                else
                    m+=String.valueOf(monthQ);
                String d=null;
                if (dayQ<=9)
                    d="0"+dayQ;
                else
                    d= String.valueOf(dayQ);
                ttt=yearQ+"-"+m+"-"+d+"T";
            }
            Date currentTime = Calendar.getInstance().getTime();


            String time="";
            if (currentTime.getHours()>9)
                time+= (currentTime.getHours())+":";
            else
                time+="0"+ (currentTime.getHours())+":";
            if (currentTime.getMinutes()>9)
                time+= (currentTime.getMinutes())+":";
            else
                time+="0"+ (currentTime.getMinutes())+":";
            if ((currentTime.getSeconds())>9)
                time+= (currentTime.getSeconds());
            else
                time+="0"+currentTime.getSeconds();
            System.out.println(time);
            String data="{\"АдресПолучателя\":\""+et_p_adres.getText().toString()+"\"," +
                    "\"ВидПеревозки\":\""+et_vid.getSelectedItem().toString()+"\","+
                    "\"Date\":\""+ ttt+time.toString()+"\"," +
                    "\"АдресОтправителя\":\""+et_z_adres.getText().toString()+"\"," +
                    "\"ВесПлан\":\""+et_p_ves.getText().toString()+"\","+
                    "\"КоличествоПлан\":\""+et_p_kolich.getText().toString()+"\","+
                    "\"Комментарий\":\""+et_kommentar.getText().toString()+"\","+
                    "\"КонтактноеЛицоОтправителя\":\""+et_z_kontakt.getText().toString()+"\","+
                    "\"КонтактноеЛицоПолучатель\":\""+et_p_kontakt.getText().toString()+"\","+
                    "\"Куда_Key\":\""+keyKuda+"\","+
                    "\"НаименованиеГруза\":\""+et_info.getText().toString()+"\","+
                    "\"ОбъемПлан\":\""+et_p_obiem.getText().toString()+"\","+
                    "\"Откуда_Key\":\""+keyOtkuda+"\","+
                    "\"Отправитель\":\""+et_z_otprav.getText().toString()+"\","+
                    "\"Получатель\":\""+et_p_poluch.getText().toString()+"\","+
                    "\"ТелефонКонтактногоЛицаПолучателя\":\""+et_p_telefon.getText().toString()+"\","+
                    "\"ТелефонКонтактногоЛицоОтправителя\":\""+et_z_telefon.getText().toString()+"\","+
                    "\"СтатусЗаказа\":\""+et_status.getSelectedItem().toString()+"\","+
                    "\"Заказчик_Key\":\""+keyZakaz+"\","+
                    "\"ЦенаПеревозки\":\""+stoim.getText().toString()+"\""+
                    "}";
            System.out.println(data);
            String res=null;
            try {
                res = process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7?$format=json", "POST", User.getCredential(),
                        data);
                System.out.println(res);
                System.out.println("add");
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

            System.out.println(res);





            JSONObject array=null;
            try {
                array = new JSONObject(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
                ManagerZayavka john = null;
                try {
                    john = new ManagerZayavka(array.getString("Number"),
                            array.getString("Date"),
                            array.getString("Отправитель"),
                            array.getString("Получатель"),/**/
                            array.getString("Откуда_Key"),
                            array.getString("Куда_Key"),
                            array.getString("Ref_Key"),
                            array.getString("Заказчик_Key"),
                            array.getString("Менеджер_Key"),
                            array.getString("Подразделение_Key"),
                            array.getString("СтатусЗаказа"),
                            array.getString("НаименованиеГруза"),
                            array.getString("СопроводительныйДокумент"),

                            (String) Pochta.pochtakontr.getAll().get("Заказчик_Key"),
                            array.getString("КонтактноеЛицоОтправителя"),
                            array.getString("КонтактноеЛицоПолучатель"),
                            array.getString("ТелефонКонтактногоЛицоОтправителя"),
                            array.getString("ТелефонКонтактногоЛицаПолучателя"),
                            array.getString("АдресОтправителя"),
                            array.getString("АдресПолучателя"),

                            array.getString("ОбъемПлан"),
                            array.getString("ОбъемФакт"),
                            array.getString("ВесПлан"),
                            array.getString("ВесФакт"),
                            array.getString("КоличествоПлан"),
                            array.getString("КоличествоФакт"),
                            array.getString("ВидПеревозки"),
                            array.getString("ЦенаПеревозки"),
                            array.getString("СтатусЗаказа"),
                            array.getString("Комментарий")
                    );





                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ManagerListAdapter.item=john;



            Intent intent=new Intent(NewReceptionManagerActivity.this, ReceptionManagerActivity.class);
            startActivity(intent);
           /* toReception=new ReceptionData(tv_code.getText().toString(),tv_date.getText().toString(),
                    autoComplete_zakazchik.getText().toString(),et_z_pochta.getText().toString(),
                    et_z_otprav.getText().toString(),autoComplete_otkuda.getText().toString(),et_z_adres.getText().toString(),
                    et_z_kontakt.getText().toString(),et_z_telefon.getText().toString(),et_p_poluch.getText().toString(),
                    et_date_edit.getText().toString(),autoComplete_kuda.getText().toString(),et_p_kolich.getText().toString(),
                    et_p_ves.getText().toString(), et_p_obiem.getText().toString(),
                    "obiem",
                    "kolichestvo",
                    "ves",
                    et_info.getText().toString(),
                    et_vid.getSelectedItem().toString(),
                    et_dostavka.getText().toString(),
                    "stoimost",
                    et_status.getSelectedItem().toString(),
                    et_kommentar.getText().toString(),
                    et_z_otkuda.getText().toString(),
                    et_z_adres.getText().toString(),
                    et_z_kontakt.getText().toString(),
                    et_z_telefon.getText().toString());


            Intent intent=new Intent(NewReceptionManagerActivity.this, ReceptionManagerActivity.class);
            intent.putExtra("toReceptionData",toReception);
            startActivity(intent);*/
        }
    };

    private View.OnClickListener clickOtmena = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /*String data="{\"АдресПолучателя\":\""+et_p_adres.getText().toString()+"\"," +
                    "\"ВидПеревозки\":\""+mactv_vid.getSelectedItem().toString()+"\","+
                    "\"ВесФакт\":\""+et_f_ves.getText().toString()+"\"," +
                    "\"Date\":\""+ mactv_date_edit.getText().toString()+"\"," +
                    "\"АдресОтправителя\":\""+mactv_z_adres.getText().toString()+"\"," +
                    "\"ВесПлан\":\""+et_p_ves.getText().toString()+"\","+
                    "\"КоличествоПлан\":\""+et_p_kolich.getText().toString()+"\","+
                    "\"КоличествоФакт\":\""+et_f_kolich.getText().toString()+"\","+
                    "\"Комментарий\":\""+et_kommentar.getText().toString()+"\","+
                    "\"КонтактноеЛицоОтправителя\":\""+mactv_z_kontakt.getText().toString()+"\","+
                    "\"КонтактноеЛицоПолучатель\":\""+mactv_p_kontakt.getText().toString()+"\","+
                    "\"Куда_Key\":\""+rlistkuda.get(kudaAdapter.getPosition(autoComplete_otkuda.getText().toString()))+"\","+
                    "\"НаименованиеГруза\":\""+mactv_info.getText().toString()+"\","+
                    "\"ОбъемПлан\":\""+et_p_obiem.getText().toString()+"\","+
                    "\"ОбъемФакт\":\""+et_f_obiem.getText().toString()+"\","+
                    "\"Откуда_Key\":\""+rlistkuda.get(otkudaAdapter.getPosition(autoComplete_otkuda.getText().toString()))+"\","+
                    "\"Отправитель\":\""+mactv_z_otprav.getText().toString()+"\","+
                    "\"Получатель\":\""+mactv_p_poluch.getText().toString()+"\","+
                    "\"ТелефонКонтактногоЛицаПолучателя\":\""+mactv_p_telefon.getText().toString()+"\","+
                    "\"ТелефонКонтактногоЛицоОтправителя\":\""+mactv_z_telefon.getText().toString()+"\","+
                    "\"СтатусЗаказа\":\""+Status.statusy[mactv_status.getSelectedItemPosition()]+"\","+
                    "\"НомерДоговора\":\""+mactv_z_dogovor.getText().toString()+"\""+
                    "\"Заказчик_Key\":\""+rkontr.get(zakazchikAdapter.getPosition(autoComplete_zakazchik.getText().toString()))+"\","+
                    "\"ЦенаПеревозки\":\""+mactv_stoimost.getText().toString()+"\""+
                    "}";
            System.out.println(data);
            RefKeys.Status=Status.statusy[mactv_status.getSelectedItemPosition()];
            RefKeys.ZakazKey=rkontr.get(zakazchikAdapter.getPosition(autoComplete_zakazchik.getText().toString()));
            RefKeys.KudaKey=rlistkuda.get(kudaAdapter.getPosition(autoComplete_kuda.getText().toString()));
            RefKeys.OkudaKey=rlistkuda.get(kudaAdapter.getPosition(autoComplete_otkuda.getText().toString()));
            RefKeys.vidpr=mactv_vid.getSelectedItem().toString();


            String res = process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7(guid\'" + RefKeys.Ref_Key + "\')?$format=json", "PATCH", User.getCredential(),
                    data);
            System.out.println(res);*/


        }
    };
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String process(String url, String way, String cred, String data) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String body=url+","+way+","+cred+"*---*" +data;
        System.out.println(body);
        String string = AES.aesEncryptString(body, "1234567890123456");
        body="data="+string;
        Log.d("aaa",body);
        System.out.println(body);
        Server server;
        server = new Server("http://185.209.23.53/odata/demoaes.php",null, body);
        return server.post();
    }

}
