package com.jdkgroup.signle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdkgroup.recyclerviewselectsinglemultiple.R;

import java.util.ArrayList;

public class AdapterSingleSelectBackground extends RecyclerView.Adapter<AdapterSingleSelectBackground.ViewHolder> {
    private ArrayList<Person> mPersonList;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView apptvName, apptvAdd, apptvDsg;
        RelativeLayout list_row;

        public ViewHolder(View v) {
            super(v);
            apptvName = (AppCompatTextView) v.findViewById(R.id.apptvName);
            apptvAdd = (AppCompatTextView) v.findViewById(R.id.apptvAdd);
            apptvDsg = (AppCompatTextView) v.findViewById(R.id.apptvDsg);
            list_row = (RelativeLayout) v.findViewById(R.id.list_row);
        }
    }

    public void add(int position, Person item) {
        mPersonList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mPersonList.indexOf(item);
        mPersonList.remove(position);
        notifyItemRemoved(position);
    }

    public AdapterSingleSelectBackground(ArrayList<Person> personList, Context context) {
        mPersonList = personList;
        mPref = context.getSharedPreferences("person", Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_single_select_background, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.apptvName.setText(mPersonList.get(position).getName());
        holder.apptvAdd.setText(mPersonList.get(position).getAdd());
        holder.apptvDsg.setText(mPersonList.get(position).getDsg());

        if (mPersonList.get(position).isSelected()) {
            holder.list_row.setBackgroundColor(Color.parseColor("#DFC4B4"));
        } else {
            holder.list_row.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void setSelected(int pos) {
        try {
            if (mPersonList.size() > 1) {
                mPersonList.get(mPref.getInt("position", 0)).setSelected(false);
                mEditor.putInt("position", pos);
                mEditor.commit();
            }
            mPersonList.get(pos).setSelected(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }
}
