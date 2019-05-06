package com.example.mariyam.werp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Signin extends AppCompatActivity {
    private EditText loginemail,loginpassword,inputnewpassword;
    private Button resetpassword,newpassword;
    private Button login,google;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    //GoogleSignIn mGooglesignInClient;//added
    private int RC_SIGN_IN=1;//added
    GoogleSignInClient mGoogleSignInClient; //added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Button btnToRegister = (Button) findViewById(R.id.btnToRegister);
        loginemail=(EditText)findViewById(R.id.edtUsername);
        loginpassword=(EditText)findViewById(R.id.edtpassword);
        resetpassword=(Button)findViewById(R.id.btnForgetPass);
        login=(Button)findViewById(R.id.btnLogin);
        mProgressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        //added

        google = findViewById(R.id.btnSignupGoogle);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        //till here

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });

        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin.this,Register.class);
                startActivity(intent);
            }
        });
      /*  mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser=firebaseAuth.getCurrentUser();

                if(mUser!=null){
                    String email=loginemail.getText().toString();
                    String pwd=loginpassword.getText().toString();

                    login(email,pwd);

                    /*Toast.makeText(Signin.this,"Signed in",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Signin.this,FirstActivity.class));
                    finish();*/

               /* }else {
                    Toast.makeText(Signin.this,"Not Signed In",Toast.LENGTH_LONG).show();
                }

            }
        };*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(loginemail.getText().toString()) && !TextUtils.isEmpty(loginpassword.getText().toString())){

                    mProgressDialog.setMessage("Logging in");
                    mProgressDialog.show();

                    String email=loginemail.getText().toString();
                    String pwd=loginpassword.getText().toString();

                    login(email,pwd);

                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(Signin.this,"Enter all the entries",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    mProgressDialog.dismiss();
                    emailVerificationdone();
                    // Toast.makeText(LoginActivity.this,"Signed in",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    //finish();
                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(Signin.this,"Login Failed",Toast.LENGTH_SHORT).show();


                }
            }
        });

    }

    private void emailVerificationdone() {
        FirebaseUser firebaseUser=mAuth.getInstance().getCurrentUser();
        Boolean emailverified=firebaseUser.isEmailVerified();
        if(emailverified){
            finish();
            startActivity(new Intent(this,FirstActivity.class));

        }else{
             mProgressDialog.dismiss();

            Toast.makeText(this,"Verify your mail",Toast.LENGTH_SHORT).show();
           // mProgressDialog.dismiss();
            mAuth.signOut();

        }

    }

    private void resetpassword() {
        AlertDialog.Builder builder=new AlertDialog.Builder(Signin.this);
        inputnewpassword=new EditText(this);
        inputnewpassword.setHint("Enter your reistered MailID");
        builder.setView(inputnewpassword);
       /* newpassword=new Button(this);
        newpassword.setText("Reset Password");*/
        builder.setNeutralButton("ResetPassword", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=inputnewpassword.getText().toString().trim();
                if(email.equals("")){
                    Toast.makeText(Signin.this,"Enter enter your registered email ID",Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Signin.this,"Password reset email sent",Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                            }else {
                                Toast.makeText(Signin.this,"Error in sending password reset email",Toast.LENGTH_SHORT).show();
                                //this errror will occur when the email is not registered in database
                                mProgressDialog.dismiss();
                            }

                        }
                    });
                }
            }
        });builder.show();

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
       // mAuth.addAuthStateListener(mAuthListener);
    }
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent i = new Intent(Signin.this,FirstActivity.class);
            startActivity(i);
            this.finish();
        }
    }

    //added
    private void signIn() {
        Log.d("hello","entered");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d("hello","iamhere");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("hello",task.isSuccessful()+"");
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("hello","heretoo");
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("hello", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("hello", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("hello", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUImod(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("hello", "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUImod(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUImod(FirebaseUser user) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
        }
        Toast.makeText(this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
    }

    //till here


}
