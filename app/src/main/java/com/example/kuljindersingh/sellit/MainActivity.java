package com.example.kuljindersingh.sellit;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;

import com.facebook.login.LoginManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private FirebaseUser currentuser;
    private ActionBarDrawerToggle t;
    private FirebaseAuth mAuth;
    private NavigationView nv;
    private CircleImageView profile_image;
    private TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        View headerLayout = nv.inflateHeaderView(R.layout.nav_header);
        profile_image = (CircleImageView) headerLayout.findViewById(R.id.profile_image);
        user_name = (TextView)headerLayout.findViewById(R.id.display_name);
        UpdateNav(currentuser);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                    {
                        Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();

                    }
                    case R.id.settings:
                    {
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();

                    }
                    case R.id.logout_nav:
                    {
                        mAuth.signOut();
                        LoginManager.getInstance().logOut();
                        intent_login();


                    }
                    default:
                        return true;
                }




            }
        });


    }

    private void intent_login() {
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
        finish();
    }

    private void UpdateNav(FirebaseUser currentuser) {   // check for user profile and name to update it in navigation bar

        if (currentuser.getPhotoUrl() != null) {

            Picasso.get().load(currentuser.getPhotoUrl().toString()).into(profile_image);
            Log.w("URL",currentuser.getPhotoUrl().toString());
        } else {
            profile_image.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        }
        if(currentuser.getDisplayName() != null)
        {
            user_name.setText(currentuser.getDisplayName().toString());
        }
        else {
            user_name.setText(currentuser.getEmail());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}