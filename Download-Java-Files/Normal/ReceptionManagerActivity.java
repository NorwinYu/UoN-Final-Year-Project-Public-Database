package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.ExpedPage;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.Image;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.ImageViewer;
import com.example.usk.glotus_final.SuperviserApp.ReceptionFiles.Reception;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.SuperviserListActivity;
import com.example.usk.glotus_final.System.Catalog.Adress;
import com.example.usk.glotus_final.System.Catalog.Kontragent;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReceptionManagerActivity extends AppCompatActivity {
    private String KeyZakaz="00000000-0000-0000-0000-000000000000",KeyOtkuda="00000000-0000-0000-0000-000000000000",KeyKuda="00000000-0000-0000-0000-000000000000";


    private static final int REQUEST_CODE=1;
    private RelativeLayout rlZakazchik, rlPoluchatel, rlOtpravitel;
    private Button showZakazchik, showPoluchatel, showOtpravitel, btn_dokumenty, btn_izmenit;
    private TextView tv_code, tv_date, tv_z_zakazchik, tv_z_pochta, tv_z_dogovor, tv_z_otprav,
                    tv_z_otkuda, tv_z_adres, tv_z_kontakt, tv_z_telefon, tv_p_poluch, tv_date_edit,
                    tv_p_kolich, tv_p_ves, tv_p_obiem, tv_f_kolich, tv_f_ves, tv_f_obiem, tv_info,
                    tv_vid, tv_dostavka, tv_stoimost, tv_status,tv_kuda,tv_otkuda,tv_kommentar,
                    tv_p_kuda,tv_p_adres,tv_p_kontakt,tv_p_telefon;
    private ReceptionData recData;
    private ReceptionData returnedData;
    private ReceptionData fromNewRecp;
    private Button btn_foto_gruza;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_reception);

        findView();

        showZakazchik.setOnClickListener(clickZakaz);
        showPoluchatel.setOnClickListener(clickPoluch);
        showOtpravitel.setOnClickListener(clickOtprav);
