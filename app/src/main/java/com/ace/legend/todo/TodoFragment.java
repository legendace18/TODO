package com.ace.legend.todo;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ace.legend.todo.adapters.TodoAdapter;
import com.ace.legend.todo.tabs.SlidingTabLayout;

import java.util.ArrayList;


public class TodoFragment extends Fragment implements SlidingTabLayout.FragmentRefreshListener{
    DatabaseHandler db;
    ArrayList<ToDo> todoList;
    TodoAdapter adapter;
    private ListView lv;

    public TodoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_todo, container, false);
        setHasOptionsMenu(true);

        db = new DatabaseHandler(getActivity());
        todoList = new ArrayList<>();
        lv = (ListView) layout.findViewById(R.id.lv_todo);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        displayList();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDo todo = todoList.get(position);

                Intent intent = new Intent(getActivity(),TodoDetail.class);
                intent.putExtra("title", todo.getTitle());
                intent.putExtra("detail", todo.getDetail());
                intent.putExtra("time", todo.getTime());
                intent.putExtra("date", todo.getDate());
                intent.putExtra("RequestCode", position);
                startActivity(intent);
            }
        });
        registerForContextMenu(lv);
    }

    public void displayList() {
        todoList = db.getTodo();
        adapter = new TodoAdapter(getActivity(), todoList);
        lv.setAdapter(adapter);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(todoList.get(info.position).title);
        menu.add(0, 1, 98, "Open");
        menu.add(0, 2, 99, "Delete");
        menu.add(0, 3, 100, "Task Completed");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        final AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        final PendingIntent pi = PendingIntent.getBroadcast(getActivity(), position, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if(getUserVisibleHint()) {
            final ToDo todo = todoList.get(position);
            int id = item.getItemId();
            switch (id) {
                case 1:
                    Intent i = new Intent(getActivity(), TodoDetail.class);

                    i.putExtra("title", todo.getTitle());
                    i.putExtra("detail", todo.getDetail());
                    i.putExtra("time", todo.getTime());
                    i.putExtra("date", todo.getDate());
                    i.putExtra("RequestCode", position);
                    startActivity(i);
                    break;
                case 2:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Delete ToDo");
                    builder.setMessage("Do you want to delete the task?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int pos) {

                                    int count = db.deleteTodo(new ToDo(todo.getTitle(), todo.getDetail()));
                                    if (count == 1) {
                                        alarmManager.cancel(pi);
                                        Toast.makeText(getActivity(), "ToDo deleted.",
                                                Toast.LENGTH_LONG).show();
                                        todoList.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }

                                }
                            });
                    builder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
                case 3:
                    int num = db.shiftTodo(new ToDo(todo.getTitle(), todo.getDetail()));
                    if (num == 1) {
                        alarmManager.cancel(pi);
                        Toast.makeText(getActivity(), "ToDo completed.", Toast.LENGTH_LONG)
                                .show();
                        todoList.remove(position);
                        adapter.notifyDataSetChanged();

                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public void refreshFragment() {
        todoList.clear();
        displayList();
    }
}