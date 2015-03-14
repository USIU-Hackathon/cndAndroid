package com.example.nigel.hackathon.fragments;


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
import com.example.nigel.hackathon.adapters.DatesAdapter;
import com.example.nigel.hackathon.helpers.DividerItemDecoration;
import com.example.nigel.hackathon.model.DatesInfo;
import com.example.nigel.hackathon.network.VolleySingleInstance;
import com.example.nigel.hackathon.scrape.HomeScrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String HOME_URL = "http://www.kenyabuzz.com";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;

    HomeScrapper homeScrapper;

    List<DatesInfo> thisData = new ArrayList<>();

    DatesAdapter datesAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatesFragment newInstance(String param1, String param2) {
        DatesFragment fragment = new DatesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DatesFragment() {
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

        View view = inflater.inflate(R.layout.fragment_dates, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.dates_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        datesAdapter = new DatesAdapter(getActivity());

        recyclerView.setAdapter(datesAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),null));

        return view;
    }


    public List<DatesInfo> getData(){

        Log.d("getData Dates", "method invoked.");

        RequestQueue rq = VolleySingleInstance.getVolleySingleInstance().getSinglereqRestQueue();

        StringRequest sq = new StringRequest(Request.Method.GET,
                HOME_URL+"/movies/now-showing",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        homeScrapper = new HomeScrapper(response);
                        thisData = homeScrapper.getDatesInfo();
                        datesAdapter.updateData(thisData);
                    }
                },
                new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(), "Volley Error", Toast.LENGTH_LONG);

                }
        });

        rq.add(sq);

        return thisData;

    }

    @Override
    public void onResume() {
        super.onResume();

        getData();

    }
}
