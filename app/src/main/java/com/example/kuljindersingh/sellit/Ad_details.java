package com.example.kuljindersingh.sellit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Ad_details extends AppCompatActivity {
    private String item_name , item_description , contact , item_url , item_uid ;
    private ImageView img;
    private TextView name,description,user_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);
        description = (TextView)findViewById(R.id.details_description);
        user_contact = (TextView)findViewById(R.id.details_contact);
        Intent intent = getIntent();
        item_description = intent.getStringExtra("item_description");

        item_name = intent.getStringExtra("item_name");
        name = (TextView)findViewById(R.id.details_name);
        item_url = intent.getStringExtra("item_url");
        img = (ImageView)findViewById(R.id.details_image);
        item_uid = intent.getStringExtra("item_uid");

        contact = intent.getStringExtra("item_contact");
        name.setText(item_name);
        description.setText(item_description);
        user_contact.setText(contact);
        Picasso.get().load(item_url).into(img);

    }
}
