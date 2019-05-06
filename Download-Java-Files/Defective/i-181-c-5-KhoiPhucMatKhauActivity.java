package com.example.appfoody.View;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfoody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class KhoiPhucMatKhauActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtDangKyKP;
    Button btnGuiMail;
    EditText edEmailKP;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quenmatkhau);

        firebaseAuth=FirebaseAuth.getInstance();
        txtDangKyKP=(TextView) findViewById(R.id.txtDangKyKP);
        btnGuiMail=(Button) findViewById(R.id.btnGuiMail);
        edEmailKP=(EditText)  findViewById(R.id.edEmailKP);

        btnGuiMail.setOnClickListener(this);
        txtDangKyKP.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.btnGuiMail:
                String email=edEmailKP.getText().toString();
                boolean kiemTraEmail=kiemTraEmail(email);
                if(kiemTraEmail)
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(KhoiPhucMatKhauActivity.this,getString(R.string.thongbaoguimailthanhcong),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else{
                    Toast.makeText(KhoiPhucMatKhauActivity.this,getString(R.string.thongbaoemailkhonghople),Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txtDangKyKP:
                Intent iDangKy=new Intent(KhoiPhucMatKhauActivity.this,DangKiActivity.class);
                startActivity(iDangKy);
                break;
        }
    }
    public boolean kiemTraEmail(String email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
