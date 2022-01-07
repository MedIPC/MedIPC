package com.example.myapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Alarms.AlarmActivity;
import com.example.myapplication.Alarms.AlarmProfile;
import com.example.myapplication.Medication.MedicationActivity;
import com.example.myapplication.Medication.MedicationProfile;
import com.example.myapplication.Profile.Profile;
import com.example.myapplication.Profile.ProfileActivity;


public class MainActivity extends AppCompatActivity {
    private Profile profile;
    private AlarmProfile alarmprofile;
    private MedicationProfile medicationProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = new Profile();
        alarmprofile = new AlarmProfile();
        medicationProfile = new MedicationProfile();

        if(getIntent().getSerializableExtra(getString(R.string.profile)) != null)
            profile = (Profile)getIntent().getSerializableExtra(getString(R.string.profile));

        if(getIntent().getSerializableExtra(getString(R.string.alarm)) != null)
            alarmprofile = (AlarmProfile)getIntent().getSerializableExtra(getString(R.string.alarm));

        if(getIntent().getSerializableExtra(getString(R.string.medication_key)) != null)
            medicationProfile = (MedicationProfile) getIntent().getSerializableExtra(getString(R.string.medication_key));

        Button button = findViewById(R.id.btProfile);
        Button button2 = findViewById(R.id.btAlarm);
        Button button3 = findViewById(R.id.btMedication);

        button.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
            myIntent.putExtra("profile", profile);
            MainActivity.this.startActivity(myIntent);
        });
        button2.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, AlarmActivity.class);
            myIntent.putExtra("alarm", alarmprofile);
            MainActivity.this.startActivity(myIntent);
        });
        button3.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, MedicationActivity.class);
            myIntent.putExtra(getString(R.string.medication_key), medicationProfile);
            MainActivity.this.startActivity(myIntent);
        });
    }
}

