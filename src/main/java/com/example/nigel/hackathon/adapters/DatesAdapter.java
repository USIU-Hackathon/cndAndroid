package com.example.nigel.hackathon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nigel.hackathon.R;
import com.example.nigel.hackathon.model.DatesInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by nigel on 3/14/15.
 */
public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.DatesAdapterHolder> {

    private LayoutInflater layoutInflater;

    List<DatesInfo> datesInfos = Collections.emptyList();


    public DatesAdapter(Context context){

        layoutInflater = LayoutInflater.from(context);

    }

    public void updateData( List<DatesInfo> data){

        datesInfos = data;

        notifyItemRangeChanged(0,datesInfos.size());

    }

    @Override
    public DatesAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.dates_row,parent,false);

        DatesAdapterHolder holder = new DatesAdapterHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(DatesAdapterHolder holder, int position) {

        DatesInfo current = datesInfos.get(position);

        holder.dates.setText(current.stringDate);

    }

    @Override
    public int getItemCount() {
        return datesInfos.size();
    }

    static class DatesAdapterHolder extends RecyclerView.ViewHolder{

        TextView dates;

        public DatesAdapterHolder(View itemView) {
            super(itemView);

            dates = (TextView) itemView.findViewById(R.id.dates_text);

        }
    }

}
