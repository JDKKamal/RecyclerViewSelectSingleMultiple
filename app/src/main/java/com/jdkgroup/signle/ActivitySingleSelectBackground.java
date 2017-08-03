package com.jdkgroup.signle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdkgroup.recyclerviewselectsinglemultiple.ActivityMain;
import com.jdkgroup.recyclerviewselectsinglemultiple.R;
import com.jdkgroup.widget.RecyclerItemClickListener;

import java.util.ArrayList;

public class ActivitySingleSelectBackground extends AppCompatActivity {

    private Activity activity;
    private Toolbar toolbar;

    private TextView tvTitle;
    private ImageView ivBack, ivSearch, ivSort, ivDoneMultile;

    private RecyclerView recyclerView;

    private ArrayList<Person> mPersonList;
    private AdapterSingleSelectBackground adapterSingleSelectBackground;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select_background);

        activity = this;

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

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, ActivityMain.class));
            }
        });

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            adapterSingleSelectBackground.setSelected(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
        setupPersonList();
    }

    private void setupPersonList() {
        mPersonList = new ArrayList<Person>();
        mPersonList.clear();
        for (int i = 0; i < 5; i++) {
            Person person = new Person("Person " + i, "Desgination " + i, "Address " + i);
            mPersonList.add(person);
        }
        adapterSingleSelectBackground = new AdapterSingleSelectBackground(mPersonList, this);
        recyclerView.setAdapter(adapterSingleSelectBackground);
    }
}
