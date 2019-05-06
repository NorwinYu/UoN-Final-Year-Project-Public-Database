package com.frictionhacks.tenderhaltinfo.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.frictionhacks.tenderhaltinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment {
    private TextView tvName,tvEmail,tvContractorId;

    public ProfileFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        tvName=v.findViewById(R.id.tv_profile_frag_name);
        tvEmail=v.findViewById(R.id.tv_profile_frag_email);
        tvContractorId=v.findViewById(R.id.tv_profile_frag_contractor_id);

        db.collection("Contractor").document(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                  DocumentSnapshot documentSnapshot= task.getResult();
                    assert documentSnapshot != null;
                    String name=Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.get("Name")).toString());
                    tvName.setText(name);
                    String Email=Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.get("Email")).toString());
                    tvEmail.setText(Email);
                    String Contractor=Objects.requireNonNull(Objects.requireNonNull(documentSnapshot.get("Contractor")).toString());
                    tvContractorId.setText(Contractor);
                } else {
                    Log.w(TAG, "Error ProfileFragment documents.", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();

            }
        });



        return v;

    }


}
