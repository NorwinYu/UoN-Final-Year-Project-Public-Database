package ethered.wallpapershots;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Firebase extends AppCompatActivity implements View.OnClickListener /*  implementing click listener */ {
    //a constant to track the file chooser intent
    private static final String TAG = "Firebase1";
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final String ImageURL = "image_url";
    private static final String UserID = "user_id";
    FirebaseFirestore db;

    //Buttons
    private Button buttonChoose;
    private Button buttonUpload;


    //ImageView
    private ImageView imageView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();


    //a Uri object to store file path
    private Uri filePath;
    private String userid;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        db = FirebaseFirestore.getInstance();

        //getting views from layout
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        imageView = (ImageView) findViewById(R.id.imageView);

        //attaching listener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }



    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

             EditText e = (EditText)findViewById(R.id.imgname);
            StorageReference riversRef = mStorageRef.child("images/"+e.getText().toString());
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });





            final Handler  handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();
                    EditText e = (EditText)findViewById(R.id.imgname);
                    StorageReference ref=mStorageRef.child("images/"+ e.getText().toString());
                    ref.getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    userid = user.getUid();
                                    Map<String,Object> datatosave = new HashMap<String, Object>();
                                    datatosave.put(ImageURL,uri.toString());
                                    datatosave.put(UserID,userid);
                                    db.collection("imagedata").add(datatosave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("Error","Document uploaded");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("Error","Document failed to upload");
                                        }
                                    });
                                    progressDialog.dismiss();
                                    Log.d(TAG,"Got the url and it is " +uri.toString());
                                    Toast.makeText(Firebase.this, uri.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"did not get the URL");
                            Toast.makeText(Firebase.this,"failed please try again in sometime!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            },7000);


        }
        //if there is not any file
        else {
            //you can display an error toast
        }




    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        //if the clicked button is choose
        if (view == buttonChoose) {
            showFileChooser();
        }
        //if the clicked button is upload
        else if (view == buttonUpload) {
            uploadFile();
            Toast.makeText(getApplicationContext(),"Your Photo is being uploaded",Toast.LENGTH_SHORT).show();

        }
    }
}