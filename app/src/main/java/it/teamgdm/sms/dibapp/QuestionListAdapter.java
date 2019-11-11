package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionListAdapter extends ArrayAdapter<Question> {

    //the list values in the List of type hero
    private ArrayList<Question> questionsList;

    //activity context
    Context context;

    //the layout resource file for the list items
    private int resource;

    //constructor initializing the values
    QuestionListAdapter(Context context, int resource, ArrayList<Question> questionsList) {
        super(context, resource, questionsList);
        this.context = context;
        this.resource = resource;
        this.questionsList = questionsList;
    }

    //this will return the ListView Item as a View
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater

        //getting the view
        View view = LayoutInflater.from(context).inflate(resource, null, false);

        //getting the view elements of the list from the view
        TextView rateText = view.findViewById(R.id.questionRating);
        TextView qstText = view.findViewById(R.id.questionText);

        //getting the hero of the specified position
        Question qst = questionsList.get(position);

        Log.i(Constants.TAG, "QUESTION LIST = " + questionsList.toString());
        Log.i(Constants.TAG, "QUESTION CONTENT = " + qst.toString());

        //adding values to the list item
        rateText.setText(String.valueOf(qst.rate));
        qstText.setText(qst.question);

        //finally returning the view
        return view;
    }

    //prevent the on click visual feedback when a list item is pressed
    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
