package com.ace.legend.todo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class AddTodo extends ActionBarActivity {

    private Toolbar toolBar;
    EditText et_title, et_detail;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo);

        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_title = (EditText) findViewById(R.id.et_title);
        et_detail = (EditText) findViewById(R.id.et_detail);

        db = new DatabaseHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_cancel:
				/*Intent i = new Intent(this,MainActivity.class);
				startActivity(i);*/
                finish();
                return true;

            case R.id.action_done:
                String eTitle = et_title.getText().toString();
                String eDetail = et_detail.getText().toString();

                if (TextUtils.isEmpty(eTitle)) {
                    et_title.setError("Enter title.");
                    return false;
                }

                db.addTodo(new ToDo(eTitle,eDetail));
                Toast.makeText(this, "ToDo added", Toast.LENGTH_LONG).show();
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
