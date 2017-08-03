package com.jdkgroup.radiobox;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.List;
import com.jdkgroup.recyclerviewselectsinglemultiple.R;

public class AdapterRadioBox extends RecyclerView.Adapter<AdapterRadioBox.ViewHolder> {

    private List<Subject> alSubject;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    public AdapterRadioBox(List<Subject> alSubject) {
        this.alSubject = alSubject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_radiobox, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvName.setText(alSubject.get(position).getName());
        viewHolder.tvEmailId.setText(alSubject.get(position).getEmailId());

        viewHolder.chkSelected.setChecked(alSubject.get(position).isSelected());
        viewHolder.chkSelected.setTag(new Integer(position));

        if (position == 0 && alSubject.get(0).isSelected() && viewHolder.chkSelected.isChecked()) {
            lastChecked = viewHolder.chkSelected;
            lastCheckedPos = 0;
        }

        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                int clickedPos = ((Integer) cb.getTag()).intValue();

                if (cb.isChecked()) {
                    if (lastChecked != null) {
                        lastChecked.setChecked(false);
                        alSubject.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                } else
                    lastChecked = null;
                alSubject.get(clickedPos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return alSubject.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvName, tvEmailId;
        public AppCompatCheckBox chkSelected;
        private LinearLayout llSelect;

        public ViewHolder(View view) {
            super(view);

            tvName = (AppCompatTextView) view.findViewById(R.id.tvName);
            tvEmailId = (AppCompatTextView) view.findViewById(R.id.tvEmailId);
            chkSelected = (AppCompatCheckBox) view.findViewById(R.id.chkSelected);
            llSelect = (LinearLayout) view.findViewById(R.id.llSelect);
        }
    }

    public List<Subject> getStudentist() {
        return alSubject;
    }

}