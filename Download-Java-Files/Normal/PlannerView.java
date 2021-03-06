package com.msproject.myhome.mydays;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlannerView extends ConstraintLayout {
    TextView yearText;
    TextView monthText;
    LinearLayout calendarSelectLayout;
    ConstraintLayout calendarSelectView;//년,달을 변경하는 view
    boolean isCalendarSelectClicked;
    ViewPager viewPager;
    Context context;
    Button calendarSelectButton;
    boolean isExistToday;
    Button todayButton;
    Button previousButton;
    Button nextButton;
    LocalDate date;
    HashMap<String, ArrayList<Event>> hashMap;
    MyDialogListener dialogListener;

    public PlannerView(Context context) {
        super(context);
        this.context = context;
        this.isCalendarSelectClicked = false;
    }

    public PlannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.isCalendarSelectClicked = false;
    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public void initView(final LocalDate localDate) {
        this.date = localDate;
        removeAllViews();
        this.isExistToday = false;

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infService);

        final View view = inflater.inflate(R.layout.planner_view, this, false);
        addView(view);

        hashMap = new HashMap<>();

        todayButton = view.findViewById(R.id.calendar_today_bt);
        todayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                date = new LocalDate();
                viewPager.setCurrentItem(findIndex());
                setCalendarSelectLayout();
            }
        });


        if(this.date != null) {
            yearText = view.findViewById(R.id.calendar_year_text);
            monthText = view.findViewById(R.id.calendar_month_text);

            viewPager = view.findViewById(R.id.calendar_viewpager);
            viewPager.setAdapter(new CalendarPagerAdapter(this.date));
            viewPager.setCurrentItem(findIndex());
            setCalendarSelectLayout();

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position < findIndex()) {
                        date = date.minusMonths(1);
                    } else if(position > findIndex()) {
                        date = date.plusMonths(1);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state == ViewPager.SCROLL_STATE_IDLE) {
                        viewPager.setCurrentItem(findIndex());
                        setCalendarSelectLayout();
                    }
                }
            });

            calendarSelectView = view.findViewById(R.id.calendar_select_view); // number picker부분
            calendarSelectView.setOnClickListener(new OnClickListener() { // 검은배경 클릭시
                @Override
                public void onClick(View view) {

                }
            });
            calendarSelectButton = view.findViewById(R.id.calendar_select_bt);
            calendarSelectLayout = view.findViewById(R.id.calendar_select_layout);
            calendarSelectLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final NumberPicker yearPicker = view.findViewById(R.id.year_picker);
                    final NumberPicker monthPicker = view.findViewById(R.id.month_picker);


                    calendarSelectButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isCalendarSelectClicked = false;
                            calendarSelectView.setVisibility(View.GONE);
                            initView(new LocalDate(yearPicker.getValue(), monthPicker.getValue(), 1));
                        }
                    });

                    if(!isCalendarSelectClicked) {
                        isCalendarSelectClicked = true;

                        calendarSelectButton.setVisibility(View.VISIBLE);

                        calendarSelectView.setVisibility(View.VISIBLE);
                        calendarSelectView.setAlpha(0.0f);
                        calendarSelectView.animate().alpha(1.0f);

                        monthPicker.setWrapSelectorWheel(false);
                        monthPicker.setMinValue(1);
                        monthPicker.setMaxValue(12);
                        monthPicker.setValue(localDate.monthOfYear().get());

                        yearPicker.setWrapSelectorWheel(false);
                        yearPicker.setMinValue(2000);
                        yearPicker.setMaxValue(2099);
                        yearPicker.setValue(localDate.year().get());
                    } else {
                        calendarSelectView.animate().alpha(0.0f);
                        calendarSelectView.setVisibility(View.GONE);
                        isCalendarSelectClicked = false;

                        calendarSelectButton.setVisibility(View.GONE);
                    }
                }
            });

            previousButton = view.findViewById(R.id.calendar_previous_bt);
            previousButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    date = date.minusMonths(1);
                    viewPager.setCurrentItem(findIndex());
                    setCalendarSelectLayout();
                }
            });
            nextButton = view.findViewById(R.id.calendar_next_bt);
            nextButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    date = date.plusMonths(1);
                    viewPager.setCurrentItem(findIndex());
                    setCalendarSelectLayout();
                }
            });
        }

    }


    public class CalendarPagerAdapter extends PagerAdapter {
        int size;
        int maxYear = 2099;
        int minYear = 2000;

        LocalDate minDate;
        LocalDate date;

        public CalendarPagerAdapter(LocalDate localDate) {
            minDate = new LocalDate(2000, 1, 1);
            this.size = (maxYear - minYear + 1) * 12;
            this.date = localDate.withDayOfMonth(1);
        }



        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.planner_recyclerview, null);

            RecyclerView recyclerView = view.findViewById(R.id.calendar_recyclerview);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new CalendarRecyclerViewAdapter(minDate.plusMonths(position)));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ConstraintLayout) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return this.size;
        }
    }

    public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public LocalDate calendar;
        private int startDay;
        private int lastDay;
        private int count;

        private int calendarCount;
        private int plusCount;

        LocalDate previousCalendar;
        LocalDate nextCalendar;

        ImageView eventExist;

        Context mContext;

        public CalendarRecyclerViewAdapter(LocalDate localDate) {
            this.calendar = localDate;
            this.startDay = this.calendar.getDayOfWeek() == 7 ? 0 : this.calendar.getDayOfWeek(); // 1일의 요일
            this.lastDay = this.calendar.toDateTimeAtStartOfDay().dayOfMonth().withMaximumValue().getDayOfWeek() == 7 ? 0 : this.calendar.toDateTimeAtStartOfDay().dayOfMonth().withMaximumValue().getDayOfWeek(); // 마지막날 요일(일요일은 7)
            this.calendarCount = this.calendar.dayOfMonth().withMaximumValue().getDayOfMonth();
            this.count = startDay + (6 - lastDay) + this.calendarCount;

            this.plusCount = 0;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.planner_item, parent, false);//임시
            view.setLayoutParams(new LinearLayout.LayoutParams(parent.getWidth() / 7, parent.getHeight() / (this.count / 7)));
            final ViewHolder viewHolder = new ViewHolder(view);


            OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogListener.onCalendatItemClicked(viewHolder.getLocalDate());
                }
            };

            view.setOnClickListener(listener); // item click listener

            return viewHolder;
        }

        @Override
        public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
            super.onViewRecycled(holder);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;

            if (position < startDay) {// 전달
                this.previousCalendar = this.calendar.minusDays(startDay - position);
                viewHolder.setLocalDate(this.previousCalendar);
                viewHolder.dateText.setText(this.previousCalendar.dayOfMonth().getAsText());
                viewHolder.dateText.setTextColor(Color.parseColor("#A9A9A9"));

            } else if (position >= startDay + this.calendarCount) { // 다음달
                this.nextCalendar = this.calendar.plusDays(plusCount++);
                viewHolder.setLocalDate(this.nextCalendar);
                viewHolder.dateText.setText(this.nextCalendar.dayOfMonth().getAsText());
                viewHolder.dateText.setTextColor(Color.parseColor("#A9A9A9"));

            } else { // 현재달
                viewHolder.setLocalDate(this.calendar);
                viewHolder.dateText.setText(this.calendar.dayOfMonth().getAsText());
                if(this.calendar.getDayOfWeek() == 6) { // 토요일
                    viewHolder.dateText.setTextColor(Color.BLUE);
                } else if(this.calendar.getDayOfWeek() == 7) { // 일요일
                    viewHolder.dateText.setTextColor(Color.RED);
                } else {
                    viewHolder.dateText.setTextColor(Color.parseColor("#000000"));

                }


                for(Map.Entry<String, ArrayList<Event>> entry : hashMap.entrySet()) {
                    ArrayList<Event> events = entry.getValue();

                    for(int i = 0; i < events.size(); i++) {
                        Event event = events.get(i);


                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        if(entry.getKey().equals(format.format(this.calendar.toDate()))) {
//                            viewHolder.adapter.events.add(event);
                        }
                    }
                }
                LocalDate today = viewHolder.getLocalDate();
                SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");

                String key = format.format(today.toDate());
//                viewHolder.adapter.notifyDataSetChanged();
                String todayEvent = mContext.getSharedPreferences("EVENT", Context.MODE_PRIVATE).getString(key, "");

                if(!todayEvent.equals("")){
                    eventExist.setVisibility(View.VISIBLE);
                }


                this.calendar = this.calendar.plusDays(1);
            }
        }

        @Override
        public int getItemCount() {
            return this.count;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView dateText;
            LocalDate localDate;
            ListView dateListView;

            public ViewHolder(View itemView) {
                super(itemView);
                dateText = itemView.findViewById(R.id.planner_item_day);

            }

            public void setLocalDate(LocalDate localDate) {
                this.localDate = localDate;
            }

            public LocalDate getLocalDate() {
                return this.localDate;
            }
        }

    }

    private boolean isExistToday(LocalDate date) {
        int[] todays = new LocalDate().getValues(); // size : 3 [0] = year, [1] = month, [2] = day
        int[] dates = date.getValues();

        return todays[0] == dates[0] && todays[1] == dates[1] && todays[2] == dates[2];
    }

    private int findIndex() {
        return ((this.date.getYear() - 2000) * 12) + this.date.getMonthOfYear() - 1;
    }

    private void setCalendarSelectLayout() {
        yearText.setText(date.year().getAsText());
        monthText.setText(String.valueOf(date.monthOfYear().get()));

        isExistToday = isExistToday(date);

        if(!isExistToday) {
            todayButton.setVisibility(View.VISIBLE);
        } else {
            todayButton.setVisibility(View.GONE);
        }
    }
}