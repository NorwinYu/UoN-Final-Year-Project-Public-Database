package com.example.appfoody.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appfoody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKiActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnDangKy;
    EditText edEmailDangKy,edMatKhau,edNhapLaiMatKhau;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        firebaseAuth =FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);

        btnDangKy=(Button) findViewById(R.id.btnDangKy);
        edEmailDangKy=(EditText)  findViewById(R.id.edEmailDangKy);
        edMatKhau=(EditText)  findViewById(R.id.edMatkhau);
        edNhapLaiMatKhau=(EditText)  findViewById(R.id.edNhapLaiMatKhau);

        btnDangKy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage(getString(R.string.dangxuly));
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        String email =edEmailDangKy.getText().toString();
        String matkhau=edMatKhau.getText().toString();
        String nhaplaimatkhau=edNhapLaiMatKhau.getText().toString();
        String thongbaoloi=getString(R.string.thongbaoloidangky);
        if(email.trim().length()==0)
        {
            thongbaoloi += getString(R.string.email);
            Toast.makeText(this,thongbaoloi,Toast.LENGTH_SHORT).show();
        }else if(matkhau.trim().length()==0)
        {
            thongbaoloi +=getString(R.string.matkhau);
            Toast.makeText(this,thongbaoloi,Toast.LENGTH_SHORT).show();
        }else if(!nhaplaimatkhau.equals(matkhau))
        {
            Toast.makeText(this,getString(R.string.thongbaonhaplaimatkhau),Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else
        {
            firebaseAuth.createUserWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                        progressDialog.dismiss();
                        Toast.makeText(DangKiActivity.this,getString(R.string.dangkythanhcong),Toast.LENGTH_SHORT).show();
                }
            });
    }
}

}
