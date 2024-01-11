package com.example.health_reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int ALARM_REQUEST_CODE = 100;
    TextInputEditText editTextEmail, editTextPasswd;
    Button btnLogin;
    FirebaseAuth mAuth;
    TextView textView;

    static final String PATIENT = "patients";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPasswd = findViewById(R.id.pswd);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListPage.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPasswd.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ListPage.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
 //
  //      String dbDate = formatt.format(date);
//        LocalDate cDate = LocalDate.parse(formatt.format(thisDate).toString(), formatter);
//        LocalDate dbDate = LocalDate.parse(formatt.format(date).toString(),formatter);
//        long daysBetween = ChronoUnit.DAYS.between(dbDate, cDate);

 //       Toast.makeText(MainActivity.this, cDate, Toast.LENGTH_SHORT).show();
 //       Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();


 //       String[] dateParse= date.split("-");
//        int year = Integer.parseInt(dateParse[0]);
//        int month = Integer.parseInt(dateParse[1]);
//        int day = Integer.parseInt(dateParse[2]);

   //     String localDate = String.valueOf(LocalDate.now());
    //    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


 //       long triggerTime = System.currentTimeMillis()+(10*3);

//        Intent iBroadcast = new Intent(MainActivity.this, broadcastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, ALARM_REQUEST_CODE, iBroadcast, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
//        if((date.compareTo(localDate)<=1) ){
//
//        }


    }

    }
