package com.ace.legend.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ace.legend.todo.tabs.SlidingTabLayout;
import com.software.shell.fab.ActionButton;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolBar;
    private SlidingTabLayout tabs;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter(fm));

        initializeView();
        setFab();

    }

    private void initializeView() {
        toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.custom_tab_view, R.id.tab_text);
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.white));
        tabs.setViewPager(viewPager);
    }

    private void setFab() {
        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.accentColor));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.accentColorDark));
        actionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_36dp));
        actionButton.setImageResource(R.drawable.ic_add_white_36dp);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTodo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {

            case R.id.action_settings:
                return true;

            case R.id.action_new_todo:
                Intent i = new Intent(this, AddTodo.class);
                startActivity(i);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public class MyAdapter extends FragmentPagerAdapter {

        String[] tabs;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int i) {

            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new TodoFragment();
                    return fragment;

                case 1:
                    fragment = new DoneFragment();
                    return fragment;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 2;
        }

    }

}