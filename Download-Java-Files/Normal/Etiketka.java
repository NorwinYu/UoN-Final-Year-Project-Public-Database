package com.example.usk.glotus_final.SuperviserApp.ReceptionFiles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.BluetoothPrintService.Other;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.SuperviserListActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Etiketka extends AppCompatActivity{
    private MenuItem btn_next;
    private PdfData data;
    private LinearLayout firstPageLayout;
    private LinearLayout firstPartLayout;
    private LinearLayout secondPartLayout;
    private TextView mesto;
    private int kolvoMest;

    RelativeLayout relativeLayout;
    Button send;
    File imageFile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etiketka);

        Intent intent=getIntent();
        data=(PdfData) intent.getExtras().getSerializable("pdfData");
        buildText(data);
        kolvoMest=Integer.parseInt(data.getKolvoMest());

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                save1();
                printDocument(imageFile,kolvoMest+1);
            }
        });
    }

    public void buildText(PdfData pd){
        TextView gorodOtkuda=findViewById(R.id.gorod_otkuda);
        TextView gorodKuda=findViewById(R.id.gorod_kuda);
        TextView imya_otprav=findViewById(R.id.imya_otprav);
        TextView imya_poluch=findViewById(R.id.imya_poluch);
        TextView kolichMest=findViewById(R.id.kolich);
        TextView ves=findViewById(R.id.ves);
        TextView obiem=findViewById(R.id.obiem);
        TextView raspechatal=findViewById(R.id.admin);
        TextView idAndDate=findViewById(R.id.date);
        TextView gorodOtkuda1=findViewById(R.id.gorod_otkuda1);
        TextView gorodKuda1=findViewById(R.id.gorod_kuda1);
        TextView imya_otprav1=findViewById(R.id.imya_otprav1);
        TextView imya_poluch1=findViewById(R.id.imya_poluch1);
        mesto=findViewById(R.id.mesto);
        TextView raspechatal1=findViewById(R.id.admin1);
        TextView idAndDate1=findViewById(R.id.date1);

        relativeLayout=findViewById(R.id.relative1);
        firstPageLayout=findViewById(R.id.mainLay);
        firstPartLayout=findViewById(R.id.linLay);
        secondPartLayout=findViewById(R.id.linLay1);
        send = findViewById(R.id.send);

        gorodOtkuda.setText(pd.getFromCity());
        gorodKuda.setText(pd.getToCity());
        imya_otprav.setText(pd.getOtpravitel());
        imya_otprav1.setText(pd.getOtpravitel());
        imya_poluch.setText(pd.getPoluchatel());
        imya_poluch1.setText(pd.getPoluchatel());
        kolichMest.setText(pd.getKolvoMest());
        ves.setText(pd.getVes());
        obiem.setText(pd.getObiem());
        raspechatal.setText(pd.getRasp());
        idAndDate.setText(pd.getNumZakaz()+" от "+pd.getDate());
        gorodOtkuda1.setText(pd.getFromCity());
        gorodKuda1.setText(pd.getToCity());
        mesto.setText("1 из "+pd.getKolvoMest());
        raspechatal1.setText(pd.getRasp());
        idAndDate1.setText(pd.getNumZakaz()+" от "+pd.getDate());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.pdf_menu, menu);

        btn_next=menu.findItem(R.id.btn_next);
        btn_next.setVisible(true);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.btn_next){
            Intent myIntent = new Intent(Etiketka.this, ExpedPage.class);
            myIntent.putExtra("pdfExped",data);
            startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public Bitmap getViewBitmap(View v){
        v.setDrawingCacheEnabled(true);
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);

        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save1 (){
        Bitmap bitmap=getViewBitmap(firstPartLayout);
        Bitmap secondPart=getViewBitmap(secondPartLayout);
        int width = 280;
        int height = 280;

        Bitmap rszBitmap = resizeBitmap(bitmap,width,height);
        Bitmap rszBitmap2= resizeBitmap(secondPart,width,height);

        int xx=rszBitmap.getWidth();
        int yy=rszBitmap.getHeight();
        int secXX=rszBitmap2.getWidth();
        int secYY=rszBitmap2.getHeight();

        PrintAttributes printAttrs = new PrintAttributes.Builder().
                setColorMode(PrintAttributes.COLOR_MODE_COLOR).
                setMediaSize(PrintAttributes.MediaSize.NA_LETTER).
                setMinMargins(PrintAttributes.Margins.NO_MARGINS).
                build();
        PrintedPdfDocument document = new PrintedPdfDocument(this,printAttrs);

        for(int i=0;i<kolvoMest+1;i++) {
            if(i==0){
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(xx, yy, i + 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                Canvas canvas=page.getCanvas();
                canvas.drawBitmap(rszBitmap,0,0,null);
                document.finishPage(page);
            }else if(i>0){
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(secXX, secYY, i + 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                mesto.setText(i+" из "+kolvoMest);
                Canvas canvas=page.getCanvas();
                canvas.drawBitmap(rszBitmap2,0,0,null);
                document.finishPage(page);
            }
        }

        File mFolder;
        String fileName="Etiketka.pdf";
        try {
            mFolder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            imageFile = new File(mFolder,fileName);
            if (!mFolder.exists()) {
                mFolder.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(imageFile);
            document.writeTo(out);
            document.close();
            out.close();
            Toast.makeText(this,"Результат сохранен", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "При сохранении возникла ошибка", Toast.LENGTH_LONG).show();
        }
    }

    public Bitmap resizeBitmap(Bitmap bitmap, int newWidth,int newHeight){
        Bitmap resizedBitmap;
        resizedBitmap = Other.resizeImage(bitmap, newWidth, newHeight);
        return resizedBitmap;
    }

    public void printDocument(File file,int totalPage){
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        String jobName = this.getString(R.string.app_name) + " Document";
        printManager.print(jobName, new MyPrintDocumentAdapter(file,totalPage), null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888,true);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
        return resizedBitmap;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Etiketka.this, SuperviserListActivity.class);
        startActivity(myIntent);
    }
}
