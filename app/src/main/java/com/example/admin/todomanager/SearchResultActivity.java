package com.example.admin.todomanager;

import android.app.Activity;
import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 7/5/2017.
 */

public class SearchResultActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ToDoListAdapter toDoListAdapter;
 private ListView friendListView;
    SearchView sv;
    private MenuItem searchMenuItem;
   
    private ArrayList<ToDo> friendList;
    @Override
    public void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityresult);
        sv=(SearchView)findViewById(R.id.app_bar_search);
        sv.setOnQueryTextListener(this);
       // initFriendList();
     handleIntent(getIntent());


    }

    private void initFriendList() {
        ToDoOpenHelper toDoOpenHelper= new ToDoOpenHelper(this);
        SQLiteDatabase database= toDoOpenHelper.getReadableDatabase();
        Cursor cursor=database.query(ToDoOpenHelper.ToDo_Table_Name,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String title= cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_title));
            String Category =cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Category));
            String Date=cursor.getString(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Date));
            int id=cursor.getInt(cursor.getColumnIndex(ToDoOpenHelper.ToDo_Table_Id));
            ToDo t= new ToDo(id,title,Date,Category);
            friendList.add(t);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView)menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
    protected void onNewIntent(Intent intent){
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
       // searchView= (SearchView)findViewById(R.id.app_bar_search);

        if(intent.ACTION_SEARCH.equals(intent.getAction())){
            String query=intent.getStringExtra(SearchManager.QUERY);

        }


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        toDoListAdapter.filter(text);
        return true;
    }
}
