package com.msproject.myhome.mydays;

import java.util.ArrayList;

public interface DragEventCallBackListener {
    public void onDragFinished(ArrayList<Event> events);
    public void setCanDrag(boolean canDrag);
    public boolean dragable();
    public void setStartPos(int startPos);
    public void click(int position);
    public void setColor(String color);
    public String getColor();
}
