package com.ace.legend.todo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ace.legend.todo.DatabaseHandler;
import com.ace.legend.todo.R;
import com.ace.legend.todo.ToDo;

import java.util.ArrayList;

/**
 * Created by rohan on 4/5/15.
 */
public class TodoAdapter extends ArrayAdapter<ToDo> {

    ArrayList<ToDo> todoList;
    private DatabaseHandler db;
    private Context context;

    public TodoAdapter(Context context, ArrayList<ToDo> list) {
        super(context, R.layout.todo_list_item, list);
        db = new DatabaseHandler(context);
        this.todoList = list;
        this.context = context;
    }

    public void delete(int pos) {
        todoList.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.todo_list_item, parent, false);
        }
        TextView tv_title = (TextView) row.findViewById(R.id.tv_title);
        TextView tv_time = (TextView) row.findViewById(R.id.tv_time);
        Button btn_shift = (Button) row.findViewById(R.id.btn_shift);

        ToDo todo = todoList.get(position);
        String title = todo.getTitle();
        String detail = todo.getDetail();
        String time = todo.getTime();
        String date = todo.getDate();

        tv_title.setText(title);
        if (date != null && time != null)
            tv_time.setText(date + " " + time);
        else
            tv_time.setText("No Remainder");
        btn_shift.setTag(position);

        btn_shift.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int position = (int) v.getTag();
                ToDo todo = todoList.get(position);
                String title = todo.getTitle();
                String detail = todo.getDetail();

                int count = db.shiftTodo(new ToDo(title, detail));
                if (count == 1) {
                    Toast.makeText(context, "ToDo completed",
                            Toast.LENGTH_SHORT).show();
                    delete(position);
                }


            }
        });

        return row;
    }
}
