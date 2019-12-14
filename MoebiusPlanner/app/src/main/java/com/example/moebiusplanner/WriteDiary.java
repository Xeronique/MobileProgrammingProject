package com.example.moebiusplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class WriteDiary extends AppCompatActivity {

    private DiaryDbHelper diaryDbHelper;
    public TextView textDay;
    public EditText editTitle;
    public EditText editContents;

    private int yearNum;
    private int monthNum;
    private int dayNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        diaryDbHelper = new DiaryDbHelper(this);
        diaryDbHelper.open();
        diaryDbHelper.create();

        this.initializeView();
    }

    public void initializeView() {
        textDay = (TextView) findViewById(R.id.textDay);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editContents = (EditText) findViewById(R.id.editContents);

        Intent intent = getIntent();

        yearNum = intent.getIntExtra("year", 0);
        monthNum = intent.getIntExtra("month", 0);
        dayNum = intent.getIntExtra("day", 0);

        textDay.setText(yearNum + " / " + monthNum + " / " + dayNum);

        HashMap<String, String> diary = new HashMap<String, String>();
        diary = diaryDbHelper.getDiary(yearNum, monthNum, dayNum);
        if (diary != null) {
            editTitle.setText(diary.get(DiaryDB.CreateDB.TITLE));
            editContents.setText(diary.get(DiaryDB.CreateDB.CONTENTS));
        }
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.button_write_Save:
                diaryDbHelper.insertDiary(yearNum, monthNum, dayNum, editTitle.getText().toString(), editContents.getText().toString());
                break;

            case R.id.button_write_Cancel:
                finish();
                break;
        }
    }
}
