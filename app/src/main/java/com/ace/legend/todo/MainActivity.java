package com.ace.legend.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    ActionBar actionBar;
    ViewPager viewPager;

    public interface FragmentRefreshListener{
        void refreshFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter(fm));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {
                // TODO Auto-generated method stub
                actionBar.setSelectedNavigationItem(pos);

                MyAdapter adapter = (MyAdapter) viewPager.getAdapter();
                FragmentRefreshListener frag = (FragmentRefreshListener) adapter.instantiateItem(viewPager, pos);
                frag.refreshFragment();
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int pos) {
                // TODO Auto-generated method stub
                if (pos == ViewPager.SCROLL_STATE_IDLE) {
                    Log.d("Legend.ace18", "scroll state idle");
                }
                if (pos == ViewPager.SCROLL_STATE_DRAGGING) {
                    Log.d("Legend.ace18", "scroll state dragging");
                }
                if (pos == ViewPager.SCROLL_STATE_SETTLING) {
                    Log.d("Legend.ace18", "scroll state settling");
                }
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("ToDo");
        tab1.setTabListener(this);
        actionBar.addTab(tab1);

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("Done");
        tab2.setTabListener(this);
        actionBar.addTab(tab2);


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


    @Override
    public void onTabReselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        Log.d("legend.ace18", "" + tab.getPosition());
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
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
        public int getCount() {
            // TODO Auto-generated method stub
            return 2;
        }

    }

}