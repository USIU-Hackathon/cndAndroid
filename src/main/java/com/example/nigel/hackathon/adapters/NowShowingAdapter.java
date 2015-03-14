package com.example.nigel.hackathon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nigel.hackathon.R;
import com.example.nigel.hackathon.model.NowShowingInfo;
import com.example.nigel.hackathon.network.VolleySingleInstance;

import java.util.Collections;
import java.util.List;

/**
 * Created by nigel on 3/14/15.
 */
public class NowShowingAdapter extends RecyclerView.Adapter<NowShowingAdapter.NowShowingHolder> {

    private LayoutInflater layoutInflater;

    private static final String HOME_URL = "http://www.kenyabuzz.com";

    List<NowShowingInfo> myData = Collections.emptyList();


    public NowShowingAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateData (List<NowShowingInfo> data){

        myData = data;

        notifyItemRangeChanged(0,myData.size());

    }

    @Override
    public NowShowingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.now_showing_row, parent, false);

        NowShowingHolder holder = new NowShowingHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(final NowShowingHolder holder, int position) {

        NowShowingInfo current = myData.get(position);

        holder.movieTitleText.setText(current.movieTitle);

        holder.movieDescriptiontext.setText(current.movieDescription);

        ImageLoader imageLoader = VolleySingleInstance.getVolleySingleInstance().getSingleImageLoader();

        imageLoader.get(HOME_URL+current.movieImageLink, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.myImageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return myData.size();
    }


    class NowShowingHolder extends RecyclerView.ViewHolder{

        ImageView myImageView;
        TextView movieTitleText, movieDescriptiontext;

        public NowShowingHolder(View itemView) {
            super(itemView);

            myImageView = (ImageView) itemView.findViewById(R.id.movie_image_view);

            movieTitleText = (TextView) itemView.findViewById(R.id.movie_title);

            movieDescriptiontext = (TextView) itemView.findViewById(R.id.movie_description);


        }


    }

}
