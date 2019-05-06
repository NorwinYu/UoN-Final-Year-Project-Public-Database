package com.msproject.myhome.mydays;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class StatisticActivity extends AppCompatActivity {

    PieChart pieChart;
    private final String[] statisType = {"일간", "주간", "월간"};

    Button btn_daily, btn_weekly, btn_monthly;

    ConstraintLayout titleBar;
    CalendarDialog calendarDialog;
    Context context;
    MyDialogListener myDialogListener;
    TextView calendarDate, dateType, hasNoItem;
    int year; // 년도는 view에 존재하지 않기 때문에 변수로 담고 있어야함.
    MydaysDBHelper mydaysDBHelper;
    CategoryDBHelper categoryDBHelper;

    ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
    ArrayList<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);


        titleBar = findViewById(R.id.statics_title_bar);
        context = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.colorTitleBar));
        }

        LocalDate ld = new LocalDate();
        calendarDate = titleBar.findViewById(R.id.calendarDate);
        calendarDate.setText(ld.getMonthOfYear() + "월 " + ld.getDayOfMonth() + "일");

        dateType = titleBar.findViewById(R.id.dateType);

        hasNoItem = findViewById(R.id.sorryNoItem);

        ImageView lastdayButton = titleBar.findViewById(R.id.btPrev);
        ImageView nextdayButton = titleBar.findViewById(R.id.btNext);
        mydaysDBHelper = new MydaysDBHelper(this,"MyDays.db",null,1);
        categoryDBHelper = new CategoryDBHelper(this,"CATEGORY.db",null,1);
        year = ld.getYear();
        setMoveDay(lastdayButton, nextdayButton);
        setCalendarView();

        btn_daily = (Button) findViewById(R.id.btn_daily);
        btn_weekly = (Button) findViewById(R.id.btn_weekly);
        btn_monthly = (Button) findViewById(R.id.btn_monthly);

        pieChart = (PieChart)findViewById(R.id.piechart);


        btn_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChart(0);
            }
        });
        btn_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChart(1);
            }
        });
        btn_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChart(2);
            }
        });

        btn_daily.callOnClick();
    }


    public void setMoveDay(ImageView lastdayButton, ImageView nextdayButton){//day movement function
        lastdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate localDate = parsingLocalDate(calendarDate.getText().toString());
                LocalDate lastDate = localDate.minusDays(1);
                if(localDate.getYear() != lastDate.getYear()) year--;
                calendarDate.setText(lastDate.getMonthOfYear() + "월 " + lastDate.getDayOfMonth() + "일");

                updateChartForDate();
            }
        });

        nextdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDate localDate = parsingLocalDate(calendarDate.getText().toString());
                LocalDate nextDate = localDate.plusDays(1);
                if(localDate.getYear() != nextDate.getYear()) year++;
                calendarDate.setText(nextDate.getMonthOfYear() + "월 " + nextDate.getDayOfMonth() + "일");

                updateChartForDate();
            }
        });
    }

    public void setCalendarView(){//현재 TextView에 존재하는 날짜를 기준으로한 달의 CalendarDialog를 호출
        calendarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDialog = new CalendarDialog(context, parsingLocalDate(calendarDate.getText().toString()));
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                setCalendarDialogLisetener();//캘린더의 콜백을 받기 위한 리스너
                calendarDialog.show();
                calendarDialog.setCancelable(true);
                calendarDialog.setCanceledOnTouchOutside(true);
                Window window = calendarDialog.getWindow();
                int x = (int)(size.x * 0.8f);
                int y = (int)(size.y * 0.8f);

                window.setLayout(x,y);

            }
        });
    }

    /*
    * TitleBar의 <, >, 날짜 선택에 의해 차트가 변하게 하는 함수
    * */
    public void updateChartForDate(){
        if(dateType.getText().toString().equals("일간")){
            btn_daily.callOnClick();
        }
        else if(dateType.getText().toString().equals("주간")){
            btn_weekly.callOnClick();
        }
        else if(dateType.getText().toString().equals("월간")){
            btn_monthly.callOnClick();
        }
    }

    public void setCalendarDialogLisetener(){//CalendarDialog를 호출하고 해당 날짜를 클릭했을때 콜백을 받아 날짜 TextView와 전역변수 year를 수정함.
        myDialogListener = new MyDialogListener() {
            @Override
            public void onPostClicked(Category category) {

            }

            @Override
            public void onModifyClicked(Category category, int index) {

            }

            @Override
            public void onNegativeClicked() {

            }

            @Override
            public void onCalendatItemClicked(LocalDate localDate) {
                calendarDate.setText(localDate.getMonthOfYear() + "월 " + localDate.getDayOfMonth() + "일");
                year = localDate.getYear();
                calendarDialog.dismiss();
                updateChartForDate();
            }
        };
        calendarDialog.setDialogListener(myDialogListener);
    }

    public void updateChart(int dateType){
        pieEntries = new ArrayList<>();
        colors = new ArrayList<>();

        ArrayList<String> category = new ArrayList<>();
        ArrayList<Integer> times = new ArrayList<>();

        LocalDate ld = this.parsingLocalDate(calendarDate.getText().toString());
        String todayString = ld.toString().replace("-","").substring(2);

        Toast.makeText(getApplicationContext(), todayString + "의 " + statisType[dateType] + "일정입니다.", Toast.LENGTH_SHORT).show();
        if(dateType == 0){
            getOneDayPlanner(category, times, todayString);
            this.dateType.setText("일간");
        }
        else if(dateType == 1){
            ArrayList<String> weekStrings = getOneWeekDays(todayString);

            for(String date : weekStrings){
                getOneDayPlanner(category, times, date);
            }
            this.dateType.setText("주간");
        }
        else if(dateType == 2){
            ArrayList<String> monthStrings = getOneMonthDays(todayString);

            for(String date : monthStrings){
                getOneDayPlanner(category, times, date);
            }
            this.dateType.setText("월간");
        }

        if(category.size() > 0){
            hasNoItem.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);

            Log.d("TEXT VIEW CHECK", "updateChart: " + hasNoItem.getText().toString());
            for(int i = 0; i < category.size(); i++){
                pieEntries.add(new PieEntry(times.get(i), category.get(i)));
                colors.add(ColorMakeHelper.getColor(category.get(i)));
            }
            makeChart();

            pieChart.notifyDataSetChanged();
            pieChart.invalidate();
        }
        else{
            hasNoItem.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE);
        }
    }

    public ArrayList<String> getOneMonthDays(String date){
        ArrayList<String> monthDays = new ArrayList<>();
        Calendar c1 = Calendar.getInstance();

        c1.set(Integer.parseInt("20" + date.substring(0,2)),Integer.parseInt(date.substring(2,4)) - 1,Integer.parseInt(date.substring(4,6)));

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;

        for(int d = 1; d <= c1.getActualMaximum(Calendar.DAY_OF_MONTH); d++){
            monthDays.add(makeDate(year1,month1, d));
        }

        return monthDays;
    }

    public ArrayList<String> getOneWeekDays(String date){
        ArrayList<String> weekDays = new ArrayList<>();
        Calendar c1 = Calendar.getInstance();

        c1.set(Integer.parseInt("20" + date.substring(0, 2)), Integer.parseInt(date.substring(2, 4)) - 1,
                Integer.parseInt(date.substring(4, 6)));

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH) + 1;

        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        System.out.println(c1.get(Calendar.DAY_OF_WEEK));

        if (c1.get(Calendar.DAY_OF_WEEK) != 1) {
            day1 -= c1.get(Calendar.DAY_OF_WEEK) - 1;
        }

        int day7 = day1 + 6;

        System.out.println(day1 + ", " + day7);
        System.out.println("month : " + month1);

        if (day1 <= 0) {
            int prevMonth = month1 - 1;
            c1.set(year1, prevMonth - 1, 1);
            int prevDay1 = c1.getActualMaximum(Calendar.DAY_OF_MONTH) + day1;
            int prevDay7 = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
            c1.set(Integer.parseInt("20" + date.substring(0, 2)), Integer.parseInt(date.substring(2, 4)) - 1,
                    Integer.parseInt(date.substring(4, 6)));

            for (int i = prevDay1; i <= prevDay7; i++) {
                weekDays.add(makeDate(year1, prevMonth, i));
            }

        }


        for (int d = day1; d <= day7; d++) {
            if (d > 0 && d <= c1.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                weekDays.add(makeDate(year1, month1, d));
            }
        }

        if(day7 > c1.getActualMaximum(Calendar.DAY_OF_MONTH)){
            int nextMonth = month1 + 1;
            if(month1 + 1 > 12) {
                year1++;
                nextMonth = 1;
            }
            int nextDay1 = 1;
            int nextDay7 = day7 - c1.getActualMaximum(Calendar.DAY_OF_MONTH);


            for (int i = nextDay1; i <= nextDay7; i++) {
                weekDays.add(makeDate(year1, nextMonth, i));
            }
        }
        return weekDays;
    }

    public String makeDate(int y, int m, int d){
        String YY = "";
        String MM = "";
        String DD = "";

        YY += (y +"").substring(2);

        if(m < 10){
            MM = "0";
        }
        MM += m + "";

        if(d < 10){
            DD = "0";
        }
        DD += d + "";

        return YY + MM + DD;
    }
    public void getOneDayPlanner(ArrayList<String> category, ArrayList<Integer> times, String date){
        ArrayList<Event> events = mydaysDBHelper.getResult(date);

        int index;

        for(int i = 0; i < events.size(); i++) {
            Event myEvent = events.get(i);
            if (category.contains(myEvent.getCategoryName())) {
                index = category.indexOf(myEvent.getCategoryName());
                times.set(index, times.get(index) + 1);
            } else {
                category.add(myEvent.getCategoryName());
                times.add(1);
            }
        }
    }
    public ArrayList<String> getColor(ArrayList<PieEntry> pieEntries){
        ArrayList<String> result = new ArrayList<>();

        for(PieEntry p : pieEntries){
            result.add(categoryDBHelper.getColor(p.getLabel()));
        }

        return result;
    }

    public void makeChart(){
        Description description = new Description();
        LocalDate localDate = parsingLocalDate(calendarDate.getText().toString());
        description.setText(year + "." + localDate.getMonthOfYear() + "." + localDate.getDayOfMonth()); //라벨 : 오늘 날짜 적으면 좋을 듯
        description.setTextSize(15);
        pieChart.setDescription(description);


        PieDataSet dataSet = new PieDataSet(pieEntries,"");

        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
    }
    public LocalDate parsingLocalDate(String str){//Titlebar의 날짜 텍스트를 localdate로 변환 -> year는 전역변수로 불러옴(year가 변경되는 경우 수정 필요!)
        int month;
        int day;
        String[] parsingMonth = str.split("월 ");
        month = Integer.parseInt(parsingMonth[0]);
        String[] parsingDay = parsingMonth[1].split("일");
        day = Integer.parseInt(parsingDay[0]);
        LocalDate localDate = new LocalDate(year, month, day);
        return localDate;
    }
}
