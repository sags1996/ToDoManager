package com.example.admin.todomanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    int pos;
    ListView listView;
    ArrayList<ToDo> todoList;
    ToDoListAdapter toDoListAdapter;
    View.OnClickListener myOnClickListener;
    SearchView searchView;

   // CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.ListView);
        todoList=new ArrayList<>();
        toDoListAdapter= new ToDoListAdapter(this,todoList);
        listView.setAdapter(toDoListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("rc",1);
                i.putExtra("id",todoList.get(position).id);
                i.putExtra(IntentConstants.TODO_TITLE,todoList.get(position).title);
                i.putExtra(IntentConstants.TODO_CATEGORY,todoList.get(position).category);
                i.putExtra(IntentConstants.TODO_DATE,todoList.get(position).date);
                startActivityForResult(i, 1);




            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("                    Delete         ");
                builder.setCancelable(false);
                View v=getLayoutInflater().inflate(R.layout.dialog,null);
                final TextView tv=(TextView) v.findViewById(R.id.dialogTextView);
                // final EditText et=(EditText) v.findViewById(R.id.EditText);
                tv.setText("Are you sure ??");
                builder.setView(v);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int Id =todoList.get(position).id;
                        ToDoOpenHelper expenseOpenHelper = new ToDoOpenHelper(MainActivity.this);
                        SQLiteDatabase db= expenseOpenHelper.getWritableDatabase();
                        String query="delete from "+ToDoOpenHelper.ToDo_Table_Name+" where id ="+Id;
                        db.execSQL(query);
                        updateToDoList();

                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog =builder.create();
                dialog.show();
                return true;
            }


        });
                /*int Id =todoList.get(position).id;
                ToDoOpenHelper expenseOpenHelper = new ToDoOpenHelper(MainActivity.this);
                SQLiteDatabase db= expenseOpenHelper.getWritableDatabase();
                String query="delete from "+ToDoOpenHelper.ToDo_Table_Name+" where id ="+Id;
                db.execSQL(query);
                updateToDoList();

                return true;*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(MainActivity.this, DetailActivity.class);
                // i2.putExtra("id",-1);
                i2.putExtra("rc",2);
                startActivityForResult(i2,2);
//                Snackbar.make(view, "ToDo added Successfully", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
       // searchView=(SearchView)findViewById(R.id.app_bar_search);
      //  searchView.setOnQueryTextListener(this);

        updateToDoList();
    }
    private void updateToDoList() {
        ToDoOpenHelper toDoOpenHelper= new ToDoOpenHelper(this);
        todoList.clear();
        SQLiteDatabase database= toDoOpenHelper.getReadableDatabase();
        Cursor cursor=database.query(ToDoOpenHelper.ToDo_Table_Name,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String title= cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_title));
            String Category =cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Category));
            String Date=cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Date));
            int id=cursor.getInt(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Id));
            ToDo t= new ToDo(id,title,Date,Category);
            todoList.add(t);
        }
     final  CoordinatorLayout coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator);

       Snackbar.make(coordinatorLayout, "Successfull!!", Snackbar.LENGTH_LONG).setAction("Action",myOnClickListener).show();
       toDoListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
         if(id==R.id.clearall){
             ToDoOpenHelper expenseOpenHelper = new ToDoOpenHelper(MainActivity.this);
             SQLiteDatabase db= expenseOpenHelper.getWritableDatabase();
             String query= "delete from "+ToDoOpenHelper.ToDo_Table_Name;
             db.execSQL(query);
             updateToDoList();
             return true;
         }
         if(id==R.id.sortall){
             ToDoOpenHelper expenseOpenHelper = new ToDoOpenHelper(MainActivity.this);
             SQLiteDatabase db= expenseOpenHelper.getWritableDatabase();
             todoList.clear();
             Cursor cursor =db.query(ToDoOpenHelper.ToDo_Table_Name,null,null,null,null,null,ToDoOpenHelper.ToDo_Table_title+" ASC");
             while (cursor.moveToNext()){
                 String title= cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_title));
                 String Category =cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Category));
                 String Date=cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Date));
                 int Id=cursor.getInt(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Id));
                 ToDo t= new ToDo(Id,title,Date,Category);
                 todoList.add(t);
             }
             final  CoordinatorLayout coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator);

             Snackbar.make(coordinatorLayout, "Successfull!!", Snackbar.LENGTH_LONG).setAction("Action",myOnClickListener).show();
             toDoListAdapter.notifyDataSetChanged();
             return true;
         }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {

                updateToDoList();

            }
            else if(resultCode==RESULT_CANCELED){

            }
        }
        if(requestCode==2){
            if(resultCode==RESULT_OK){

                updateToDoList();


            }
            else if(resultCode==RESULT_CANCELED){

            }
        }

    }


}
