package com.example.kuljindersingh.sellit;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private FirebaseUser currentuser;
    private ActionBarDrawerToggle t;
    private FirebaseAuth mAuth;
    private NavigationView nv;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private CircleImageView profile_image;
    private TextView user_name;
    private item  itemobj;
    private String cat[] = {"Laptop","Desktop","Mobile","Pet","House","Other"};
    private int cat_images[] = {R.drawable.laptop , R.drawable.desktop , R.drawable.mobile , R.drawable.pet,R.drawable.home , R.drawable.other };
    private ArrayList<item> list;
    private Button all_cat;
    private GridView grid_front;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        all_cat = (Button)findViewById(R.id.button_all_cat);
        all_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Show_ads.class);
                intent.putExtra("position",9999);
                startActivity(intent);

            }
        });
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
        itemobj = new item();
        list = new ArrayList<>();

        grid_front = (GridView)findViewById(R.id.grid_front);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("item_ads");

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                    {
                        Intent intent= new Intent(getApplicationContext(),Profile.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.post_add:
                    {
                        Intent intent= new Intent(getApplicationContext(),post_add.class);
                        startActivity(intent);
                        break;

                    }
                    case R.id.logout_nav:
                    {
                        mAuth.signOut();
                        LoginManager.getInstance().logOut();
                        intent_login();
                        break;
                    }
                    default:

                }

                dl.closeDrawer(GravityCompat.START);
                return true; }
        });
        grid_front.setAdapter(new front_grid_adapter(this,cat_images ,cat));
        grid_front.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Show_ads.class);
                intent.putExtra("position",i);
                startActivity(intent);
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