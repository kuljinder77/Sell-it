package com.example.kuljindersingh.sellit;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class post_add extends AppCompatActivity {

    private static final int REQUEST_CODE = 1 ;
    private ImageView item_image;
    private EditText item_name , item_description , contact;
    private Spinner spinner;
    private Uri uri , firebase_uri;
    private Button post,cancel;
    private String cat[] = {"Laptop","Desktop","Mobile","Pet","house","Other"};
    private StorageReference storage;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private item itemobj;
    private String storage_image_name;
    private SimpleDateFormat date = new SimpleDateFormat("yyyyMMDD_HHmmSS");
    private String timestamp = date.format(new Date());
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser currentuser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);
        requestStoragePermission();
        storage = FirebaseStorage.getInstance().getReference();
        spinner =(Spinner)findViewById(R.id.cat_spinner);
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cat);
        spinner.setAdapter(myadapter);
        item_image = (ImageView)findViewById(R.id.capture_view);
        cancel = (Button)findViewById(R.id.cancel_post_ad);
        item_name = (EditText)findViewById(R.id.item_name);
        item_description = (EditText)findViewById(R.id.description);
        contact = (EditText)findViewById(R.id.contact);
        post = (Button) findViewById(R.id.button_post);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("item_ads");
        itemobj = new item();
        auth = FirebaseAuth.getInstance();
        currentuser = auth.getCurrentUser();


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

       post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( item_description.getText().toString().isEmpty()  || item_name.getText().toString().isEmpty() || contact.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(),"Please provide all data !",Toast.LENGTH_LONG).show();
                }
                else
                {

                    insert();

                }
            }
        });
        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                storage_image_name = getImageName();
                File imageFile = new File(dir,storage_image_name);
                uri = Uri.fromFile(imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });


        }

    private void insert() {

        if(uri == null)
        {
            uri = Uri.parse("file:///storage/emulated/0/Pictures/item_image20180491_232921.jpg");
        }
        StorageReference filepath = storage.child("Photos").child(uri.getLastPathSegment());
        Log.w("wwwwwkkkkkkwkwkkkkkkk",uri.toString());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                firebase_uri = taskSnapshot.getDownloadUrl();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        item_getValues();
                        databaseReference.child(item_name.getText().toString()+"_"+timestamp).setValue(itemobj);

                        Toast.makeText(getApplicationContext(),"Ad posted..",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        });

    }

    private void item_getValues() {
        itemobj.setName(item_name.getText().toString());
        itemobj.setType(spinner.getSelectedItem().toString());
        itemobj.setContact(contact.getText().toString());
        itemobj.setDescription(item_description.getText().toString());
        itemobj.setUrl(firebase_uri.toString());
        itemobj.setUid(currentuser.getUid());
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(post_add.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK )
        {


            Bitmap pic = null;
            try {
                pic = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                Log.w("UUURRROTTTTTTRRRYYYYYYY",uri.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            item_image.setImageBitmap(pic);

        }
    }


    public String getImageName() {

        return "item_image"+timestamp + ".jpg";
    }

}
