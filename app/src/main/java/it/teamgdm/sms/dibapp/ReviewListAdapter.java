package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewListAdapter extends ArrayAdapter<Review> {

    //the list values in the List of type hero
    ArrayList<Review> reviewList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public ReviewListAdapter(Context context, int resource, ArrayList<Review> reviewList) {
        super(context, resource, reviewList);
        this.context = context;
        this.resource = resource;
        this.reviewList = reviewList;
    }

    //this will return the ListView Item as a View
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        RatingBar rateBar = view.findViewById(R.id.ratingBar);
        TextView titleText = view.findViewById(R.id.title);
        TextView subtitleText = view.findViewById(R.id.subtitle);

        //getting the hero of the specified position
        Review rew = reviewList.get(position);

        //adding values to the list item
        rateBar.setRating(rew.getRate());
        titleText.setText(rew.getSummary());
        subtitleText.setText(rew.getDescription());

        //finally returning the view
        return view;
    }

    //prevent the on click visual feedback when a list item is pressed
    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
