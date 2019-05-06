package com.frictionhacks.tenderhaltinfo.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.DataWord.NotificationWord;
import com.frictionhacks.tenderhaltinfo.DataWord.TenderDetailWord;
import com.frictionhacks.tenderhaltinfo.Fragments.MapFragment;
import com.frictionhacks.tenderhaltinfo.R;
import com.frictionhacks.tenderhaltinfo.Util.Methods;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class AddTenderActivity extends AppCompatActivity implements MapFragment.LatLngClickListener {
    String unixDate;
    String stg;
    Button startDate,stopDate;
    FirebaseFirestore db;
    String loc;
    TextView tv;
    FirebaseAuth mAuth;
    MapFragment mapFragment;
    LatLng location;
    FrameLayout fm;
    EditText etTenderId;
    String stringEndDate, stringStartDate;

    Button btnLoctSelect;
    private String formatStartDate;
    private String formatStopDate;
    private String formatDateStart;
    private String formatDateEnd;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fm.setVisibility(View.GONE);
        btnLoctSelect.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tender);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Tender");
        tv=findViewById(R.id.tv_map_location);
       mAuth=FirebaseAuth.getInstance();
        startDate=findViewById(R.id.btn_tender_start_date);
        stopDate=findViewById(R.id.btn_tender_stop_date);
        db = FirebaseFirestore.getInstance();

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringStartDate = Methods.getDateDialog(AddTenderActivity.this);

                if(!stringStartDate.equals("No_Date")) {
                    formatDateStart = Methods.unixToFormat(Long.parseLong(stringStartDate));
                }
                startDate.setText(formatDateStart);

            }
        });


        stopDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringEndDate = Methods.getDateDialog(AddTenderActivity.this);
                if(!stringEndDate.equals("No_Date")) {
                    formatDateEnd = Methods.unixToFormat(Long.parseLong(stringEndDate));
                }
                stopDate.setText(formatDateEnd);
            }
        });


        mapFragment = new MapFragment();
        Button button = findViewById(R.id.bt_add_tender_detail_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    submitData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        btnLoctSelect = findViewById(R.id.btn_add_tender_open_location);

        fm = findViewById(R.id.mapContainer);
        fm.setVisibility(View.GONE);
        etTenderId = findViewById(R.id.ed_add_activity_tender_name);
        ImageView btnMapOpen = findViewById(R.id.btn_map_tender_open);
        btnMapOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoctSelect.setVisibility(View.VISIBLE);
                fm.setVisibility(View.VISIBLE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.mapContainer, mapFragment);
                ft.addToBackStack("file");
                ft.commit();

            }
        });
        btnLoctSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                btnLoctSelect.setVisibility(View.GONE);
                ft.remove(mapFragment);
                ft.commit();
            }
        });

        mapFragment.setLatLng(AddTenderActivity.this);
    }

    private void submitData() throws InterruptedException {
        loc = getReverseLocation(location.latitude,location.longitude);
        if((!etTenderId.getText().toString().isEmpty())&&(location !=null)&&(stringStartDate!=null)&&(stringEndDate!=null)){
        ContractorTenderDetailsDashboardModel.mData.add(new TenderDetailWord(etTenderId.getText().toString(),location.latitude,location.longitude,"dateStart","dateEnd",1,tv.getText().toString()));
       final TenderDetailWord submitData= new TenderDetailWord(etTenderId.getText().toString(),location.latitude,location.longitude,stringStartDate,stringEndDate,1,tv.getText().toString());

        db.collection("Contractor").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber())).collection("Tenders").document(submitData.getMtenderId())
                .set(submitData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddTenderActivity.this,"updated",Toast.LENGTH_SHORT).show();

                db.collection("Notification").document(Objects.requireNonNull(mAuth.getCurrentUser().getPhoneNumber())).collection("Notifications")
                        .document(submitData.getMtenderId())
                        .set(new NotificationWord(submitData.getMtenderId(),"tender "+submitData.getMtenderId()+" have been added"))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddTenderActivity.this,"notification Added",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddTenderActivity.this,"error setting notification",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTenderActivity.this,"error",Toast.LENGTH_SHORT).show();
                finish();

            }
        });}
        else {Toast.makeText(this,"please fill details",Toast.LENGTH_SHORT).show();}

    }

    public  String getReverseLocation( double lat, double lng){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://api.opencagedata.com/geocode/v1/json?q="+lat+"+"+lng+"&key=46286ea420794543a0c13410cc91a460";

        Log.v("OPEN CAGE",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    TextView tv=findViewById(R.id.tv_map_location);
                                    JSONArray jsonArray=response.getJSONArray("results");
                                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                                    //JSONObject jsonObject1=jsonObject.getJSONObject("components");

                                    stg = jsonObject.getString("formatted");

                                    Log.d("CITY NAME",stg);
                                    tv.setText(stg);


                                    //  listViewMain.setAdapter(arrayAdapter);

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Open Cage Error", Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(jsonObjectRequest);

return stg;
    }
    @Override
    public void onGetLatLng(final LatLng latLng) {
            location= latLng;





        loc=getReverseLocation(latLng.latitude,latLng.longitude);


        Log.d("loc error :: ", "onGetLatLng: "+loc);

        }
    }

