package com.example.moebiusplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListOfDiary extends AppCompatActivity {

    private ListView listView;
    private DiaryDbHelper diaryDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_diary);

        diaryDbHelper = new DiaryDbHelper(this);
        diaryDbHelper.open();
        diaryDbHelper.create();

        listView = (ListView)findViewById(R.id.listView);

        List<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

    }

    public void intialize() {
        listView = (ListView)findViewById(R.id.listView);

        List<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}
