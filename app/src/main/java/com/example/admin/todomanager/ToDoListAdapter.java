package com.example.admin.todomanager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Admin on 7/3/2017.
 */

class ToDoListAdapter extends ArrayAdapter<ToDo> { ArrayList<ToDo> toDoArrayList;
     private  ArrayList<ToDo>FilteredList;
    private SearchResultActivity searchResultActivity;
    Context context;
  //  private FriendFilter friendFilter;



    public ToDoListAdapter(@NonNull Context context, ArrayList<ToDo> toDoArrayList) {
        super(context,0);
        this.context=context;
        this.toDoArrayList=toDoArrayList;
   this.FilteredList=toDoArrayList;
        getFilter();
    }
    @Override
    public int getCount(){
        return toDoArrayList.size();
    }



    static class ToDoViewHolder{

        TextView nameTextView;
        TextView categoryTextView ;
        TextView DateTextView ;

        ToDoViewHolder(TextView nameTextView, TextView categoryTextView, TextView DateTextView){
            this.nameTextView = nameTextView;
            this.categoryTextView = categoryTextView;
            this.DateTextView = DateTextView;
        }


    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //convertView - to recycle views for smooth scrolling ,space and time management,.
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem,null);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.TitletextView);
            TextView categoryTextView = (TextView) convertView.findViewById(R.id.CategorytextView);
            TextView DateTextView = (TextView) convertView.findViewById(R.id.DatetextView);
            ToDoViewHolder toDoViewHolder = new ToDoViewHolder(nameTextView, categoryTextView,DateTextView);
            convertView.setTag(toDoViewHolder);

        }

        ToDo e = toDoArrayList.get(position);
        ToDoViewHolder todoViewHolder = (ToDoViewHolder)convertView.getTag();
        todoViewHolder.nameTextView.setTextColor(Color.BLACK);
        todoViewHolder.categoryTextView.setTextColor(Color.BLACK);
        todoViewHolder.DateTextView.setTextColor(Color.BLACK);
        todoViewHolder.nameTextView.setText(e.title);
        todoViewHolder.categoryTextView.setText(e.category);
        todoViewHolder.DateTextView.setText(e.date);

        return  convertView;
    }

   /* public Filter getFilter(){
if(friendFilter==null){
    friendFilter=new FriendFilter();
}
    return  friendFilter;
}
private class FriendFilter extends Filter{

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            ArrayList<ToDo> tempList = new ArrayList<>();
           for(ToDo todo: toDoArrayList) {
               if (todo.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                   tempList.add(todo);
               }
           }
               filterResults.count = tempList.size();
               filterResults.values = tempList;
        }
        else{
            filterResults.count=toDoArrayList.size();
             filterResults.values=toDoArrayList;
        }
        return  filterResults;
    }
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
 FilteredList=(ArrayList<ToDo>)results.values;
        notifyDataSetChanged();
    }
}*/
   public void filter(String text) {
       text=text.toLowerCase();
       FilteredList.clear();
       if (text.length() == 0) {
           FilteredList.addAll(toDoArrayList);
       } else {
           for (ToDo wp : toDoArrayList) {
               if (wp.title.toLowerCase().contains(text)) {
                   Toast.makeText(context,"Exist",Toast.LENGTH_LONG).show();
               }
           }
       }
    //   notifyDataSetChanged();

   }
}
