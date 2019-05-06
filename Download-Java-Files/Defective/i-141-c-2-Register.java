package com.example.mariyam.werp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText email,password,confirmpassword,mobile,username;
    private Button Register;
    private ProgressDialog mprogress;
    private DatabaseReference mdatabasereference;
    private FirebaseDatabase mdatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=(EditText)findViewById(R.id.edtUsername);
        email=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.edtpassword);
        confirmpassword=(EditText)findViewById(R.id.edtConPass);
        mobile=(EditText)findViewById(R.id.editText2);
        Register=(Button)findViewById(R.id.btnRegister);
        mprogress=new ProgressDialog(this);

        mdatabase=FirebaseDatabase.getInstance();
        mdatabasereference=mdatabase.getReference().child("Users");
        mAuth=FirebaseAuth.getInstance();


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();         }
        });
    }

    private void createNewAccount() {
        String uname=username.getText().toString().trim();
        String uemail=email.getText().toString().trim();
        String upassword=password.getText().toString().trim();
        String ucnfpassword=confirmpassword.getText().toString().trim();
        String umobile=mobile.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(uemail).matches()){
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }

        if(upassword.length()<6 ){
            password.setError("Minimum length required is 6");
            password.requestFocus();
            return;
        }
        if(umobile.length()<12){
            mobile.setError("Enter the correct phone number with code");
            mobile.requestFocus();
            return;
        }
        if (!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(uemail) && !TextUtils.isEmpty(ucnfpassword) &&
                !TextUtils.isEmpty(upassword) && !TextUtils.isEmpty(umobile)){

            if(upassword.equals(ucnfpassword)){
                mprogress.setMessage("Creating Account.....");
                mprogress.show();

                mAuth.createUserWithEmailAndPassword(uemail,upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            sendemailverfication();
                        }else{

                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                mprogress.dismiss();
                                Toast.makeText(Register.this,"Email is already registered.Use another mail",Toast.LENGTH_SHORT).show();
                            }else{
                                mprogress.dismiss();
                                Toast.makeText(Register.this,"Registration failed",Toast.LENGTH_SHORT).show();

                            }
                           // Toast.makeText(Register.this,"Registration failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }else{
                Toast.makeText(Register.this,"Oopss..Password didnt match",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(Register.this,"Enter all the details",Toast.LENGTH_SHORT).show();
        }

    }

    private void sendemailverfication() {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Verification email has been sent.", Toast.LENGTH_SHORT).show();
                        String userid = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentuserid=mdatabasereference.child(userid);
                        currentuserid.child("Username").setValue(username.getText().toString().trim());
                        currentuserid.child("Email").setValue(email.getText().toString().trim());
                       /* currentuserid.child("Password").setValue(password.getText().toString().trim());
                        currentuserid.child("Confirmpassword").setValue(confirmpassword.getText().toString().trim());*/
                        currentuserid.child("MobileNo").setValue(mobile.getText().toString());

                        mprogress.dismiss();
                        mAuth.signOut();

                        startActivity(new Intent(Register.this,Signin.class));
                        finish();

                    }else {
                        Toast.makeText(Register.this,"Verificaton link not sent.",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}
