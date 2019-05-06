package com.msproject.myhome.mydays;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import org.joda.time.LocalDate;

public class CalendarDialog extends Dialog {
    private final int LAYOUT = R.layout.dialog_calendar;
    Context context;
    PlannerView plannerView;
    MyDialogListener myDialogListener;
    LocalDate localDate;

    public CalendarDialog(Context context, LocalDate localDate) {
        super(context);
        this.context = context;
        this.localDate = localDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        plannerView = findViewById(R.id.planner_view);

        plannerView.initView(this.localDate);
        this.plannerView.setDialogListener(myDialogListener);
    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.myDialogListener = dialogListener;

    }
}