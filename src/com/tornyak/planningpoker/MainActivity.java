package com.tornyak.planningpoker;


import android.os.Bundle;
import android.app.Activity;
import android.widget.GridView;

public class MainActivity extends Activity
{
    private CardGrid cardGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView) findViewById(R.id.cardGridView);
        cardGrid = new CardGrid(gridView, this);
    }

}
