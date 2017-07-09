package com.example.admin.todomanager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ReminderActivity extends AppCompatActivity {
    EditText time;
    EditText date;
    Button rem;
    long dateAlarm;
    long timeAlarm;
    String name;
int y,m,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        this.setTitle("SET REMINDER");
        Intent i = getIntent();
        name = i.getStringExtra("name");
        time = (EditText) findViewById(R.id.TimeRem);
        date = (EditText) findViewById(R.id.DateRem);
        rem=(Button) findViewById(R.id.RemSub);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int imonth = newCalendar.get(Calendar.MONTH);
                int iyear = newCalendar.get(Calendar.YEAR);
                int iday = 1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(ReminderActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                       dateAlarm = calendar.getTime().getTime();
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        y=year;
                        m=month;
                        d=dayOfMonth;

                    }
                }, iyear, imonth, iday);
                datePickerDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrenttime=Calendar.getInstance();
                int hour=mcurrenttime.get(Calendar.HOUR);
                int minute=mcurrenttime.get(Calendar.MINUTE);
                TimePickerDialog timepicker=new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay+":"+minute);
                        mcurrenttime.setTimeZone(TimeZone.getTimeZone("IST"));
                        mcurrenttime.set(y,m,d,hourOfDay,minute,0);
                       //  timeAlarm=mcurrenttime.getTime().getTime();
                      //  date.setText(""+dateAlarm);
                        if(hourOfDay<13){
                        hourOfDay=(hourOfDay*60)+minute;}
                        else{
                            hourOfDay=hourOfDay-12;
                            hourOfDay=(hourOfDay*60)+minute;
                        }
                     timeAlarm= TimeUnit.MINUTES.toMillis(hourOfDay);
                    }
                },hour,minute,true);
                timepicker.setTitle("Select Time");
                timepicker.show();

            }
        });
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ReminderActivity.this,AlarmReciever.class);
                intent.putExtra("name",name);
                AlarmManager am =(AlarmManager)ReminderActivity.this.getSystemService(Context.ALARM_SERVICE);
                PendingIntent pendingIntent= PendingIntent.getBroadcast(ReminderActivity.this,1,intent,0);
               long mils=(dateAlarm+timeAlarm);
                 //time.setText(""+timeAlarm);
                am.set(AlarmManager.RTC,mils,pendingIntent);
                Toast.makeText(ReminderActivity.this,"Alarm is set",Toast.LENGTH_SHORT).show();
            }
        });


    }
}