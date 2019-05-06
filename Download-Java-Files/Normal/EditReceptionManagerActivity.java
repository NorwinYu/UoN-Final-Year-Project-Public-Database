package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.gesture.GestureOverlayView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.System.Catalog.Adress;
import com.example.usk.glotus_final.System.Catalog.Kontragent;
import com.example.usk.glotus_final.System.Catalog.Status;
import com.example.usk.glotus_final.System.Catalog.Vidperevoz;
import com.example.usk.glotus_final.System.Encryption.AES;
import com.example.usk.glotus_final.System.connection.Server;
import com.example.usk.glotus_final.System.loginFiles.SignInActivity;
import com.example.usk.glotus_final.System.loginFiles.User;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;

public class EditReceptionManagerActivity extends AppCompatActivity {

    private LinearLayout rlZakazchik, rlPoluchatel, rlOtpravitel;
    private Button showZakazchik, showPoluchatel, showOtpravitel, btn_dokumenty, btn_sohranit, btn_otmenit;
    private TextView tv_code,tv_date,mactv_z_pochta;
    private MultiAutoCompleteTextView   mactv_z_dogovor, mactv_z_otprav,
            mactv_z_otkuda, mactv_z_adres, mactv_z_kontakt, mactv_z_telefon, mactv_p_poluch, mactv_date_edit,
            mactv_info, mactv_dostavka, mactv_stoimost, mactv_p_otkuda,mactv_p_adres,mactv_p_kontakt,mactv_p_telefon;
    private EditText et_p_kolich, et_p_ves, et_p_obiem, et_f_kolich, et_f_ves, et_f_obiem,et_kommentar;
    private AutoCompleteTextView autoComplete_zakazchik, autoComplete_otkuda, autoComplete_kuda;
    private Spinner mactv_status,mactv_vid;

    private List<String> rlistkuda = new ArrayList<String>();
    private List<String>listkuda = new ArrayList<String>();
    private List<String> rkontr = new ArrayList<String>();
    private List<String> kontr = new ArrayList<String>();

    private ArrayAdapter<String> kudaAdapter;
    private ArrayAdapter<String> otkudaAdapter;
    private ArrayAdapter<String> zakazchikAdapter;
    private ScrollView scrollView;
    private Button btn_foto_gruza;



    private String KeyZakaz="00000000-0000-0000-0000-000000000000",KeyOtkuda="00000000-0000-0000-0000-000000000000",KeyKuda="00000000-0000-0000-0000-000000000000";

    private ReceptionData returnData;
    private ReceptionData recpData;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edreception);

        findView();

        //берет данные с ReceptionManagerActivity
        Intent intent=getIntent();
        recpData=(ReceptionData) intent.getExtras().getSerializable("receptionData");

        setData();
        btn_foto_gruza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        showZakazchik.setOnClickListener(clickZakaz);
        showPoluchatel.setOnClickListener(clickPoluch);
        showOtpravitel.setOnClickListener(clickOtprav);
