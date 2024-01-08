package com.example.health_reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ListPage extends AppCompatActivity {
    Button btnMedicine;
    Button btnAppointment;
   // ViewPager2 viewPager2;
   // ViewPagerAdapter viewPagerAdapter;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        btnAppointment = findViewById(R.id.btn_appointment);
        btnMedicine = findViewById(R.id.btn_medicine);

        bottomNavigationView = findViewById(R.id.bottomNav);
      //  viewPager2 = findViewById(R.id.viewPager);
       // viewPagerAdapter = new ViewPagerAdapter(this);
      //  viewPager2.setAdapter(viewPagerAdapter);
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


        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Appointments.class);
                startActivity(intent);
                finish();
            }
        });

        btnMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Medicines.class);
                startActivity(intent);
                finish();
            }
        });
    }
}