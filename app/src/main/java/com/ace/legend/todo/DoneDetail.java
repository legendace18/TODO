package com.ace.legend.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class DoneDetail extends ActionBarActivity {

    TextView tv_title, tv_detail;
    String title, detail;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.done_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHandler(this);

        Intent i = getIntent();
        title = i.getStringExtra("title");
        detail = i.getStringExtra("detail");

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_detail = (TextView) findViewById(R.id.tv_detail);

        tv_title.setText(title);
        tv_detail.setText(detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_discard:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete ToDo");
                builder.setMessage("Do you want delete the task?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int pos) {
                                int count = db.deleteTodo(new ToDo(title, detail));
                                if (count == 1) {
                                    Toast.makeText(DoneDetail.this, "ToDo deleted.",
                                            Toast.LENGTH_LONG).show();
                                    finish();
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
                return true;

            case R.id.action_new_todo:
                Intent intent = new Intent(this, AddTodo.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_shift:
                int num = db.shiftDone(new ToDo(title, detail));
                if(num == 1){
                    Toast.makeText(this, "ToDo not completed.", Toast.LENGTH_LONG).show();
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}