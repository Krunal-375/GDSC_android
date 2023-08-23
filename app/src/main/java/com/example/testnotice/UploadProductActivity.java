package com.example.testnotice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class UploadProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    TextView headline, shortDescription, description;
    ImageView uploadbtn, featuredImage;
    Button submit;
    Uri ImageUri;
    RelativeLayout relativeLayout;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    String postedBy,userID;;

    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.setTitle("Notice Uploading");
        dialog.setCanceledOnTouchOutside(false);

        headline = findViewById(R.id.headline);
        shortDescription = findViewById(R.id.description);
        description = findViewById(R.id.aboutProduct);
        relativeLayout = findViewById(R.id.relative);

        uploadbtn = findViewById(R.id.uploadbtn);
        featuredImage = findViewById(R.id.productImage);

        submit = findViewById(R.id.submit);

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
                relativeLayout.setVisibility(View.VISIBLE);
                uploadbtn.setVisibility(View.GONE);
            }
        });
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()){
//                    fullname.setText(documentSnapshot.getString("fName"));
//                    adminToolbar.setTitle("Welcome "+documentSnapshot.getString("fName"));
                    postedBy = documentSnapshot.getString("fName");
                }else {
                    Log.d("tag","onEvent: Document do not exists ");
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                final StorageReference reference = firebaseStorage.getReference().child("notice")
                        .child(System.currentTimeMillis()+"");
                reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                 ProjectModel model = new ProjectModel();
                                 model.setFeaturedImage(uri.toString());

                                 model.setHeadline(headline.getText().toString());
                                 model.setShortDescription(shortDescription.getText().toString());
                                 model.setDescription(description.getText().toString());
                                 model.setPostedBy(postedBy);

                                 database.getReference().child("notice").push().setValue(model)
                                         .addOnSuccessListener(new OnSuccessListener<Void>() {
                                             @Override
                                             public void onSuccess(Void unused) {

                                                 Toast.makeText(UploadProductActivity.this, "Notice Upload Successfully", Toast.LENGTH_SHORT).show();
                                                 dialog.dismiss();
                                             }
                                         }).addOnFailureListener(new OnFailureListener() {
                                             @Override
                                             public void onFailure(@NonNull Exception e) {
                                                 Toast.makeText(UploadProductActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                             }
                                         });
                                if (!headline.getText().toString().isEmpty() && !shortDescription.getText().toString().isEmpty()) {
                                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                            "/topics/all",
                                            headline.getText().toString(),
                                            shortDescription.getText().toString(),
                                            getApplicationContext(),
                                            UploadProductActivity.this
                                    );
                                    notificationsSender.SendNotifications();
                                } else {
                                    Toast.makeText(UploadProductActivity.this, "Please enter your data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }
        });

    }

    private void UploadImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                ImageUri = data.getData();
                featuredImage.setImageURI(ImageUri);
            }
        }
    }


}