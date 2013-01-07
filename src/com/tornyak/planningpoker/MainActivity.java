package com.tornyak.planningpoker;


import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.widget.GridView;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        // Get memory size in MB of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        final int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = 1024 * 1024 * memClass / 8;
        BitmapUtils.initBmpMemoryCache(cacheSize);
        
        setContentView(R.layout.activity_main);
        GridView gridView = (GridView) findViewById(R.id.cardGridView);
        new CardGrid(gridView, this);
    }
}
