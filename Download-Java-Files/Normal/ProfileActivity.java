package com.frictionhacks.tenderhaltinfo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frictionhacks.tenderhaltinfo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
EditText etName,etEmail,etContractorId;
TextView tv;
FirebaseFirestore db;
FirebaseAuth mAuth;
String personType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button btnSubmit=findViewById(R.id.btn_profile_submit);
        getSupportActionBar().setTitle(getString(R.string.enter_profile_details));
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        etName=findViewById(R.id.et_profile_name);
        etEmail=findViewById(R.id.et_profile_email);
        etContractorId=findViewById(R.id.et_profile_contractor_id);
        personType=getIntent().getStringExtra("person1");
        tv=findViewById(R.id.tv_profile_contractor_id);
        tv.setText(personType + "Id");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (personType.equals("Contractor")){
                Map<String,Object> map=new HashMap<>();
                map.put("Name",etName.getText().toString());
                map.put("Email",etEmail.getText().toString());
                map.put("Contractor",etContractorId.getText().toString());
                db.collection("Contractor").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber())).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                        Toast.makeText(ProfileActivity.this, "Success ADDING", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "ERROR ADDING", Toast.LENGTH_SHORT).show();
                    }
                });}
                else {
                    Map<String,Object> map=new HashMap<>();
                    map.put("Name",etName.getText().toString());
                    map.put("Email",etEmail.getText().toString());
                    map.put("GOvID",etContractorId.getText().toString());
                    db.collection("GovermentOf").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber()))
                            .set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(ProfileActivity.this,GovtMainActivity.class));
                            Toast.makeText(ProfileActivity.this, "Success ADDING", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "ERROR ADDING", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });
    }
}
