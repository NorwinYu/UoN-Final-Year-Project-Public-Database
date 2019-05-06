package com.frictionhacks.tenderhaltinfo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.R;
import com.frictionhacks.tenderhaltinfo.Util.Methods;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TenderDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button btnDate,btnSubmit;
FloatingActionButton btnAddImage;
String date,weatherType,downloadUrl;
Spinner spnWeatherType;
TextView tvIsVerify,tvTenderId,tvStart,tvStop,tvLocation,tvDate;
StorageReference mStorageRef;
FirebaseFirestore db;
FirebaseAuth mAuth;
int pos;
    private String imgName;
    private String imgPath;
    private String formatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_detail);

        getSupportActionBar().setTitle("Tender Details");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        pos= Objects.requireNonNull(getIntent().getExtras()).getInt("position");
        tvTenderId=findViewById(R.id.tv_tender_detail_tenderID);
        tvStart=findViewById(R.id.tv_tender_detail_start_date);
        tvStop=findViewById(R.id.tv_tender_detail_end_date);
        tvLocation=findViewById(R.id.tv_tender_detail_location);
        tvDate=findViewById(R.id.tv_tender_detail_date);
        tvDate.setText(getString(R.string.dates));

        btnAddImage=findViewById(R.id.btn_tender_detail_image);

btnAddImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        imagePick();
    }
});

        setUpinitialData();
        btnDate=findViewById(R.id.btn_tender_detail_date);
        btnSubmit=findViewById(R.id.btn_tender_detail_submit);
        spnWeatherType=findViewById(R.id.spn_tender_detail_main);
        tvIsVerify=findViewById(R.id.tv_tender_detail_is_verify);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = Methods.getDateDialog(TenderDetailActivity.this);

                if(!date.equals("No_Date")) {
                    formatDate= Methods.unixToFormat(Long.parseLong(date));
                }
                tvDate.setText(formatDate);
                Log.d("DATE",date);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDarkSky(ContractorTenderDetailsDashboardModel.mData.get(pos).getmLat(),ContractorTenderDetailsDashboardModel.mData.get(pos).getmLong(),date);

            }
        });

    spnWeatherType.setOnItemSelectedListener(this);

        ArrayList<String> categories=new ArrayList<>();
        categories.add("rain");
        categories.add("snow");
        categories.add("other");


        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,categories);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWeatherType.setAdapter(stringArrayAdapter);
    }

    private void setUpinitialData() {
        tvTenderId.setText(ContractorTenderDetailsDashboardModel.mData.get(pos).getMtenderId());
        tvLocation.setText(ContractorTenderDetailsDashboardModel.mData.get(pos).getlocation());
        tvStart.setText(ContractorTenderDetailsDashboardModel.mData.get(pos).getmDateStart());
        tvStop.setText(ContractorTenderDetailsDashboardModel.mData.get(pos).getmDataEnd());
    }


    private void getDarkSky(double lat, double lng, final String unixDate) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.darksky.net/forecast/4ca85b12437d1c366ea7512e2a91307d/"+lat+","+lng+","+unixDate;

        Log.v("DARK SKY",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject("daily");
                                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                                    JSONObject jsonObject1=jsonArray.getJSONObject(0);

                                        Log.d("WEATHER DATA",jsonObject1.getString("precipType"));
                                 //   tvIsVerify.setText("Date is:"+unixDate+"\n precipIntensity "+jsonObject1.getString("precipIntensity")+"\nprecipType "+jsonObject1.getString("precipType"));
                                  if(jsonObject1.getString("precipType").equals(weatherType)){
                                      tvIsVerify.setText("requestPassed");
                                  }
                                  else{
                                      tvIsVerify.setText("requestnotPassed");
                                  }

                                    Map<String,Object> map=new HashMap<>();
                                    map.put("tenderID",ContractorTenderDetailsDashboardModel.mData.get(pos).getMtenderId());
                                    map.put("status",tvIsVerify.getText().toString());
                                    map.put("date",tvDate.getText().toString());
                                    map.put("img",downloadUrl);

                                    db.collection("Contractor").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber())).collection("Tenders").document(ContractorTenderDetailsDashboardModel.mData.get(pos).getMtenderId()).set(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(TenderDetailActivity.this,tvIsVerify.getText().toString(),Toast.LENGTH_SHORT).show();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(TenderDetailActivity.this, "Error uploading Data", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "DARK sky Error", Toast.LENGTH_SHORT).show();

                    }
                });
      requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        weatherType = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + weatherType, Toast.LENGTH_LONG).show();
        if(weatherType.equals("other")){
            btnAddImage.setVisibility(View.VISIBLE);
        }
        else{
            btnAddImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void imagePick(){
        ImagePicker.with(TenderDetailActivity.this)
                .setMultipleMode(false)
                .setShowCamera(true)
                .setDoneTitle("Done")
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);

            imgPath=images.get(0).getPath();
            imgName=images.get(0).getName();

            ImageView iv_tender=findViewById(R.id.iv_tender_detail);

            iv_tender.setImageURI(Uri.parse("file://"+imgPath));
            uploadImage(Uri.parse("file://"+imgPath),imgName);


        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadImage(Uri imgPath,String imgName){
        final StorageReference sRef = mStorageRef.child("images/"+imgName);

        sRef.putFile(imgPath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                  downloadUrl= uri.toString();
                        Toast.makeText(TenderDetailActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TenderDetailActivity.this, "Problem in getting image", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
