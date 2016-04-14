package com.toptoche.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.toptoche.multiselectwidget.MultiSelectView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiSelectView multiSelectView = (MultiSelectView) findViewById(R.id.m);

        List<String> stringList = new ArrayList<>();
        stringList.add(getResources().getStringArray(android.R.array.imProtocols)[1]);

        multiSelectView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(android.R.array.imProtocols)), new ArrayList());
    }
}
