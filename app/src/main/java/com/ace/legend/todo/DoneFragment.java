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


public class DoneFragment extends ListFragment implements MainActivity.FragmentRefreshListener{
    DatabaseHandler db;
    ArrayList<ToDo> todoList;
    CustomAdapter adapter;
    ListView lv;
    String title, detail;

    public DoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        db = new DatabaseHandler(getActivity());
        todoList = new ArrayList<ToDo>();


        return inflater.inflate(R.layout.fragment_done, container, false);
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
                Log.d("legend.ace", "detail" + detail);

                Intent i = new Intent(getActivity(), DoneDetail.class);
                i.putExtra("title", title);
                i.putExtra("detail", detail);
                startActivity(i);
            }

        });
    }

    public void displayList() {
        todoList = db.getDone();
        adapter = new CustomAdapter(getActivity(), todoList);
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
        inflater.inflate(R.menu.done_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                row = inflater.inflate(R.layout.done_list_item, parent, false);
            }

            TextView tv_title = (TextView) row.findViewById(R.id.tv_title);
            Button btn_shift = (Button) row.findViewById(R.id.btn_shift);

            ToDo todo = todoList.get(position);
            title = todo.getTitle();
            detail = todo.getDetail();
            tv_title.setText(todo.title);

            btn_shift.setTag(position);
            btn_shift.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    ToDo todo = todoList.get(position);
                    title = todo.getTitle();
                    detail = todo.getDetail();
                    int count = db.shiftDone(new ToDo(title, detail));
                    if (count == 1) {
                        Toast.makeText(getActivity(), "ToDo not completed",
                                Toast.LENGTH_SHORT).show();
                        onResume();
                    }

                }
            });

            return row;
        }

    }

}