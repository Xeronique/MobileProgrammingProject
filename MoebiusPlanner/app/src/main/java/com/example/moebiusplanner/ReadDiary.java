package com.example.moebiusplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ReadDiary extends AppCompatActivity {

    private DiaryDbHelper diaryDbHelper;
    public TextView textDay;
    public TextView textTitle;
    public TextView textContents;

    private int yearNum;
    private int monthNum;
    private int dayNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_diary);

        diaryDbHelper = new DiaryDbHelper(this);
        diaryDbHelper.open();
        diaryDbHelper.create();

        this.initializeView();
    }

    @Override
    protected void onStart() {
        HashMap<String, String> diary = diaryDbHelper.getDiary(yearNum, monthNum, dayNum);
        if (diary != null) {
            textTitle.setText(diary.get(DiaryDB.CreateDB.TITLE));
            textContents.setText(diary.get(DiaryDB.CreateDB.CONTENTS));
        }

        super.onStart();
    }

    public void initializeView() {
        textDay = (TextView) findViewById(R.id.textDay);
        textTitle = (TextView) findViewById(R.id.textTitle);
        textContents = (TextView) findViewById(R.id.textContents);

        Intent intent = getIntent();

        yearNum = intent.getIntExtra("year", 0);
        monthNum = intent.getIntExtra("month", 0);
        dayNum = intent.getIntExtra("day", 0);

        textDay.setText(yearNum + " / " + monthNum + " / " + dayNum);

        HashMap<String, String> diary = diaryDbHelper.getDiary(yearNum, monthNum, dayNum);
        if (diary != null) {
            textTitle.setText(diary.get(DiaryDB.CreateDB.TITLE));
            textContents.setText(diary.get(DiaryDB.CreateDB.CONTENTS));
        }
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.button_read_Edit:
                Intent intent = new Intent(this, WriteDiary.class);
                intent.putExtra("year", yearNum);
                intent.putExtra("month", monthNum);
                intent.putExtra("day", dayNum);
                startActivity(intent);
                break;

            case R.id.button_read_Delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("일기 삭제");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("정말 삭제하시겠습니까?");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diaryDbHelper.deleteDiary(yearNum, monthNum, dayNum);
                        finish();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                break;

            case R.id.button_read_Cancel:
                finish();
                break;
        }
    }
}
