package com.example.nigel.hackathon.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nigel.hackathon.R;
import com.example.nigel.hackathon.fragments.DatesFragment;
import com.example.nigel.hackathon.fragments.FragmentNowShowing;
import com.example.nigel.hackathon.helpers.SlidingTabLayout;

import com.example.nigel.hackathon.network.VolleySingleInstance;
import com.example.nigel.hackathon.scrape.HomeScrapper;

public class MainActivity extends ActionBarActivity implements FragmentNowShowing.RequestNowShowingData {

    Toolbar toolbar;

    TextView textView;

    ViewPager viewPager;

    SlidingTabLayout slidingTabLayout;

    private MyPagerAdapter myPagerAdapter;

    public HomeScrapper homeScrapper = null;

    private static final String HOME_URL = "http://www.kenyabuzz.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        this.setTitle("Movies");

        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.my_view_pager);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(myPagerAdapter);

        slidingTabLayout.setDistributeEvenly(true);

        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.primary_app_color));

        slidingTabLayout.setViewPager(viewPager);

        RequestQueue requestQueue = VolleySingleInstance.getVolleySingleInstance().getSinglereqRestQueue();

        if (savedInstanceState == null){
           requestQueue.getCache().clear();
        }

    }

    public void getData(RequestQueue requestQueue){



        StringRequest req = new StringRequest(Request.Method.GET,
                HOME_URL+"/movies/now-showing",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        homeScrapper = new HomeScrapper(response);

                        homeScrapper.getNowShowingInfo();

                        nullTest();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(req);


        if (homeScrapper != null){
            Log.d("Home Scrapper","it works.");
        }
        else {
            Log.d("Home Scrapper","it is still null");
        }



    }

    private void nullTest(){

        Log.d("Null Test", "method invoked");

        Log.d("Home Scrapper size", ""+homeScrapper.getNowShowingInfo());

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getNowShowingdata() {

        FragmentNowShowing frag = (FragmentNowShowing) myPagerAdapter.getItem(0);

        frag.updateUI(homeScrapper.getNowShowingInfo());

    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        String tabs[];

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tab_titles);
        }

        @Override
        public Fragment getItem(int position) {

            Log.d("Getting Fragment", "At position " + position);

            if (position == 0){

                return FragmentNowShowing.newInstance("","");
            }
            else if (position == 1){

                return DatesFragment.newInstance("", "");
            }


            Log.d("Null Fragment", "Fragment adapter didn't return a fragment.");

            return null;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 2;
        }



    }





}
