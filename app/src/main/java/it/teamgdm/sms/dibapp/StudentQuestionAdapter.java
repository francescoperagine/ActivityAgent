package it.teamgdm.sms.dibapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StudentQuestionAdapter extends ArrayAdapter<Question> {

    Context context;
    int layoutResourceId;
    ArrayList<Question> data = new ArrayList<Question>();

    public StudentQuestionAdapter(Context context, int layoutResourceId, ArrayList<Question> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        questionHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new questionHolder();
            holder.textQuestion  = (TextView) row.findViewById(R.id.textView);
            holder.rate = (TextView) row.findViewById(R.id.textView2);
            holder.btnRateBad = (Button) row.findViewById(R.id.button2);
            holder.btnRateGood = (Button) row.findViewById(R.id.button3);
            row.setTag(holder);
        } else {
            holder = (questionHolder) row.getTag();
        }
        Question question = data.get(position);
        holder.textQuestion.setText(question.getQuestion());
        holder.rate.setText(question.getRate());
        holder.btnRateBad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(context, "-1 button Clicked",
                        Toast.LENGTH_LONG).show();
            }
        });
        holder.btnRateGood.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "+1 button Clicked",
                        Toast.LENGTH_LONG).show();
            }
        });
        return row;

    }

    static class questionHolder {
        TextView textQuestion;
        TextView rate;
        Button btnRateGood;
        Button btnRateBad;
    }
}
