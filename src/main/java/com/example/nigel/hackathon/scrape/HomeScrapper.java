package com.example.nigel.hackathon.scrape;

import android.util.Log;

import com.example.nigel.hackathon.model.DatesInfo;
import com.example.nigel.hackathon.model.NowShowingInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nigel on 3/14/15.
 */
public class HomeScrapper {

    private Document doc;

    public HomeScrapper(String response){

        doc = Jsoup.parse(response);

    }

    public List<NowShowingInfo> getNowShowingInfo(){

        List<NowShowingInfo> myList = new ArrayList<>();

        Elements moviesElements = doc.select("div.movie.listing");

        Log.d("Number of movies found", ""+moviesElements.size());

        for (Element movieElement : moviesElements){

            NowShowingInfo current = new NowShowingInfo();

            current.movieTitle = movieElement.select("a.stackContent").text();

            if (current.movieTitle == null){
                Log.d("Null movie title", "Movie title not found.");
            }

            Log.d("Movie Title", ""+current.movieTitle);

            current.movieDescription = movieElement.select("div.listing-content.stackContent").text();

            if (current.movieDescription == null){
                Log.d("Null movie description", "No movie description found.");
            }

            Log.d("Movie Description", ""+current.movieDescription);

            myList.add(current);

            current.movieImageLink = movieElement.select("img").attr("src").toString();

            Log.d("Movie Image Link", ""+current.movieImageLink);

        }



        return myList;

    }


    public List<DatesInfo> getDatesInfo(){

        Log.d("getDatesInfo", "method invoked");

        List<DatesInfo> myData = new ArrayList<>();

        Element element = doc.select("div.stackAuto").first().nextElementSibling();

        Elements elements = element.select("li");

        for (Element liElement : elements){

            DatesInfo mDatesInfo = new DatesInfo();

            mDatesInfo.stringDate = liElement.select("a").text();

            if (mDatesInfo.stringDate != null){

                Log.d("date", mDatesInfo.stringDate);

                myData.add(mDatesInfo);

            }
            else{

                Log.d("dates", "returned null");

            }

        }


        Log.d("Dates info size",""+myData.size());

        return myData;

    }

}
