package com.ace.legend.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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

import com.ace.legend.todo.adapters.DoneAdapter;
import com.ace.legend.todo.tabs.SlidingTabLayout;

import java.util.ArrayList;


public class DoneFragment extends ListFragment implements SlidingTabLayout.FragmentRefreshListener {
    DatabaseHandler db;
    ArrayList<ToDo> todoList;
    private DoneAdapter adapter;
    private ListView lv;

    public DoneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_done, container, false);
        setHasOptionsMenu(true);
        db = new DatabaseHandler(getActivity());
        todoList = new ArrayList<>();
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        displayList();
        lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDo todo = todoList.get(position);
                Intent intent = new Intent(getActivity(), DoneDetail.class);
                intent.putExtra("title", todo.getTitle());
                intent.putExtra("detail", todo.getDetail());
                startActivity(intent);
            }
        });
        registerForContextMenu(lv);

    }

    public void displayList() {
        todoList = db.getDone();
        adapter = new DoneAdapter(getActivity(), todoList);
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
        switch (id) {

        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(todoList.get(info.position).title);
        menu.add(0,1,98,"Open");
        menu.add(0,2,99,"Delete");
        menu.add(0,3,100,"Task Not Completed");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;
        final ToDo todo = todoList.get(position);
        if(getUserVisibleHint()) {
            int id = item.getItemId();
            switch (id) {
                case 1:
                    Intent i = new Intent(getActivity(), DoneDetail.class);

                    i.putExtra("title", todo.getTitle());
                    i.putExtra("detail", todo.getDetail());
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
                    int num = db.shiftDone(new ToDo(todo.getTitle(), todo.getDetail()));
                    if (num == 1) {
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