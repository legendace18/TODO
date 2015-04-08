package com.ace.legend.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;


public class DoneDetail extends ActionBarActivity {

    private Toolbar toolBar;
    TextView tv_title, tv_detail;
    String title, detail;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.done_detail);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHandler(this);

        Intent i = getIntent();
        title = i.getStringExtra("title");
        detail = i.getStringExtra("detail");

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_detail = (TextView) findViewById(R.id.tv_detail);

        tv_title.setText(title);
        tv_detail.setText(detail);

        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.accentColor));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.accentColorDark));
        actionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_assignment_white_24dp));
        actionButton.setImageResource(R.drawable.ic_assignment_white_24dp);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = db.shiftDone(new ToDo(title, detail));
                if(num == 1){
                    Toast.makeText(DoneDetail.this, "ToDo not completed.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
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

            case R.id.action_add:
                Intent intent = new Intent(this, AddTodo.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}