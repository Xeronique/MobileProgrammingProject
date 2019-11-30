package com.example.moebiusplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


public class MainActivity extends AppCompatActivity {

    public MaterialCalendarView mCalendar;
    public TextView mTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalendar = (MaterialCalendarView) findViewById(R.id.calendarView);
        mTextDate = (TextView) findViewById(R.id.textDate);

        //mCalendar.addDecorators(new SaturdayDecorator(), new SundayDecorator());
    }
}
