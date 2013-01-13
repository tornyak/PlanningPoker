package com.tornyak.planningpoker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("NewApi")
public class ShowCardActivity extends Activity implements SensorEventListener
{
    public static final String LOG_TAG = "ShowCardActivity";

    /**
     * A handler object, used for deferring UI operations.
     */
    private Handler mHandler = new Handler();

    private static final int[] cardBackImages = { R.drawable.ic_zero_big,
            R.drawable.ic_half_big, R.drawable.ic_one_big,
            R.drawable.ic_two_big, R.drawable.ic_three_big,
            R.drawable.ic_five_big, R.drawable.ic_eight_big,
            R.drawable.ic_thirteen_big, R.drawable.ic_twenty_big,
            R.drawable.ic_forty_big, R.drawable.ic_hundred_big,
            R.drawable.ic_unknown_big, R.drawable.ic_break_big };
    
    private static int index;
    private boolean cardBackSide = false;
    private SensorManager sensorManager;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);

        if (savedInstanceState == null)
        {
            index = getIntent().getIntExtra("index", -1);
            // If there is no saved instance state, add a fragment representing
            // the
            // front of the card to this activity. If there is saved instance
            // state,
            // this fragment will have already been added to the activity.
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new CardFrontFragment()).commit();

        }
        else
        {
            cardBackSide = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            getActionBar().hide();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void flipCard(View v)
    {

        Log.d(LOG_TAG, "flipCard() index=" + index);

        if (cardBackSide)
        {
            getFragmentManager().popBackStack();
            cardBackSide = false;
            return;
        }

        // Flip to the back.
        cardBackSide = true;

        // Create and commit a new fragment transaction that adds the fragment
        // for the back of
        // the card, uses custom animations, and is part of the fragment
        // manager's back stack.
        getFragmentManager().beginTransaction()

        // Replace the default fragment animations with animator resources
        // representing
        // rotations when switching to the back of the card, as well as animator
        // resources representing rotations when flipping back to the front
        // (e.g. when
        // the system Back button is pressed).
                .setCustomAnimations(R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a
                // fragment
                // representing the next page (indicated by the just-incremented
                // currentPage
                // variable).
                .replace(R.id.container, new CardBackFragment())

                // Add this transaction to the back stack, allowing users to
                // press Back
                // to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();

        // Defer an invalidation of the options menu (on modern devices, the
        // action bar). This
        // can't be done immediately because the transaction may not yet be
        // committed. Commits
        // are asynchronous in that they are posted to the main thread's message
        // loop.
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                invalidateOptionsMenu();
            }
        });
    }

    /**
     * A fragment representing the front of the card.
     */
    public static class CardFrontFragment extends Fragment
    {
        public CardFrontFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.fragment_card_front, container,
                    false);
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public static class CardBackFragment extends Fragment
    {
        public CardBackFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState)
        {
            ImageView v = (ImageView) inflater.inflate(
                    R.layout.fragment_card_back, container, false);
            v.setImageResource(cardBackImages[index]);
            return v;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event)
    {
        if(cardBackSide)
            return;
            
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 4) //
        {
            if (actualTime - lastUpdate < 300)
            {
                return;
            }
            lastUpdate = actualTime;

            // behave as if card was toched
            flipCard(null);
        }
    }

}
