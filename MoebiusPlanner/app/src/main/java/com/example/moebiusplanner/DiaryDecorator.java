package com.example.moebiusplanner;

import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DiaryDecorator implements DayViewDecorator {

    private DiaryDbHelper diaryDbHelper;

    public DiaryDecorator(Context context) {
        diaryDbHelper = new DiaryDbHelper(context);
        diaryDbHelper.open();
        diaryDbHelper.create();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return diaryDbHelper.getDiary(day.getYear(), day.getMonth(), day.getDay()) != null;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(10, Color.MAGENTA));
    }

}
