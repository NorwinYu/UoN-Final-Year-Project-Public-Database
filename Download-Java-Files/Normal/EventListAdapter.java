package com.msproject.myhome.mydays;

import android.annotation.SuppressLint;

import android.content.ClipDescription;

import android.annotation.TargetApi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventListAdapter extends BaseAdapter {
    ArrayList<Event> events;
    ArrayList<View> views;
    Context context;
    CategoryDBHelper dbHelper;
    DragEventCallBackListener dragEventCallBackListener;
    int preAction;
    int lastIndex;
    int startPos;
    int endPos;
    float deltaY;

    boolean isDraging;

    Point touch;


    public void setDragEventCallBackListener(DragEventCallBackListener dragEventCallBackListener){
        this.dragEventCallBackListener = dragEventCallBackListener;
    }

    public void setDragable(boolean b){
        this.dragEventCallBackListener.setCanDrag(b);
    }

    public EventListAdapter(ArrayList<Event> events, Context context){
        this.events = events;
        this.context = context;
        dbHelper = new CategoryDBHelper(context, "CATEGORY.db", null, 1);
        views = new ArrayList<>();
        touch = new Point();
//        setTouchBoundaryListener();
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setItem(int position, Event event){
        this.events.set(position, event);
    }

    public View getView(int position){
        return views.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        TextView timeTextView = view.findViewById(R.id.event_time);
        final TextView categoryName = view.findViewById(R.id.category_name);
        TextView eventContent = view.findViewById(R.id.event_content);
        if(events.get(position).eventNo < 10){
            timeTextView.setText("0"+events.get(position).getEventNo()+":00");
        }
        else{
            timeTextView.setText(events.get(position).getEventNo() + ":00");
        }
        categoryName.setText(events.get(position).getCategoryName());
        eventContent.setText(events.get(position).getEventContent());
        if(events.get(position).getCategoryName() != ""){
            String color = dbHelper.getColor(events.get(position).getCategoryName());
            categoryName.setBackgroundColor(Color.parseColor(color));
            eventContent.setBackgroundColor(Color.parseColor(color));
            categoryName.setTextColor(Color.parseColor("#FFFFFF"));
            eventContent.setTextColor(Color.parseColor("#FFFFFF"));
        }

        view.setId(position);
        final MyListViewDragListener myListViewDragListener = new MyListViewDragListener();
        view.setOnDragListener(myListViewDragListener);
        view.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if((!dragEventCallBackListener.dragable())&& (!categoryName.getText().equals("")) && event.getAction() == MotionEvent.ACTION_DOWN) {
//                    v.setBackground(new RippleDrawable(ColorStateList.valueOf(Color.WHITE)),null,null);
                    dragEventCallBackListener.click(position);

                }
                else{
                    deltaY = event.getY();
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder();
                    startPos = position;

//                if(isDraging){
//                    return false;
//                }
                    v.startDrag(null, shadowBuilder, null, 0);
                    isDraging = true;
                    dragEventCallBackListener.setStartPos(startPos);

                    v.startDragAndDrop(null, shadowBuilder, null, 0);
                }

                return true;
            }

        });
        views.add(view);
        return view;
    }


    private class MyListViewDragListener implements View.OnDragListener{
        boolean up;
        boolean white;
        String color;
        @SuppressLint("ResourceType")
        @Override
        public boolean onDrag(View v, DragEvent event) {
            color = dragEventCallBackListener.getColor();
            if(!dragEventCallBackListener.dragable()){
                return false;
            }
            final int action = event.getAction();
            Log.d("action2==",Integer.toString(action));
            switch(action){
                case DragEvent.ACTION_DRAG_LOCATION:

                    if(startPos > v.getId()){//위로 드래그일 경우
                        up = true;
                    }
                    else{//밑으로
                        up = false;
                    }
                    if(deltaY > event.getY()){
                        if(up || startPos == v.getId()){//위로 드래그 && 정상
                            white = false;
                        }
                        else{//위로 드래그인 상황 && 드래그 취소로 밑으로 내려오는경우
                            white = true;
                        }
                    }
                    else{
                        if(!up || startPos == v.getId()){//밑으로 드래그 && 정상
                            white = false;
                        }
                        else{//밑으로 드래그 && 드래그 취소로 위로 올라가는 경우
                            white = true;
                        }
                    }
                    deltaY = event.getY();
                    return true;
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(Color.parseColor(color));
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:

                    if(preAction == DragEvent.ACTION_DRAG_EXITED) {
                        if (lastIndex == 5) {
                            endPos = 5;

                        } else {
                            endPos = 0;
                        }
                        ArrayList<Event> selectedEvent;
                        if (startPos > endPos) {
                            selectedEvent = new ArrayList<>((events.subList(endPos, startPos + 1)));
                        } else {
                            selectedEvent = new ArrayList<>(events.subList(startPos, endPos + 1));
                        }
                        dragEventCallBackListener.onDragFinished(selectedEvent);
                        dragEventCallBackListener.setCanDrag(false);
                        isDraging = false;
                    }


                    return true;
                case DragEvent.ACTION_DRAG_EXITED://되면 여기서 이벤트 추가해서 eventActivity로 콜백 ㄱㄱ
                    preAction = event.getAction();
                    lastIndex = v.getId();
                    if(white){
                        v.setBackgroundColor(Color.WHITE);
                    }
                  return true;
                case DragEvent.ACTION_DROP:
                    endPos = v.getId();
                    ArrayList<Event> selectedEvent;
                    if(startPos > endPos){
                        selectedEvent = new ArrayList<>((events.subList(endPos, startPos + 1)));
                    }
                    else{
                         selectedEvent = new ArrayList<>(events.subList(startPos, endPos + 1));
                    }
                    dragEventCallBackListener.onDragFinished(selectedEvent);
                    dragEventCallBackListener.setCanDrag(false);
                    Log.d("endPos==",Integer.toString(endPos));
                    isDraging = false;
                    return true;
                default:
                    break;
            }
            return false;
        }
    }


}
