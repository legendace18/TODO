package com.ace.legend.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class TodoFragment extends ListFragment implements MainActivity.FragmentRefreshListener{
    DatabaseHandler db;
    ArrayList<ToDo> todoList;
    CustomAdapter adapter;
    ListView lv;
    String title, detail, time, date;

    public TodoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        db = new DatabaseHandler(getActivity());
        todoList = new ArrayList<ToDo>();

        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        displayList();

        lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ToDo todo = todoList.get(position);
                String title = todo.getTitle();
                String detail = todo.getDetail();
                String date = todo.getDate();
                String time = todo.getTime();
                Log.d("legend.ace", "detail" + detail);

                Intent i = new Intent(getActivity(), TodoDetail.class);
                i.putExtra("title", title);
                i.putExtra("detail", detail);
                i.putExtra("date", date);
                i.putExtra("time", time);
                i.putExtra("RequestCode", position);
                startActivity(i);
            }

        });

    }

    public void displayList() {
        todoList = db.getTodo();
        adapter = new CustomAdapter(getActivity(), todoList);
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        todoList.clear();
        displayList();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.todo_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        switch(id){

        }
        return true;
    }

    @Override
    public void refreshFragment() {
        todoList.clear();
        displayList();
    }

    class CustomAdapter extends ArrayAdapter<ToDo> {

        Context context;
        private ArrayList<ToDo> todoList;

        public CustomAdapter(Context context, ArrayList<ToDo> todoList) {
            super(context, R.layout.todo_list_item, R.id.tv_title, todoList);
            this.context = context;
            this.todoList = todoList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.todo_list_item, parent, false);
            }

            TextView tv_title = (TextView) row.findViewById(R.id.tv_title);
            TextView tv_time = (TextView) row.findViewById(R.id.tv_time);
            Button btn_shift = (Button) row.findViewById(R.id.btn_shift);

            ToDo todo = todoList.get(position);
            title = todo.getTitle();
            detail = todo.getDetail();
            time = todo.getTime();
            date = todo.getDate();

            tv_title.setText(title);
            if (date != null && time != null)
                tv_time.setText(date + " " + time);
            else
                tv_time.setText("No Remainder");
            btn_shift.setTag(position);

            btn_shift.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
					/*
					 * View parentRow = (View) v.getParent(); ListView lv =
					 * (ListView) parentRow.getParent(); final int position =
					 * lv.getPositionForView(parentRow);
					 */
                    int position = (int) v.getTag();
                    ToDo todo = todoList.get(position);
                    title = todo.getTitle();
                    detail = todo.getDetail();

                    int count = db.shiftTodo(new ToDo(title, detail));
                    if (count == 1) {
                        Toast.makeText(getActivity(), "ToDo completed",
                                Toast.LENGTH_SHORT).show();
                        onResume();
                    }


                }
            });

            return row;
        }
    }

}