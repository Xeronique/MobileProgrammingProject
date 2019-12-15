package com.example.moebiusplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.LocalDate;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public MaterialCalendarView mCalendar;
    public TextView mTextDate;
    public TextView mAddDiary;
    public TextView mViewTitle;
    public TextView mViewContents;
    public View mLayoutViewDiary;

    private final SelectDecorator selectDecorator = new SelectDecorator();
    private DiaryDbHelper diaryDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.intializeView();

        mCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectDecorator.setDate(date);
                mCalendar.invalidateDecorators();
                mTextDate.setText(date.getYear() + " / " + date.getMonth() + " / " + date.getDay());

                HashMap<String, String> diary = diaryDbHelper.getDiary(date.getYear(), date.getMonth(), date.getDay());
                if (diary == null) {
                    mAddDiary.setVisibility(View.VISIBLE);
                    mLayoutViewDiary.setVisibility(View.GONE);
                } else {
                    mAddDiary.setVisibility(View.GONE);
                    mLayoutViewDiary.setVisibility(View.VISIBLE);
                    mViewTitle.setText(diary.get(DiaryDB.CreateDB.TITLE));
                    mViewContents.setText(diary.get(DiaryDB.CreateDB.CONTENTS));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        HashMap<String, String> diary = diaryDbHelper.getDiary(mCalendar.getSelectedDate().getYear(), mCalendar.getSelectedDate().getMonth(), mCalendar.getSelectedDate().getDay());
        if (diary == null) {
            mAddDiary.setVisibility(View.VISIBLE);
            mLayoutViewDiary.setVisibility(View.GONE);
        } else {
            mAddDiary.setVisibility(View.GONE);
            mLayoutViewDiary.setVisibility(View.VISIBLE);
            mViewTitle.setText(diary.get(DiaryDB.CreateDB.TITLE));
            mViewContents.setText(diary.get(DiaryDB.CreateDB.CONTENTS));
        }
        mCalendar.invalidateDecorators();
        super.onResume();
    }

    public void intializeView() {

        diaryDbHelper = new DiaryDbHelper(this);
        diaryDbHelper.open();
        diaryDbHelper.create();

        mCalendar = (MaterialCalendarView) findViewById(R.id.calendarView);
        mTextDate = (TextView) findViewById(R.id.textDate);
        mAddDiary = (TextView) findViewById(R.id.textView_addDiary);
        mViewTitle = (TextView) findViewById(R.id.textView_Title);
        mViewContents = (TextView) findViewById(R.id.textView_Contents);
        mLayoutViewDiary = (View) findViewById(R.id.layout_viewDiary);
        registerForContextMenu(mLayoutViewDiary);

        mCalendar.setSelectedDate(CalendarDay.today());
        mCalendar.addDecorators(new GeneralDecorator(), new SaturdayDecorator(), new SundayDecorator(), selectDecorator, new DiaryDecorator(this));
        mTextDate.setText(mCalendar.getSelectedDate().getYear() + " / " + mCalendar.getSelectedDate().getMonth() + " / " + mCalendar.getSelectedDate().getDay());

        HashMap<String, String> diary = diaryDbHelper.getDiary(mCalendar.getSelectedDate().getYear(), mCalendar.getSelectedDate().getMonth(), mCalendar.getSelectedDate().getDay());
        if (diary == null) {
            mAddDiary.setVisibility(View.VISIBLE);
            mLayoutViewDiary.setVisibility(View.GONE);
        } else {
            mAddDiary.setVisibility(View.GONE);
            mLayoutViewDiary.setVisibility(View.VISIBLE);
            mViewTitle.setText(diary.get(DiaryDB.CreateDB.TITLE));
            mViewContents.setText(diary.get(DiaryDB.CreateDB.CONTENTS));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "목록으로 보기");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(this, ListOfDiary.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.layout_viewDiary:
                getMenuInflater().inflate(R.menu.contextmenu, menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent editIntent = new Intent(this, WriteDiary.class);
                editIntent.putExtra("year", mCalendar.getSelectedDate().getYear());
                editIntent.putExtra("month", mCalendar.getSelectedDate().getMonth());
                editIntent.putExtra("day", mCalendar.getSelectedDate().getDay());
                startActivity(editIntent);
                break;

            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("일기 삭제");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("정말 삭제하시겠습니까?");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diaryDbHelper.deleteDiary(mCalendar.getSelectedDate().getYear(), mCalendar.getSelectedDate().getMonth(), mCalendar.getSelectedDate().getDay());
                        onResume();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.textView_addDiary:
                Intent addIntent = new Intent(this, WriteDiary.class);
                addIntent.putExtra("year", mCalendar.getSelectedDate().getYear());
                addIntent.putExtra("month", mCalendar.getSelectedDate().getMonth());
                addIntent.putExtra("day", mCalendar.getSelectedDate().getDay());
                startActivity(addIntent);
                break;
            case R.id.layout_viewDiary:
                Intent viewIntent = new Intent(this, ReadDiary.class);
                viewIntent.putExtra("year", mCalendar.getSelectedDate().getYear());
                viewIntent.putExtra("month", mCalendar.getSelectedDate().getMonth());
                viewIntent.putExtra("day", mCalendar.getSelectedDate().getDay());
                startActivity(viewIntent);
                break;
        }
    }
}