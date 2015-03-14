package com.example.nigel.hackathon.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nigel.hackathon.R;
import com.example.nigel.hackathon.adapters.NowShowingAdapter;
import com.example.nigel.hackathon.model.NowShowingInfo;
import com.example.nigel.hackathon.network.VolleySingleInstance;
import com.example.nigel.hackathon.scrape.HomeScrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNowShowing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNowShowing extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String HOME_URL = "http://www.kenyabuzz.com";

    HomeScrapper homeScrapper;

    List<NowShowingInfo> thisData = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context;

    RecyclerView myRecyclerView;

    RequestNowShowingData myCallback;

    NowShowingAdapter nowShowingAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNowShowing.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNowShowing newInstance(String param1, String param2) {
        FragmentNowShowing fragment = new FragmentNowShowing();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentNowShowing() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_now_showing, container, false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.r_v);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        nowShowingAdapter = new NowShowingAdapter(getActivity());

        myRecyclerView.setAdapter(nowShowingAdapter);

        return view;

    }

    public List<NowShowingInfo> getData(){

        Log.d("getData", "method invoked.");

        RequestQueue rq = VolleySingleInstance.getVolleySingleInstance().getSinglereqRestQueue();

        StringRequest sq = new StringRequest(Request.Method.GET,
                HOME_URL+"/movies/now-showing",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        homeScrapper = new HomeScrapper(response);
                        thisData = homeScrapper.getNowShowingInfo();
                        nowShowingAdapter.updateData(thisData);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "Volley Error", Toast.LENGTH_LONG);

                    }
                });

        rq.add(sq);

        nowShowingAdapter.updateData(thisData);


        return thisData;

    }

    public interface RequestNowShowingData{
        public void getNowShowingdata();
    }

    public void updateUI(List<NowShowingInfo> data){

        myRecyclerView.setAdapter(new NowShowingAdapter(getActivity()));

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        try{
            myCallback = (RequestNowShowingData) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException("Activity did not implement interface.");
        }

    }

    @Override
    public void onResume() {
        super.onResume();

       getData();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
