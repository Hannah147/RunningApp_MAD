package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SummaryPage extends AppCompatActivity {

    TextView tvDate, tvMetresRan, tvCalories, tvTimeRan;
    Integer usersSteps;
    Double usersTime, metresRan, caloriesBurned;
    String usersTimeS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_page);

        tvDate = findViewById(R.id.tvDate);
        tvMetresRan = findViewById(R.id.tvMetresRun);
        tvCalories = findViewById(R.id.tvCalories);
        tvTimeRan = findViewById(R.id.tvTimeRan);

        // Bring data over from main page
        usersSteps = getIntent().getIntExtra("usersSteps", 1);
        usersTimeS = getIntent().getStringExtra("usersTime");
        usersTime = Double.parseDouble(usersTimeS);

        // Get current date and format it
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        metresRan = usersSteps * 0.8;
        caloriesBurned = usersSteps * 0.04;

        tvDate.setText(formattedDate);
        tvMetresRan.setText(String.format("%.2f", metresRan) + " metres ran");
        tvCalories.setText(String.format("%.2f", caloriesBurned) + " calories burned");
        tvTimeRan.setText(String.valueOf(usersTime) + " seconds");

    }

    public void doExit(View view) {
        finish();
    }
}