package com.example.kuljindersingh.sellit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signout;
    private FirebaseUser current_user;
    private TextView user_name;
    private ImageView user_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        user_name = (TextView)findViewById(R.id.user_name);
        user_image = (ImageView)findViewById(R.id.profile_pic);
        signout = (Button)findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();



            }
        });
        user_name.setText(current_user.getDisplayName());
        Picasso.get().load(current_user.getPhotoUrl().toString()).into(user_image);

    }
}
