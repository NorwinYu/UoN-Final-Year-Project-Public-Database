package com.example.mariyam.werp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class FirstActivity extends AppCompatActivity {
    private Button signout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        signout=(Button)findViewById(R.id.Signout);
        mAuth=FirebaseAuth.getInstance();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert=new AlertDialog.Builder(FirstActivity.this);
                alert.setTitle("Are you sure you want to signout?");
                alert.setNegativeButton("NO",null);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        startActivity(new Intent(FirstActivity.this,Signin.class));
                        finish();

                    }
                });alert.show();



            }

        });
    }
}
