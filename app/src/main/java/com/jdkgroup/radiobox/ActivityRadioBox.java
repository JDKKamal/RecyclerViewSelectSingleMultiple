package com.jdkgroup.radiobox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdkgroup.recyclerviewselectsinglemultiple.ActivityMain;
import com.jdkgroup.recyclerviewselectsinglemultiple.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityRadioBox extends AppCompatActivity {

    private Activity activity;
    private Toolbar toolbar;

    private TextView tvTitle;
    private ImageView ivBack, ivSearch, ivSort, ivDoneMultile;

    private RecyclerView recyclerView;

    private AdapterRadioBox adapterRadioBox;
    private List<Subject> alSubject;
    private AppCompatButton btnSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiobox);

        activity = this;

        btnSelection = (AppCompatButton) findViewById(R.id.btnShow);

        alSubject = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Subject st = new Subject("Subject " + i, "jdkgroup" + i + "@gmail.com", false);
            alSubject.add(st);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        ivBack = (ImageView) toolbar.findViewById(R.id.ivBack);
        ivSearch = (ImageView) toolbar.findViewById(R.id.ivSearch);
        ivSort = (ImageView) toolbar.findViewById(R.id.ivSort);
        ivDoneMultile = (ImageView) toolbar.findViewById(R.id.ivDoneMultile);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            tvTitle.setText("Single Select RadioBox");
        }
        ivSearch.setVisibility(View.GONE);
        ivSort.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterRadioBox = new AdapterRadioBox(alSubject);
        recyclerView.setAdapter(adapterRadioBox);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, ActivityMain.class));
            }
        });

        btnSelection.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                List<Subject> stList = ((AdapterRadioBox) adapterRadioBox).getStudentist();

                for (int i = 0; i < stList.size(); i++) {
                    Subject singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {

                        data = data + "\n" + singleStudent.getName().toString();
                        //Toast.makeText(ActivityCheckBox.this, " " + singleStudent.getName() + " " + singleStudent.getEmailId() + " " + singleStudent.isSelected(), Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(ActivityRadioBox.this, "Selected Item :" + data, Toast.LENGTH_LONG).show();
            }
        });

    }

}