package com.example.usk.glotus_final.SuperviserApp.ReceptionFiles;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.SuperviserListActivity;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.ZayavkaListAdapter;
import com.example.usk.glotus_final.System.Catalog.Kontragent;
import com.example.usk.glotus_final.System.Catalog.KontragentNum;
import com.example.usk.glotus_final.System.Catalog.Podrazd;
import com.example.usk.glotus_final.System.Catalog.Transport;
import com.example.usk.glotus_final.System.Encryption.AES;
import com.example.usk.glotus_final.System.connection.Server;
import com.example.usk.glotus_final.System.connection.ServerPhoto;
import com.example.usk.glotus_final.System.loginFiles.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/*
* there some elements that
*
* */
public class Reception extends AppCompatActivity {
    private static final String TAG = "a";
    static int cm=0;
    public static ArrayList<String> singleAddress = new ArrayList<String>();
    public static ArrayList<String> adress = new ArrayList<String>();
    static PdfData pd;
    static Spinner upakovka;
    private LinearLayout layToHide;
    private Spinner soprDocument;
    private AutoCompleteTextView transportType;
    static TextView foto_kol;
    private TextView numZakaz, date, zakazchik, otpravitel, poluchatel, manager,podrazdelenie, soprDoc;
    private EditText dateToFill, vesFact, obiemFact, kolich, komentToFill,comment;
    private CheckBox gruz;
    private Button save,delete,etiketka,saveBtn;
    private ImageView img,img1,img2;
    private RelativeLayout relativeLayout;
    private TextView tv_zakazchik_nomer;
    private LinearLayout hide_lay;
    static Integer fotokol=0;
    private TextView kolichFotok;
    private EditText sopnumber;
    private EditText sopdate;
    private DatePickerDialog.OnDateSetListener mDateListener;
    String ttt="";
    boolean ch=false;

    Boolean damage=false;
    private String msg= "Уважаемый клиент, обращаем Ваше внимание, что в результате приемки груза были выявлены повреждения упаковки (см. фото),\n" +
            "\n" +
            " по всем вопросам связывайтесь с Вашим менеджером Администратор тел.";
    private String trkey="00000000-0000-0000-0000-000000000000";

    //новые переменные для фоток
    private String imagePath; //путь к фоткам
    private String imageName; //название фоток
    private static ArrayList<Image> arr=new ArrayList<>(); //arraylist для фоток
    private static final int REQUEST_CODE=1;
    static final int REQUEST_TAKE_PHOTO = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        relativeLayout=findViewById(R.id.relativeLay);

        //System.out.println(ZayavkaListAdapter.item.getMenedjer());
        setContentView(R.layout.activity_reception);

        hide_lay=findViewById(R.id.hide_lay);

        layToHide=findViewById(R.id.lay_to_hide);
        foto_kol=findViewById(R.id.foto_kol);

        comment=findViewById(R.id.tv_comment);
        zakazchik=findViewById(R.id.tv_zakazchic);
        zakazchik.setText((String)Kontragent.kontrpreferences.getAll().get(ZayavkaListAdapter.item.getZakaz()));

        numZakaz=findViewById(R.id.tv_nomer);
        numZakaz.setText(ZayavkaListAdapter.item.getNumber());

        date=findViewById(R.id.tv_data);
        date.setText(ZayavkaListAdapter.item.getDate().split("T")[0]);

        vesFact=findViewById(R.id.et_ves_fact);
        obiemFact=findViewById(R.id.et_obem_fact);
        kolich=findViewById(R.id.et_kolich);

        sopdate=findViewById(R.id.sopdate);
        sopnumber=findViewById(R.id.sopnum);

        otpravitel=findViewById(R.id.tv_otpravitel);
        otpravitel.setText(ZayavkaListAdapter.item.getSender());
        sopnumber.setInputType(InputType.TYPE_CLASS_NUMBER  );

        poluchatel=findViewById(R.id.tv_poluchatel);
        poluchatel.setText(ZayavkaListAdapter.item.getRecept());
        sopdate.setInputType(InputType.TYPE_NULL);

