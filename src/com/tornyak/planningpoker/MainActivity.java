package com.tornyak.planningpoker;


import android.os.Bundle;
import android.app.Activity;
import android.widget.GridView;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView) findViewById(R.id.cardGridView);
        new CardGrid(gridView, this);
    }

}
