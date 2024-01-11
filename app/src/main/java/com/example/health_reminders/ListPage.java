package com.example.health_reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ListPage extends AppCompatActivity {

    Button btnMedicine;
    Button btnAppointment;
    BottomNavigationView bottomNavigationView;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference myRef, userRef;
    TextView mName, nDoses, nDays;
    private static final String PATIENT = "patients";
    String uid, type, date, time, medName, days, doses, medInfo, appointmentInfo;
    DataSnapshot item, ds;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        btnAppointment = findViewById(R.id.btn_appointment);
        btnMedicine = findViewById(R.id.btn_medicine);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        uid = fAuth.getCurrentUser().getUid();
        myRef = database.getReference(PATIENT);
        userRef = myRef.child(uid);

        bottomNavigationView = findViewById(R.id.bottomNav);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                item = snapshot.child("appointments");
                ds = snapshot.child("medicines");

                if (item.getChildrenCount() > 0) {
                    for (DataSnapshot ds : item.getChildren()) {
                        type = ds.child("type").getValue().toString();
                        date = ds.child("date").getValue().toString();
                        time = ds.child("time").getValue().toString();
                        appointmentInfo = "You have " + type + "appointment at" + time + "on" + date;
                    }
                }
                if (ds.getChildrenCount() > 0) {
                    DataSnapshot item = ds.child("medicine");
                    medName =  item.child("name").getValue().toString();
                    days = item.child("days").getValue().toString();
                    doses = item.child("doses").getValue().toString();

                    medInfo = "You are prescribed to take " + medName + doses + "times a day for" + days + "days";
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    Intent intent1 = new Intent(getApplicationContext(), ListPage.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                    finish();
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
        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Appointments.class);
                startActivity(intent);
                if(item.getChildrenCount() > 0){
                    textToSpeech.speak(appointmentInfo, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else{
                    textToSpeech.speak("You don't have any appointment", TextToSpeech.QUEUE_FLUSH, null, null);
                }

                finish();
            }
        });

        btnMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Medicines.class);
                startActivity(intent);
                if (ds.getChildrenCount() > 0) {
                    textToSpeech.speak(medInfo, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else{
                    textToSpeech.speak("You are not prescribed to take any medicine", TextToSpeech.QUEUE_FLUSH, null, null);
                }
                finish();
            }
        });

//

        //      Date thisDate = new Date();
        //      SimpleDateFormat formatt = new SimpleDateFormat("yyyy-mm-dd");
        //     String cDate = formatt.format(thisDate);
        //      Toast.makeText(getApplicationContext(), cDate, Toast.LENGTH_SHORT).show();


    }
}