//      btn_dokumenty.setOnClickListener(clickDocumenty);
        btn_otmenit.setOnClickListener(clickOtmena);

        //для "куда", надо брать данные с базы, и сохранить в лист
        //для "откуда", надо брать данные с базы, и сохранить в лист


        int kda=0,oda=0;
        int i=0;
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




        autoComplete_zakazchik.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!kontr.contains(autoComplete_zakazchik.getText().toString())) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditReceptionManagerActivity.this, "Такого заказчика не существует!", Toast.LENGTH_SHORT).show();

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
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!listkuda.contains(autoComplete_kuda.getText().toString())) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditReceptionManagerActivity.this, "Такого адреса не существует!", Toast.LENGTH_SHORT).show();

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
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!listkuda.contains(autoComplete_otkuda.getText().toString())) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditReceptionManagerActivity.this, "Такого адреса не существует!", Toast.LENGTH_SHORT).show();

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



        i=0;
        int zk=0;
        for (Map.Entry<String, ?> entry :Kontragent.kontrpreferences.getAll().entrySet()){
            kontr.add((String) entry.getValue());
            rkontr.add((String) entry.getKey());
            if (RefKeys.ZakazKey.equals((String) entry.getKey()))
                zk=i;
            i++;
        }
        zakazchikAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,kontr);
        autoComplete_zakazchik.setAdapter(zakazchikAdapter);

        mactv_status.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, Status.statusy));
        for (i=0;i<Status.statusy.length;i++){
            if (Status.statusy[i].equals(recpData.getStatus()))
            {
                mactv_status.setSelection(i);
                break;
            }
        }

        mactv_vid.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, Vidperevoz.vidy));
        for (i=0;i<Vidperevoz.vidy.length;i++)
        {
            if (Vidperevoz.vidy[i].equals(recpData.getVid())){
                mactv_vid.setSelection(i);
                break;
            }
        }

        btn_sohranit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    onSohranitButtonClick();
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
        });



    }

    //возвращает измененные данные в ReceptionManager
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSohranitButtonClick() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
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



        String data="{\"АдресПолучателя\":\""+mactv_p_adres.getText().toString()+"\"," +
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
                    "\"Куда_Key\":\""+keyKuda+"\","+
                    "\"НаименованиеГруза\":\""+mactv_info.getText().toString()+"\","+
                    "\"ОбъемПлан\":\""+et_p_obiem.getText().toString()+"\","+
                    "\"ОбъемФакт\":\""+et_f_obiem.getText().toString()+"\","+
                    "\"Откуда_Key\":\""+keyOtkuda+"\","+
                    "\"Отправитель\":\""+mactv_z_otprav.getText().toString()+"\","+
                    "\"Получатель\":\""+mactv_p_poluch.getText().toString()+"\","+
                    "\"ТелефонКонтактногоЛицаПолучателя\":\""+mactv_p_telefon.getText().toString()+"\","+
                    "\"ТелефонКонтактногоЛицоОтправителя\":\""+mactv_z_telefon.getText().toString()+"\","+
                    "\"СтатусЗаказа\":\""+Status.statusy[mactv_status.getSelectedItemPosition()]+"\","+
                    "\"НомерДоговора\":\""+mactv_z_dogovor.getText().toString()+"\""+
                    "\"Заказчик_Key\":\""+keyZakaz+"\","+
                    "\"ЦенаПеревозки\":\""+mactv_stoimost.getText().toString()+"\""+
                    "}";
        System.out.println(data);
        RefKeys.Status=Status.statusy[mactv_status.getSelectedItemPosition()];
        RefKeys.ZakazKey=keyZakaz;
        RefKeys.KudaKey=keyKuda;
        RefKeys.OkudaKey=keyOtkuda;
        RefKeys.vidpr=mactv_vid.getSelectedItem().toString();


        String res = process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7(guid\'" + RefKeys.Ref_Key + "\')?$format=json", "PATCH", User.getCredential(),
                data);
        System.out.println(res);

        returnData=new ReceptionData(tv_code.getText().toString(),tv_date.getText().toString(),
                autoComplete_zakazchik.getText().toString(),mactv_z_pochta.getText().toString(),mactv_z_dogovor.getText().toString(),
                mactv_z_otprav.getText().toString(),autoComplete_otkuda.getText().toString(),mactv_z_adres.getText().toString(),
                mactv_z_kontakt.getText().toString(),mactv_z_telefon.getText().toString(),mactv_p_poluch.getText().toString(),
                mactv_date_edit.getText().toString(),autoComplete_kuda.getText().toString(),et_p_kolich.getText().toString(),et_p_ves.getText().toString(),
                et_p_obiem.getText().toString(),et_f_kolich.getText().toString(),et_f_ves.getText().toString(),
                et_f_obiem.getText().toString(),mactv_info.getText().toString(),mactv_vid.getSelectedItem().toString(),
                mactv_stoimost.getText().toString(),mactv_status.getSelectedItem().toString(), et_kommentar.getText().toString(),
                mactv_p_otkuda.getText().toString(),mactv_p_adres.getText().toString(),mactv_p_kontakt.getText().toString(),
                mactv_p_telefon.getText().toString());
        Intent returnIntent=new Intent();
        returnIntent.putExtra("changedData", returnData);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public void setData(){
        tv_code.setText(recpData.getCode());
        tv_date.setText(recpData.getDate());
        mactv_z_pochta.setText(recpData.getPochta());
        mactv_z_dogovor.setText(recpData.getDogovor());
        mactv_z_otprav.setText(recpData.getOtpavitel());
        mactv_z_otkuda.setText(recpData.getOtkuda());
        mactv_z_adres.setText(recpData.getAddress());
        mactv_z_kontakt.setText(recpData.getKontakt());
        mactv_z_telefon.setText(recpData.getTelefon());
        mactv_p_poluch.setText(recpData.getPoluchatel());
        mactv_date_edit.setText(recpData.getDateEdit());
        autoComplete_zakazchik.setText(recpData.getZakazchik());
        autoComplete_otkuda.setText(recpData.getOtkuda());
        autoComplete_kuda.setText(recpData.getKuda());
        et_p_kolich.setText(recpData.getPlanKolich());
        et_p_ves.setText(recpData.getPlanVes());
        et_p_obiem.setText(recpData.getPlanObiem());
        et_f_kolich.setText(recpData.getFactKolich());
        et_f_ves.setText(recpData.getFactVes());
        et_f_obiem.setText(recpData.getFactObiem());
        mactv_info.setText(recpData.getInfo());

        //mactv_dostavka.setText(recpData.getDostavka());
        mactv_stoimost.setText(recpData.getStoimost());


        et_kommentar.setText(recpData.getKomment());

        mactv_p_otkuda.setText(recpData.getOtkuda());
        mactv_p_adres.setText(recpData.getTv_p_adres());
        mactv_p_kontakt.setText(recpData.getTv_p_kontakt());
        mactv_p_telefon.setText(recpData.getTv_p_telefon());
    }

    public void findView(){
        btn_foto_gruza=findViewById(R.id.btn_foto_gruza);
        scrollView=findViewById(R.id.scrollView);
        rlZakazchik = findViewById(R.id.rl_zakazchik);
        rlOtpravitel = findViewById(R.id.rl_otpravitel);
        rlPoluchatel = findViewById(R.id.rl_poluchatel);
        showOtpravitel = findViewById(R.id.btn_otpravitel);
        showPoluchatel = findViewById(R.id.btn_poluchatel);
        showZakazchik = findViewById(R.id.btn_zakazchik);
        btn_dokumenty= findViewById(R.id.btn_dokumenty);
        btn_sohranit=findViewById(R.id.btn_sohranit);
        btn_otmenit=findViewById(R.id.btn_otmenit);

        tv_code=findViewById(R.id.tv_code);
        tv_date=findViewById(R.id.tv_date);
        autoComplete_zakazchik=findViewById(R.id.autoComplete_zakazchik);
        mactv_z_pochta=findViewById(R.id.mactv_z_pochta);
        mactv_z_dogovor=findViewById(R.id.mactv_z_dogovor);
        mactv_z_otprav=findViewById(R.id.mactv_z_otprav);
        mactv_z_otkuda=findViewById(R.id.mactv_z_otkuda);
        mactv_z_adres=findViewById(R.id.mactv_z_adres);
        mactv_z_kontakt=findViewById(R.id.mactv_z_kontakt);
        mactv_z_telefon=findViewById(R.id.mactv_z_telefon);
        mactv_p_poluch=findViewById(R.id.mactv_p_poluch);
        mactv_date_edit=findViewById(R.id.mactv_date_edit);
        mactv_date_edit.setEnabled(false);
        et_p_kolich=findViewById(R.id.et_p_kolich);
        et_p_ves=findViewById(R.id.et_p_ves);
        et_p_obiem=findViewById(R.id.et_p_obiem);
        et_f_kolich=findViewById(R.id.et_f_kolich);
        et_f_kolich.setEnabled(false);
        et_f_ves=findViewById(R.id.et_f_ves);
        et_f_ves.setEnabled(false);
        et_f_obiem=findViewById(R.id.et_f_obiem);
        et_f_obiem.setEnabled(false);
        autoComplete_otkuda=findViewById(R.id.autoComplete_otkuda);
        autoComplete_kuda=findViewById(R.id.autoComplete_kuda);
        mactv_info=findViewById(R.id.mactv_info);
        mactv_vid=findViewById(R.id.mactv_vid);
        mactv_dostavka=findViewById(R.id.mactv_dostavka);
        mactv_stoimost=findViewById(R.id.mactv_stoimost);
        mactv_status=findViewById(R.id.mactv_status);
        et_kommentar=findViewById(R.id.et_kommentar);
        mactv_stoimost.setEnabled(false);
        mactv_status.setEnabled(false);

        mactv_p_otkuda=findViewById(R.id.mactv_p_otkuda);
        mactv_p_adres=findViewById(R.id.mactv_p_adres);
        mactv_p_kontakt=findViewById(R.id.mactv_p_kontakt);
        mactv_p_telefon=findViewById(R.id.mactv_p_telefon);
    }

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

    private View.OnClickListener clickOtmena = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(EditReceptionManagerActivity.this,ReceptionManagerActivity.class);
            startActivity(intent);
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
