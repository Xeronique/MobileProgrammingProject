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

public class MonthListOfDiary extends AppCompatActivity {

    private ListView listView;
    private TextView viewDay;
    private List<String> list;
    private DiaryDbHelper diaryDbHelper;
    private int yearNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_list_of_diary);

        intializeView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MonthListOfDiary.this, DayListOfDiary.class);
                intent.putExtra("year", yearNum);
                intent.putExtra("month", (int) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        list = diaryDbHelper.getListofMonth(yearNum);
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

        viewDay = (TextView) findViewById(R.id.textView_day);
        viewDay.setText("" + yearNum);

        listView = (ListView)findViewById(R.id.listView);
        list = diaryDbHelper.getListofMonth(yearNum);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
