package com.example.moebiusplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListOfDiary extends AppCompatActivity {

    private ListView listView;
    private DiaryDbHelper diaryDbHelper;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_diary);

        intializeView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListOfDiary.this, MonthListOfDiary.class);
                intent.putExtra("year", (int) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        list = diaryDbHelper.getListofYear();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        super.onResume();
    }

    public void intializeView() {

        diaryDbHelper = new DiaryDbHelper(this);
        diaryDbHelper.open();
        diaryDbHelper.create();

        listView = (ListView)findViewById(R.id.listView);
        list = diaryDbHelper.getListofYear();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