        manager=(TextView)findViewById(R.id.tv_menedzher);
        manager.setText(ZayavkaListAdapter.item.getMenedjer());
        sopdate.setInputType(InputType.TYPE_NULL);
        sopdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Reception.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateListener,year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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
                sopdate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };



        podrazdelenie=findViewById(R.id.tv_podrazdel);
        podrazdelenie.setText((String)Podrazd.pdpreferences.getAll().get(ZayavkaListAdapter.item.getPodrazd()));

        kolichFotok=findViewById(R.id.kolich_fotok);

        soprDocument=findViewById(R.id.spinner_soprDoc);
        String[] itemsForSop=new String[]{"","Транспортная накладная","Товарно-транспортная накладная",
                "Универсально-передаточный документ","Счет фактура","Накладная",
                "Расходная накладная","INVOICE","другое"};
        ArrayAdapter<String> adapterForSop=new ArrayAdapter<String>(this,R.layout.spinner_item, itemsForSop);
        adapterForSop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soprDocument.setAdapter(adapterForSop);

        transportType=findViewById(R.id.spin_transport);
        setTransportSpinner(transportType);

        upakovka=findViewById(R.id.spin_upakovka);
        String[] items=new String[]{"Без упаковки","Ящик","Паллет","Короб","Мешок","Другое"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upakovka.setAdapter(adapter);

        komentToFill=findViewById(R.id.et_message_povrezhden);
        gruz=findViewById(R.id.cb_gruz_povrezhden);
        gruz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_cb(view);
            }
        });

        //it it takes a photo of gruz
        img=findViewById(R.id.iw_camera_icon);
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //когда нажимаешь сразу открывает камеру
                Intent pictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(pictureIntent.resolveActivity(getPackageManager())!=null){
                    File photoFile=null;
                    try{
                        photoFile=createImageFile();
                    }catch (IOException ex){

                    }
                    if(photoFile!=null){
                        Uri photoUri= FileProvider.getUriForFile(Reception.this,getPackageName() +".provider",photoFile);
                        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                        startActivityForResult(pictureIntent,REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });

        //here I will change, this is delete button for gruz;
        delete=findViewById(R.id.del);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (arr.size()>0){
                    arr.remove(arr.size()-1);
                    foto_kol.setText(String.valueOf(arr.size()));
                }
            }
        });

        saveBtn=findViewById(R.id.btn_otpr);
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(kolich.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Количество мест не должно быть пустым", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        try {
                            posting();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
            }
        });

        //когда нажимаешь он передает объекты картинок на ImageViewer
        kolichFotok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(arr.size()==0){
                    Toast.makeText(getApplicationContext(),"No any photo",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Reception.this, ImageViewer.class);
                    intent.putExtra("imageData", arr);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
    }

    // берет данные с intent камеры
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: picture taken successfully");
                arr.add(new Image(imageName,imagePath));
                foto_kol.setText(String.valueOf(arr.size()));
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //создает фотку и сохраняет ее на телефон
    private File createImageFile() throws IOException{
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="JPEG_"+timeStamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image =File.createTempFile(imageFileName,".jpg",storageDir);
        imagePath=image.getAbsolutePath();
        imageName=imageFileName+".jpg";
        return image;
    }

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


    public void uploadingfile(){

        for(int i=0;i<arr.size();i++){
            String encodedImage="";
            try {
                FileInputStream inputStream = new FileInputStream(arr.get(i).getImagePath().toString());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                encodedImage=imagetobase64(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(encodedImage);
            upload(encodedImage,i+1);
        }
        for (int i=0;i<adress.size();i++){
            System.out.println(adress.get(i).toString());
        }
    }

    public void upload(String encodedImage, int i) {
        encodedImage=encodedImage.replace("/","%2F").replace("+","%2B");
        String body = "image="+encodedImage+"&key="+ZayavkaListAdapter.item.getNumber().toString()+"&iter="+i;
        ServerPhoto sr= new ServerPhoto("http://185.209.23.53/upload/uploaded.php",null,body);

        adress.add(sr.post().replace("<","").replace("\\","\\\\").replace("/","\\\\"));
        System.out.println(sr.getRes());
        System.out.println("7887");

    }

    public String imagetobase64(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,30,outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void posting() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, JSONException {
        final ProgressDialog progressDialog = new ProgressDialog(Reception.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        pposting();
    }

        //here some mistakes, look throw
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pposting() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, JSONException {
        uploadingfile();

        String images="\"Изображения\" : [";
        for(int i=0;i<adress.size();i++) {
            System.out.println(adress.get(i));
            int c=i+1;
            images += "{\"LineNumber\":  \"" + c + "\", \"ПутьКИзображению\": \"" + adress.get(i) + "\"}";
            if (i!=adress.size() - 1)
                images+=",";
        }

        images+="]";
        System.out.println(images);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime()).replace("_","T");

        String ssp=soprDocument.getSelectedItem().toString()+" от " +sopnumber.getText().toString()+" "+sopdate.getText().toString();
        if (soprDocument.getSelectedItem().toString().length()>1 && sopnumber.getText().toString().length()>=1 && sopdate.getText().toString().length()>1)
            ssp="[{\"LineNumber\": \"1\",\"СопроводительныйДокумент\": \""+soprDocument.getSelectedItem().toString()+"\",\"НомерСД\": \""+sopnumber.getText().toString()+"\",\"ДатаСД\": \""+ttt+"00:00:00\"}}";
        else
          ssp="[{}]";

        String body="{\n" +
                "    \"КоличествоФакт\": \""+kolich.getText().toString()+"\",\n" +
                "    \"ВесФакт\": \""+vesFact.getText().toString()+"\",\n" +
                "    \"ОбъемФакт\": \""+obiemFact.getText().toString()+"\",\n" +
                "    \"ГрузПоврежден\": "+damage.toString()+",\n" +
                "    \"Posted\": true,\n" +
                "    \"Заказчик_Key\": \""+ZayavkaListAdapter.item.getZakaz()+"\",\n" +
                "    \"Упаковка\": \""+upakovka.getSelectedItem().toString()+"\",\n" +
                "    \"Date\": \""+timeStamp+"\"    ,\n" +
                "    \"Транспорт_Key\": \""+trkey+"\",\n" +
                "    \"ДокументОснования_Key\": \""+ZayavkaListAdapter.item.getRef_key()+"\",\n" +
              //      "    \"Менеджер_Key\": \""+ZayavkaListAdapter.item.getMenedjer()+"\",\n" +
                "    \"Отправитель\": \""+ZayavkaListAdapter.item.getSender().replace("\"","\\\"")+"\",\n" +
                "    \"Подразделение_Key\": \""+ZayavkaListAdapter.item.getPodrazd()+"\",\n" +
                "    \"Комментарий\": \""+comment.getText().toString()+"\",\n" +
                "    \"Фото_Type\": \"application/image/jpeg\",\n" +
                "    \"Письмо\": \""+komentToFill.getText().toString()+"\",\n" +
                "    \"ПисьмоОтправлено\": false,\n"+
                "    \"Док\": "+ssp+","+
                     images+
                " }";
        Log.d("aa",body);
        System.out.println(body);
        System.out.println(body);

        String res=process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%9F%D1%80%D0%B8%D0%B5%D0%BC%D0%9D%D0%B0%D0%A1%D0%BA%D0%BB%D0%B0%D0%B4?$format=json","POST", User.getCredential(),body);
        System.out.println(res);
        JSONArray array = null;
        JSONObject jsonObj=null;
        try {
            jsonObj = new JSONObject(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObj.getString("Ref_Key").toString());

        res=process("http://185.209.23.53/InfoBase/odata/standard.odata/Document_%D0%97%D0%B0%D0%BA%D0%B0%D0%B7(guid\'"+ZayavkaListAdapter.item.getRef_key()+"\')?$format=json","PATCH","Basic 0JDQtNC80LjQvdC40YHRgtGA0LDRgtC+0YA6MTIz",
                    "{\"ДокументПриемГруза_Key\": \""+jsonObj.getString("Ref_Key").toString()+"\",\"СтатусЗаказа\": \"ПринятноНаСкладе\",\"ВесФакт\":\""+vesFact.getText().toString()+"\",\"ОбъемФакт\": \""+obiemFact.getText().toString()+"\",\"КоличествоФакт\": \""+kolich.getText().toString()+"\"}");

        System.out.println(res);
        pd=new PdfData(ZayavkaListAdapter.item.getSenderadr(),ZayavkaListAdapter.item.getReceptadr(),ZayavkaListAdapter.item.getRecept(),ZayavkaListAdapter.item.getSender(),kolich.getText().toString(),vesFact.getText().toString(),
                    obiemFact.getText().toString(),"AUTO","Админ", ZayavkaListAdapter.item.getNumber().toString(),ZayavkaListAdapter.item.getDate().toString(),
                    " ");

        Intent myIntent = new Intent(Reception.this, Etiketka.class);
        myIntent.putExtra("pdfData", pd);
        startActivity(myIntent);
    }

    public void setTransportSpinner(final AutoCompleteTextView spinner){
        final List<String> list = new ArrayList<String>();
        final List<String> rlist = new ArrayList<String>();

        list.add("Выберите:");
        rlist.add("");

        for (Map.Entry<String, ?> entry : Transport.trpreferences.getAll().entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            list.add((String) entry.getValue());
            rlist.add(entry.getKey());
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(arrayAdapter);
                spinner.setWidth(50);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                trkey=(rlist.get(list.indexOf(spinner.getText().toString())));
            }


        });
    }

    public void check_cb(View view){
        if(gruz.isChecked()){
            System.out.println("damaged ");
            damage=true;
            komentToFill.setText(msg);
            layToHide.setVisibility(View.VISIBLE);
        }
        else{
            damage=false;
            komentToFill.setText("");
            layToHide.setVisibility(View.GONE);
        }
        System.out.println(damage.toString());
    }
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Reception.this, SuperviserListActivity.class);
        startActivity(myIntent);

    }
}
