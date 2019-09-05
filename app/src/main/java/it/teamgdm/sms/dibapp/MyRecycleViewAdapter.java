package it.teamgdm.sms.dibapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {

    private ArrayList<?> data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ViewHolder(View view) {
            super(view);
            Log.i(Settings.TAG, getClass().getSimpleName() + " -ViewHolder-Constructor");
            title = view.findViewById(R.id.recyclerViewRow);
        }
    }

    public MyRecycleViewAdapter(ArrayList data) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -MyRecycleViewAdapter-");
        this.data = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onCreateViewHolder-");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -onBindViewHolder-");
        viewHolder.title.setText(data.get(position).toString());
    }
    @Override
    public int getItemCount() {
        Log.i(Settings.TAG, getClass().getSimpleName() + " -getItemCount-"+data.size());
        return data.size();
    }
}