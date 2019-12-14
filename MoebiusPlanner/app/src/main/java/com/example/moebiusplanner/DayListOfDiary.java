package com.example.moebiusplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DayListOfDiary extends AppCompatActivity {

    private ListView listView;
    private TextView viewDay;
    private List<String> list;
    private DiaryDbHelper diaryDbHelper;
    private int yearNum;
    private int monthNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_list_of_diary);

        intializeView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DayListOfDiary.this, ReadDiary.class);
                intent.putExtra("year", yearNum);
                intent.putExtra("month", monthNum);
                intent.putExtra("day", (int) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        list = diaryDbHelper.getListofDay(yearNum, monthNum);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        super.onResume();
    }

    public void intializeView() {

        diaryDbHelper = new DiaryDbHelper(this);
        diaryDbHelper.open();
        diaryDbHelper.create();

        Intent intent = getIntent();
        yearNum = intent.getIntExtra("year", 0);
        monthNum = intent.getIntExtra("month", 0);

        viewDay = (TextView) findViewById(R.id.textView_day);
        viewDay.setText(yearNum + " / " + monthNum);

        listView = (ListView)findViewById(R.id.listView);
        list = diaryDbHelper.getListofDay(yearNum, monthNum);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
