package com.example.moebiusplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.LocalDate;


public class MainActivity extends AppCompatActivity {

    public MaterialCalendarView mCalendar;
    public TextView mTextDate;
    private final SelectDecorator selectDecorator = new SelectDecorator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalendar = (MaterialCalendarView) findViewById(R.id.calendarView);
        mTextDate = (TextView) findViewById(R.id.textDate);

        mCalendar.setSelectedDate(CalendarDay.today());
        mCalendar.addDecorators(new GeneralDecorator(), new SaturdayDecorator(), new SundayDecorator(), selectDecorator);
        mTextDate.setText(mCalendar.getSelectedDate().getYear() + " / " + mCalendar.getSelectedDate().getMonth() + " / " + mCalendar.getSelectedDate().getDay());

        mCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectDecorator.setDate(date);
                mCalendar.invalidateDecorators();
                mTextDate.setText(date.getYear() + " / " + date.getMonth() + " / " + date.getDay());
            }
        });
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
}