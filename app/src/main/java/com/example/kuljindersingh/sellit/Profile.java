package com.example.kuljindersingh.sellit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signout;
    private FirebaseUser current_user;
    private TextView user , ads;
    private ImageView user_image;
    private long num_ads ;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        ads = (TextView)findViewById(R.id.num_ads);
        user = (TextView)findViewById(R.id.user_name_profile);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("item_ads");
        user_image = (ImageView)findViewById(R.id.profile_pic);
        String id = current_user.getUid();
        Log.w("user id",id);
        if(current_user.getPhotoUrl() != null )
        {
            Picasso.get().load(current_user.getPhotoUrl().toString()).into(user_image);
        }
        else
        {
            Picasso.get().load(R.drawable.com_facebook_profile_picture_blank_portrait).into(user_image);

        }
        databaseReference.orderByChild("uid").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                num_ads = dataSnapshot.getChildrenCount();
                Log.w("user id............", String.valueOf(num_ads));
                ads.setText( String.valueOf(num_ads));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        user.setText(current_user.getEmail());


    }

}
