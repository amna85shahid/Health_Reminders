package com.example.health_reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class Medicines extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView listView;
    private BottomNavigationView bottomNavigationView;
    private TextView mName, nDoses,nDays;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef, userRef;
    private static final String PATIENT = "patients";
    String email;
    String uid, medName, doses, days, speechText;
    DataSnapshot ds;
    TextToSpeech textToSpeech;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);

        bottomNavigationView = findViewById(R.id.bottomNav);
       // listView = findViewById(R.id.listView);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mName = findViewById(R.id.medName);
        nDays= findViewById(R.id.noDays);
        nDoses= findViewById(R.id.noDoses);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("patients");
        uid = fAuth.getCurrentUser().getUid();
        userRef = myRef.child(uid);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home){
                    Intent intent1 = new Intent(getApplicationContext(), ListPage.class);
                    startActivity(intent1);
                    finish();
                    // viewPager2.setCurrentItem(0);
                }
                else{
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                    finish();
                    //viewPager2.setCurrentItem(1);
                }
                return false;
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ds = snapshot.child("medicines");
                if(ds.getChildrenCount()>0) {
                    DataSnapshot item = ds.child("medicine");
                   medName =  item.child("name").getValue().toString();
                    mName.setText(medName);
                   days = item.child("days").getValue().toString();
                   nDays.setText(days);
                    doses = item.child("doses").getValue().toString();
                    nDoses.setText(doses);
                    speechText = "You have to take " + medName + doses + "times a day for" + days + "days";
                    textToSpeech.speak(speechText, TextToSpeech.QUEUE_FLUSH, null, null);

                }
                else{
                    Toast.makeText(Medicines.this, "No medicines prescribed", Toast.LENGTH_SHORT).show();

                }

      //          adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}