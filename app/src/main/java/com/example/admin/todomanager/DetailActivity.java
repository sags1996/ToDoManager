package com.example.admin.todomanager;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DetailActivity extends AppCompatActivity {
    TextView titleTextView;
    TextView categoryTextView;
    TextView DateTextView;
    DatePickerDialog dp;
    long date;
    long dateAlarm;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.setTitle("About Todo");
        titleTextView = (EditText) findViewById(R.id.TitleView);
        categoryTextView=(EditText)findViewById(R.id.CategoryView);
        DateTextView=(EditText)findViewById(R.id.DateView);
        Button submitButton = (Button) findViewById(R.id.SubmitView);
        Intent i = getIntent();
         title =i.getStringExtra(IntentConstants.TODO_TITLE);
        if(title!=null){
            titleTextView.setText(title);}
        String Catg=i.getStringExtra(IntentConstants.TODO_CATEGORY);
        if(Catg!=null) {
            categoryTextView.setText(Catg);}
        String Date=i.getStringExtra(IntentConstants.TODO_DATE);
        if(Date!=null){
            DateTextView.setText(Date);}

DateTextView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Calendar newCalendar=Calendar.getInstance();
        int month=newCalendar.get(Calendar.MONTH);
        int year=newCalendar.get(Calendar.YEAR);
        showDatePicker(DetailActivity.this,year,month,1);
    }
});

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo t1 = new ToDo();
                Intent intent=getIntent();
                int Id= intent.getIntExtra("id",-2);

                int r= intent.getIntExtra("rc",0);
                // if(r==2){
                String newTitle = titleTextView.getText().toString();
                String newCategory=categoryTextView.getText().toString();
                String newDate=DateTextView.getText().toString();
                ToDoOpenHelper toDoOpenHelper =new ToDoOpenHelper(DetailActivity.this);
                SQLiteDatabase database= toDoOpenHelper.getWritableDatabase();
                ContentValues cv= new ContentValues();
                cv.put(ToDoOpenHelper.ToDo_Table_title,newTitle);
                cv.put(ToDoOpenHelper.ToDo_Table_Category,newCategory);
                cv.put(ToDoOpenHelper.ToDo_Table_Date,newDate);
                if(r==2){
                    database.insert(ToDoOpenHelper.ToDo_Table_Name,null,cv);}
                else  if (r==1){
                    database.update(ToDoOpenHelper.ToDo_Table_Name,cv,ToDoOpenHelper.ToDo_Table_Id+" = "+Id,null);
                }
                Intent i = new Intent();

                setResult(RESULT_OK, i);
                finish();


            }
        });

    }



    private void showDatePicker(final Context context, int iyear, int imonth, int iday) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                @Override
                  public void onDateSet(DatePicker datepicker, int year, int month, int dayOfMonth) {
                      Calendar calendar=Calendar.getInstance();
                    calendar.set(year,month,dayOfMonth);
                    date= calendar.getTime().getTime();
                    DateTextView.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                  }
              },iyear,imonth,iday);
    datePickerDialog.show();
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.add){
            Intent intent= new Intent(DetailActivity.this,ReminderActivity.class);
            intent.putExtra("name",title);
            startActivity(intent);

        /*  detime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar newCalendar=Calendar.getInstance();
                    int imonth=newCalendar.get(Calendar.MONTH);
                    int iyear=newCalendar.get(Calendar.YEAR);
                    int iday=1;

                    DatePickerDialog datePickerDialog = new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker datepicker, int year, int month, int dayOfMonth) {
                            Calendar calendar=Calendar.getInstance();
                            calendar.set(year,month,dayOfMonth);
                            dateAlarm= calendar.getTime().getTime();
                            detime.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                        }
                    },iyear,imonth,iday);
                    datePickerDialog.show();

                }
            });*/


                 /*   AlarmManager am =(AlarmManager)DetailActivity.this.getSystemService(Context.ALARM_SERVICE);
                    Intent i= new Intent(DetailActivity.this,AlarmReciever.class);
                    i.putExtra("name",title);
                    PendingIntent pendingIntent=PendingIntent.getBroadcast(DetailActivity.this,1,i,0);
                    am.set(AlarmManager.RTC,System.currentTimeMillis() + 2000,pendingIntent);
                    Toast.makeText(DetailActivity.this,"Alarm is set",Toast.LENGTH_SHORT).show();
*/
                   /*String date= detime.getText().toString();
                    String time= etime.getText().toString();
                    String str=date+" "+time+":00 UTC";
                    SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss zzz");
                    try {
                        Date d=sdf.parse(str);
                        long epoch= d.getTime();
                        etime.setText(""+epoch);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

*/
            /*    }
            });

            AlertDialog dialog =builder.create();
            dialog.show();*/

        }
        return true;
    }

    }


