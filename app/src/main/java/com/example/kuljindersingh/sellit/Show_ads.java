package com.example.kuljindersingh.sellit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Show_ads extends AppCompatActivity {
    private GridView grid ;
    private item itemobj;
    private int position;
    private ArrayList<item> item_list;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ads);
        position = getIntent().getIntExtra("position",0);
        grid = (GridView)findViewById(R.id.ads_grid);
        itemobj = new item();

        item_list = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("item_ads");
        switch (position)
        {
            case 0:
            {
                databaseReference.orderByChild("type").equalTo("Laptop").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snaphot : dataSnapshot.getChildren()) {
                            itemobj = snaphot.getValue(item.class);
                            item_list.add(itemobj);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            }
            case 1:
            {
                databaseReference.orderByChild("type").equalTo("Desktop").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snaphot : dataSnapshot.getChildren()) {
                            itemobj = snaphot.getValue(item.class);
                            item_list.add(itemobj);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            }
            case 2:
            {
                databaseReference.orderByChild("type").equalTo("Mobile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snaphot : dataSnapshot.getChildren()) {
                            itemobj = snaphot.getValue(item.class);
                            item_list.add(itemobj);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            }
            case 3:
            {
                databaseReference.orderByChild("type").equalTo("Pet").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snaphot : dataSnapshot.getChildren()) {
                            itemobj = snaphot.getValue(item.class);
                            item_list.add(itemobj);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            }
            case 4:
            {
                databaseReference.orderByChild("type").equalTo("house").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snaphot : dataSnapshot.getChildren()) {
                            itemobj = snaphot.getValue(item.class);
                            item_list.add(itemobj);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            }
            case 5:
            {
                databaseReference.orderByChild("type").equalTo("Other").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snaphot : dataSnapshot.getChildren()) {
                            itemobj = snaphot.getValue(item.class);
                            item_list.add(itemobj);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            }
            case 9999:
            {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snaphot : dataSnapshot.getChildren()) {
                            itemobj = snaphot.getValue(item.class);
                            item_list.add(itemobj);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            }
        }

        grid.setAdapter(new ads_grid_adapter(this,item_list));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Ad_details.class);
                intent.putExtra("item_name",item_list.get(i).getName());
                intent.putExtra("item_url",item_list.get(i).getUrl());
                intent.putExtra("item_uid",item_list.get(i).getUid());
                intent.putExtra("item_description",item_list.get(i).getDescription());
                intent.putExtra("item_contact",item_list.get(i).getContact());
                startActivity(intent);
            }
        });
    }
}
