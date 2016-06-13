package com.toptoche.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.toptoche.multiselectwidget.MultiSelectFragment;
import com.toptoche.multiselectwidget.MultiSelectView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MultiSelectView multiSelectView = (MultiSelectView) findViewById(R.id.m);

        List<String> stringList = new ArrayList<>();
        stringList.add(getResources().getStringArray(android.R.array.imProtocols)[1]);

        multiSelectView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(android.R.array.imProtocols)), stringList);

        multiSelectView.setDelimiter(";");

        multiSelectView.setPositiveButton("OK", new MultiSelectFragment.OnPositiveButtonClicked() {
            @Override
            public void onPositiveButtonClicked(Dialog dialog) {
                multiSelectView.getSelectedItems();
            }
        });

        multiSelectView.setNegativeButton("CANCEL", new MultiSelectFragment.OnNegativeButtonClicked() {
            @Override
            public void onNegativeButtonClicked(Dialog dialog) {

            }
        });

        multiSelectView.setOnNoItemSelectedListener(new MultiSelectFragment.OnNoItemSelected() {
            @Override
            public void onNoItemSelected(Dialog dialog) {

            }
        });
    }
}
