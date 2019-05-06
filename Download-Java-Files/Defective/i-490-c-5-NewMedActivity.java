package com.sabrcare.app.medicine;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sabrcare.app.AlarmReceiver;
import com.sabrcare.app.R;


import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmResults;


public class NewMedActivity extends AppCompatActivity {
    public Spinner day_phase;
    public EditText med_name;
    public TextView reminderTime;
    public TimePickerDialog timePickerDialog;
    public Button save;
    Switch alarmSwitch;

    public static final String PREFS_NAME = "SharedPrefs";

    SharedPreferences prefs;
    int hr, min;
    AlarmManager alarmManager;
    Intent alarmIntent;
    PendingIntent pendingIntent;

    MedicineModel medicineModel;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medication);
        reminderTime = findViewById(R.id.time);
        med_name = findViewById(R.id.medName);
        day_phase = findViewById(R.id.spinner_day_phase);
        alarmSwitch=findViewById(R.id.reminderSwitch);
        save = findViewById(R.id.done);
        alarmSwitch.setTextOff("Reminder Off");
        alarmSwitch.setTextOn("Reminder On");
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(alarmSwitch.isChecked()){
                    alarmSwitch.setText("Reminder on");
                }
                else {
                    alarmSwitch.setText("Reminder off");
                }
            }
        });

        realm=Realm.getDefaultInstance();

        if(getIntent().getAction().equalsIgnoreCase("Edit")){
            RealmResults<MedicineModel> thisMedicine = realm.where(MedicineModel.class).
                    equalTo("medName",getIntent().getStringExtra("medName")).findAll();
            medicineModel=thisMedicine.first();
            med_name.setText(medicineModel.getMedName());
            alarmSwitch.setChecked(medicineModel.isReminderOn());
            reminderTime.setText(medicineModel.getTime());
        }
        else {
            medicineModel=new MedicineModel();
        }

        final ArrayAdapter<CharSequence> adapter_day_phase = ArrayAdapter.createFromResource(NewMedActivity.this, R.array.Day_Phase, R.layout.support_simple_spinner_dropdown_item);
        adapter_day_phase.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        day_phase.setAdapter(adapter_day_phase);


        reminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                final int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(NewMedActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hr = selectedHour;
                        min = selectedMinute;

                        String sHour;
                        String sMin;
                        if (hr < 10) {
                            sHour = "0" + hr;
                        } else {
                            sHour = "" + hr;
                        }
                        if (min < 10) {
                            sMin = "0" + min;
                        } else {
                            sMin = "" + min;
                        }
                        reminderTime.setText(new StringBuilder(sHour + ":" + sMin));

                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent = new Intent(this, AlarmReceiver.class);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, hr);
                calendar.set(Calendar.MINUTE, min);

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                realm.beginTransaction();
                medicineModel.setMedName(med_name.getText().toString());
                medicineModel.setReminderOn(alarmSwitch.isChecked());
                medicineModel.setTime(reminderTime.getText().toString());
                medicineModel.setDayPhase(day_phase.getSelectedItem().toString());
                realm.insertOrUpdate(medicineModel);
                realm.commitTransaction();
                //TODO Update data in server

                if(medicineModel.isReminderOn()){
                    Intent openAlarm = new Intent(NewMedActivity.this, AlarmReceiver.class);
                    openAlarm.putExtra("medication", medicineModel.getMedName());

                    pendingIntent = PendingIntent.getBroadcast(NewMedActivity.this,
                            PendingIntent.FLAG_UPDATE_CURRENT,openAlarm,0);

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                    System.out.println(">>>>>>>>>>>SENT for "+openAlarm.getStringExtra("medication"));
                }


                Toast.makeText(NewMedActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                NewMedActivity.this.finish();

            }
        });
    }
}