//      btn_dokumenty.setOnClickListener(clickDockumenty);

        Intent intent = getIntent();
        try {
            fromNewRecp = (ReceptionData) intent.getExtras().getSerializable("toReceptionData");
        }catch (Exception e){

        }
        if(fromNewRecp==null){
            setData();
        } else {
            setDataFromNewRecp();
        }

        //кнопка изменить
        btn_izmenit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIzmenitButtonClick();
            }
        });
        btn_foto_gruza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ArrayList<String> arr=new ArrayList();
                        arr=download(arr);
                Intent intent = new Intent(ReceptionManagerActivity.this, ImagesMd.class);
                System.out.println(arr.size());
                intent.putExtra("imageData", arr);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


    }

    //передает данные в EditReceptionManagerActivity
    public void onIzmenitButtonClick(){
        RefKeys.ZakazKey=KeyZakaz;
        RefKeys.KudaKey=KeyKuda;
        RefKeys.OkudaKey=KeyOtkuda;
        //сохраняет данные в объект
        recData=new ReceptionData(tv_code.getText().toString(),tv_date.getText().toString(),
                tv_z_zakazchik.getText().toString(),tv_z_pochta.getText().toString(),tv_z_dogovor.getText().toString(),
                tv_z_otprav.getText().toString(),tv_z_otkuda.getText().toString(),tv_z_adres.getText().toString(),
                tv_z_kontakt.getText().toString(),tv_z_telefon.getText().toString(),tv_p_poluch.getText().toString(),
                tv_date_edit.getText().toString(),tv_kuda.getText().toString(),tv_p_kolich.getText().toString(),tv_p_ves.getText().toString(),
                tv_p_obiem.getText().toString(),tv_f_kolich.getText().toString(),tv_f_ves.getText().toString(),
                tv_f_obiem.getText().toString(),tv_info.getText().toString(),tv_vid.getText().toString(),
                tv_stoimost.getText().toString(),tv_status.getText().toString(),
                tv_kommentar.getText().toString(),
                tv_p_kuda.getText().toString(),tv_p_adres.getText().toString(),tv_p_kontakt.getText().toString(),
                tv_p_telefon.getText().toString());

        //передает объект в EditReceptionManagerActivity через Intent
        Intent intent=new Intent(ReceptionManagerActivity.this, EditReceptionManagerActivity.class);
        intent.putExtra("receptionData",recData);
        startActivityForResult(intent,REQUEST_CODE);
    }

    //принимает измененные данные с EditReceptionManagerActivity
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                returnedData=(ReceptionData) data.getExtras().getSerializable("changedData");
                setReturnedData(returnedData);
                saveDataToDB();
            }
        }
    }

    public ArrayList<String> download(final ArrayList<String> arr){

        String url=("http://185.209.23.53/upload/getimages.php");
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "number="+"ALA004807");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String rss=(response.body().string());
                System.out.println(rss);
                if (response.isSuccessful()) {
                    final String s[] =rss.split("-------------------");
                    for (int i=0;i<s.length;i++)
                    {
                        System.out.println(s[i]);

                        arr.add(s[i]);

                    }



                }
            }});
        //  progressBar.setVisibility(View.INVISIBLE);
        return arr;
    }
    //поменяет данные фронта по returnedData, и ты должен поменять данные базы с этими
    public void setReturnedData(ReceptionData returnedData){
        try {
            KeyZakaz = RefKeys.ZakazKey;
            tv_code.setText(returnedData.getCode());
            tv_date.setText(returnedData.getDate().split("T")[0]);
            tv_z_zakazchik.setText(returnedData.getZakazchik());
            tv_z_pochta.setText(returnedData.getPochta());
            tv_z_dogovor.setText(returnedData.getDogovor());
            tv_z_otprav.setText(returnedData.getOtpavitel());
            tv_z_otkuda.setText(returnedData.getOtkuda());
            tv_z_adres.setText(returnedData.getAddress());
            tv_z_kontakt.setText(returnedData.getKontakt());
            tv_z_telefon.setText(returnedData.getTelefon());
            tv_p_poluch.setText(returnedData.getPoluchatel());
            tv_date_edit.setText(returnedData.getDateEdit());
            tv_otkuda.setText(returnedData.getOtkuda());
            tv_kuda.setText(returnedData.getKuda());
            tv_p_kolich.setText(returnedData.getPlanKolich());
            tv_p_ves.setText(returnedData.getPlanVes());
            tv_p_obiem.setText(returnedData.getPlanObiem());
            tv_f_kolich.setText(returnedData.getFactKolich());
            tv_f_ves.setText(returnedData.getFactVes());
            tv_f_obiem.setText(returnedData.getFactObiem());
            tv_info.setText(returnedData.getInfo());
            tv_vid.setText(returnedData.getVid());
            tv_dostavka.setText(returnedData.getDostavka());
            tv_stoimost.setText(returnedData.getStoimost());
            tv_status.setText(returnedData.getStatus());
            tv_kommentar.setText(returnedData.getKomment());
            tv_p_poluch.setText(returnedData.getPoluchatel());
            tv_p_adres.setText(returnedData.getTv_p_adres());
            tv_p_kuda.setText(returnedData.getKuda());
            tv_p_telefon.setText(returnedData.getTv_p_telefon());
            tv_p_kontakt.setText(returnedData.getTv_p_kontakt());

            CharSequence hint = showZakazchik.getHint();
            showZakazchik.setHint(hint + returnedData.getZakazchik());
            hint = showOtpravitel.getHint();
            showOtpravitel.setHint(hint + returnedData.getOtpavitel());
            hint = showPoluchatel.getHint();
            showPoluchatel.setHint(hint + returnedData.getPoluchatel());

        }catch(Exception e){
            e.getCause();
        }

    }

    public void setData(){
        //перед тем как поставить данные на фронт, ты должен их взять с базы данных
        //

        ManagerZayavka zayavka=ManagerListAdapter.item;
        KeyZakaz=zayavka.getZakaz();
        KeyOtkuda=zayavka.getSenderadr();
        KeyKuda=zayavka.getReceptadr();
        RefKeys.Ref_Key=zayavka.getRef_key();
        RefKeys.Status=tv_status.getText().toString();
        RefKeys.vidpr=tv_vid.getText().toString();
        tv_p_poluch.setGravity(Gravity.START);

        tv_code.setText(zayavka.getNumber());
        tv_date.setText(zayavka.getDate().split("T")[0]);
        tv_z_zakazchik.setText((CharSequence) Kontragent.kontrpreferences.getAll().get(zayavka.getZakaz()));
        tv_z_pochta.setText(zayavka.getPochta());
        tv_z_dogovor.setText(zayavka.getNomerdogovor());
        tv_z_otprav.setText(zayavka.getSender());
        tv_z_otkuda.setText((CharSequence) Adress.adresspreferences.getAll().get(zayavka.getSenderadr()));
        tv_z_adres.setText(zayavka.getAdresotp());
        tv_z_kontakt.setText(zayavka.getLisootprav());
        tv_z_telefon.setText(zayavka.getTelefonOtprav());
        tv_p_poluch.setText(zayavka.getRecept());
        tv_date_edit.setText(zayavka.getDate());
        tv_otkuda.setText((CharSequence) Adress.adresspreferences.getAll().get(zayavka.getSenderadr()));
        tv_kuda.setText((CharSequence) Adress.adresspreferences.getAll().get(zayavka.getReceptadr()));

        tv_p_kolich.setText(zayavka.getKolplan());
        tv_p_ves.setText(zayavka.getVesplan());
        tv_p_obiem.setText(zayavka.getObiemplan());
        tv_f_kolich.setText(zayavka.getKolfact());
        tv_f_ves.setText(zayavka.getVesfact());
        tv_f_obiem.setText(zayavka.getObiemfact());
        tv_info.setText(zayavka.getNamegruz());
        tv_vid.setText(zayavka.getVidpere());
//        tv_dostavka.setText("dostavka");
        tv_stoimost.setText(zayavka.getPriceorder());
        tv_status.setText(zayavka.getStatusorder());
        tv_kommentar.setText(zayavka.getComment());

        tv_p_kuda.setText((CharSequence) Adress.adresspreferences.getAll().get(zayavka.getReceptadr()));
        tv_p_adres.setText(zayavka.getAdrespol());
        tv_p_kontakt.setText(zayavka.getLisoplouch());
        tv_p_telefon.setText(zayavka.getTelefonOtprav());
    }

    public void setDataFromNewRecp(){
        try {
            tv_code.setText(fromNewRecp.getCode());
            tv_date.setText(fromNewRecp.getDate().split("T")[0]);
            tv_z_zakazchik.setText(fromNewRecp.getZakazchik());
            tv_z_pochta.setText(fromNewRecp.getPochta());
            tv_z_dogovor.setText(fromNewRecp.getDogovor());
            tv_z_otprav.setText(fromNewRecp.getOtpavitel());
            tv_z_otkuda.setText(fromNewRecp.getOtkuda());
            tv_z_adres.setText(fromNewRecp.getAddress());
            tv_z_kontakt.setText(fromNewRecp.getKontakt());
            tv_z_telefon.setText(fromNewRecp.getTelefon());
            tv_p_poluch.setText(fromNewRecp.getPoluchatel());
            tv_date_edit.setText(fromNewRecp.getDate());
            tv_otkuda.setText(fromNewRecp.getOtkuda());
            tv_kuda.setText(fromNewRecp.getKuda());

            tv_p_kolich.setText(fromNewRecp.getPlanKolich());
            tv_p_ves.setText(fromNewRecp.getPlanVes());
            tv_p_obiem.setText(fromNewRecp.getPlanObiem());
            tv_f_kolich.setText(fromNewRecp.getFactKolich());
            tv_f_ves.setText(fromNewRecp.getFactVes());
            tv_f_obiem.setText(fromNewRecp.getPlanObiem());
            tv_info.setText(fromNewRecp.getInfo());
            tv_vid.setText(fromNewRecp.getVid());
            tv_dostavka.setText("dostavka");
            tv_stoimost.setText(fromNewRecp.getStoimost());
            tv_status.setText(fromNewRecp.getStatus());
            tv_kommentar.setText(fromNewRecp.getKomment());

            tv_p_kuda.setText(fromNewRecp.getKuda());
            tv_p_adres.setText(fromNewRecp.getTv_p_adres());
            tv_p_kontakt.setText(fromNewRecp.getTv_p_kontakt());
            tv_p_telefon.setText(fromNewRecp.getTv_p_telefon());
        }catch (Exception e){

        }
    }

    public void saveDataToDB(){
        if (returnedData!=null)
        if(recData.toString()!=returnedData.toString()){
            System.out.println("123213");
            //сохраняем весь объект на базу
        }else{
            System.out.println("11111");
            //ниче не надо сохранять
        }
    }

    public void findView(){
        btn_foto_gruza=findViewById(R.id.btn_foto_gruza);
        tv_stoimost=findViewById(R.id.tv_stoimost);
        rlZakazchik = findViewById(R.id.rl_zakazchik);
        rlOtpravitel = findViewById(R.id.rl_otpravitel);
        rlPoluchatel = findViewById(R.id.rl_poluchatel);
        showOtpravitel = findViewById(R.id.btn_otpravitel);
        showPoluchatel = findViewById(R.id.btn_poluchatel);
        showZakazchik = findViewById(R.id.btn_zakazchik);
        btn_dokumenty=findViewById(R.id.btn_dokumenty);
        btn_izmenit=findViewById(R.id.btn_izmenit);
        tv_code=findViewById(R.id.tv_code);
        tv_date=findViewById(R.id.tv_date);
        tv_z_zakazchik=findViewById(R.id.tv_z_zakazchik);
        tv_z_pochta=findViewById(R.id.tv_z_pochta);
        tv_z_dogovor=findViewById(R.id.tv_z_dogovor);
        tv_z_otprav=findViewById(R.id.tv_z_otprav);
        tv_z_otkuda=findViewById(R.id.tv_);
        tv_z_adres=findViewById(R.id.tv_z_adres);
        tv_z_kontakt=findViewById(R.id.tv_z_kontakt);
        tv_z_telefon=findViewById(R.id.tv_z_telefon);
        tv_p_poluch=findViewById(R.id.tv_p_poluch);
        tv_date_edit=findViewById(R.id.tv_date_edit);
        tv_otkuda=findViewById(R.id.tv_otkuda);
        tv_kuda=findViewById(R.id.tv_kuda);
        tv_p_kolich=findViewById(R.id.tv_p_kolich);
        tv_p_ves=findViewById(R.id.tv_p_ves);
        tv_p_obiem=findViewById(R.id.tv_p_obiem);
        tv_f_kolich=findViewById(R.id.tv_f_kolich);
        tv_f_ves=findViewById(R.id.tv_f_ves);
        tv_f_obiem=findViewById(R.id.tv_f_obiem);
        tv_info=findViewById(R.id.tv_info);
        tv_vid=findViewById(R.id.tv_vid);
        tv_status=findViewById(R.id.tv_status);
        tv_kommentar=findViewById(R.id.tv_kommentar);
        tv_p_kuda=findViewById(R.id.tv_p_kuda);
        tv_p_adres=findViewById(R.id.tv_p_adres);
        tv_p_kontakt=findViewById(R.id.tv_p_kontakt);

        tv_p_telefon=findViewById(R.id.tv_p_telefon);

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

    private View.OnClickListener clickDockumenty = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(ReceptionManagerActivity.this, ManagerListActivity.class);
        startActivity(myIntent);
    }

}
