package com.jdkgroup.recyclerviewselectsinglemultiple;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jdkgroup.multiple.ActivityMultipleSelect;
import com.jdkgroup.multipledelete.ActivityMultipleDeleteB;
import com.jdkgroup.radiobox.ActivityRadioBox;
import com.jdkgroup.signle.ActivitySingleSelectBackground;

public class ActivityMain extends AppCompatActivity {

    private Activity activity;
    private LinearLayout llSingleSelectRadioBox, llSingleSelectBackground, llMultipleSelect, llMultipleSelectB;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        llSingleSelectRadioBox = (LinearLayout) findViewById(R.id.llSingleSelectRadioBox);
        llSingleSelectBackground = (LinearLayout) findViewById(R.id.llSingleSelectBackground);
        llMultipleSelect = (LinearLayout) findViewById(R.id.llMultipleSelect);
        llMultipleSelectB  = (LinearLayout) findViewById(R.id.llMultipleSelectB);

        llSingleSelectRadioBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, ActivityRadioBox.class));
            }
        });

        llSingleSelectBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, ActivitySingleSelectBackground.class));
            }
        });

        llMultipleSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, ActivityMultipleSelect.class));
            }
        });

        llMultipleSelectB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, ActivityMultipleDeleteB.class));
            }
        });

    }
}

