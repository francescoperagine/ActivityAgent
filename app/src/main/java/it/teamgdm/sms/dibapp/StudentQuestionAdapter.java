package it.teamgdm.sms.dibapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentQuestionAdapter extends ArrayAdapter<Question> {

    private Context context;
    private int layoutResourceId;
    ArrayList<Question> data;
    //used to check if the change of state of a checkbox is asked by the click of the user or by the click on the other checkbox
    boolean chanceCheckFlag = false;

    StudentQuestionAdapter(Context context, int layoutResourceId, ArrayList<Question> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    //this will return the ListView Item as a View
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(layoutResourceId, null, false);

        //getting the view elements of the list from the view
        TextView textQuestion  = view.findViewById(R.id.questiontext);
        TextView rate = view.findViewById(R.id.questionRating);

        CheckBox btnRateBad = view.findViewById(R.id.questionDislikeButton);
        CheckBox btnRateGood = view.findViewById(R.id.questionLikeButton);

        //getting the hero of the specified position
        Question question = data.get(position);

        //adding values to the list item
        textQuestion.setText(question.getQuestion());
        rate.setText(String.valueOf(question.getRate()));;

        int flag = DAO.isQuestionRated( Session.getUserID(),question.getId() );

        if(flag ==-1){
            btnRateGood.setChecked(false);
            btnRateBad.setChecked(true);

        }
        else if (flag == 1) {
                btnRateGood.setChecked(true);
                btnRateBad.setChecked(false);
        } else {
                btnRateGood.setChecked(false);
                btnRateBad.setChecked(false);
        }

        //listeners on buttons

        btnRateGood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            int newRate;

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){

                    newRate = Integer.parseInt(rate.getText().toString()) + 1;
                    if(btnRateBad.isChecked()){
                        newRate++;
                        chanceCheckFlag = true;
                        btnRateBad.setChecked(false);
                    }

                    DAO.deleteQuestionRate(Session.getUserID(), question.getId());

                    DAO.setQuestionRate(Session.getUserID(), question.getId(), Constants.RATE_GOOD);

                    rate.setText(String.valueOf(newRate));


                }
                else {
                    if(chanceCheckFlag){
                        chanceCheckFlag = false;
                    }
                    else{
                        newRate = Integer.parseInt(rate.getText().toString()) - 1;

                        DAO.deleteQuestionRate(Session.getUserID(), question.getId());

                        rate.setText(String.valueOf(newRate));
                    }

                }

            }
        }
        );

        btnRateBad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            int newRate;

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){

                    newRate = Integer.parseInt(rate.getText().toString()) - 1;
                    if(btnRateGood.isChecked()){
                        newRate--;
                        chanceCheckFlag = true;
                        btnRateGood.setChecked(false);
                    }

                    DAO.deleteQuestionRate(Session.getUserID(), question.getId());

                    DAO.setQuestionRate(Session.getUserID(), question.getId(), Constants.RATE_BAD);

                    rate.setText(String.valueOf(newRate));
                }
                else{
                    if(chanceCheckFlag){
                        chanceCheckFlag = false;
                    }
                    else{
                        newRate = Integer.parseInt(rate.getText().toString()) + 1;

                        DAO.deleteQuestionRate(Session.getUserID(), question.getId());

                        rate.setText(String.valueOf(newRate));
                    }

                }

            }
        }
        );

        //finally returning the view
        return view;
    }

}
