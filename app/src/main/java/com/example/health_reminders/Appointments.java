package com.example.health_reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class Appointments extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textView;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef, userRef;
    private ListView listView;
    private static final String PATIENT = "patients";
    String email;
    String uid;
    DataSnapshot item;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        bottomNavigationView = findViewById(R.id.bottomNav);
        listView = findViewById(R.id.listView);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        textView = findViewById(R.id.textView);
        database = FirebaseDatabase.getInstance();
        uid = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("patients");
        userRef = myRef.child(uid);

       // Intent intent = getIntent();
        // email = intent.getStringExtra("email");

      //  Query checkUser = userRef.orderByChild("email").equalTo(email);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home){
                    Intent intent1 = new Intent(getApplicationContext(), ListPage.class);
                    startActivity(intent1);
                    finish();
                }
                else{
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                    finish();
                }
                return false;
            }
        });
       final ArrayList<String> list = new ArrayList<>();
       final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.appointment_list, list);
        listView.setAdapter(adapter);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                item = snapshot.child("appointments");

                if(item.getChildrenCount()>0) {
                    for (DataSnapshot ds : item.getChildren()) {
                      //  list.add(ds.getKey().toString());
                        //        list.add(item.getKey().toString());
                               list.add(ds.child("type").getValue().toString());
                                list.add(ds.child("date").getValue().toString());
                               list.add(ds.child("time").getValue().toString());
                    }
                }
                else{
                    list.add("No appointment Exist");
                }

                    adapter.notifyDataSetChanged();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}