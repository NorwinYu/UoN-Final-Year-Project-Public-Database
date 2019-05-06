package com.msproject.myhome.mydays;

import org.joda.time.LocalDate;

public interface MyDialogListener {
    public void onPostClicked(Category category);
    public void onModifyClicked(Category category, int index);
    public void onNegativeClicked();
    public void onCalendatItemClicked(LocalDate localDate);
}
