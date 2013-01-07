package com.tornyak.planningpoker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ShowCardActivityV4 extends FragmentActivity
{
    public static final String LOG_TAG = "ShowCardActivityV4";
    
    private static final int[] cardFaceImages = {R.drawable.ic_zero_big,
                                    R.drawable.ic_half_big,
                                    R.drawable.ic_one_big,
                                    R.drawable.ic_two_big,
                                    R.drawable.ic_three_big,
                                    R.drawable.ic_five_big,
                                    R.drawable.ic_eight_big,
                                    R.drawable.ic_thirteen_big,
                                    R.drawable.ic_twenty_big,
                                    R.drawable.ic_forty_big,
                                    R.drawable.ic_hundred_big,
                                    R.drawable.ic_unknown_big,
                                    R.drawable.ic_break_big};
    private static int index;
    private boolean coverSide = false;
    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);

        if (savedInstanceState == null) {
            index = getIntent().getIntExtra("index", -1);
            // If there is no saved instance state, add a fragment representing the
            // front of the card to this activity. If there is saved instance state,
            // this fragment will have already been added to the activity.
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
            
        } else {
            coverSide = (getSupportFragmentManager().getBackStackEntryCount() > 0);
        }
    }
    
    
    public void flipCard(View v) {
        
        
        Log.d(LOG_TAG, "flipCard() index=" + index);
        
        if (coverSide) {
            getSupportFragmentManager().popBackStack();
            coverSide = false;
            return;
        }

        // Flip to the back.
        coverSide = true;
        

            
         // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.
        getSupportFragmentManager().beginTransaction()

                // Replace the default fragment animations with animator resources representing
                // rotations when switching to the back of the card, as well as animator
                // resources representing rotations when flipping back to the front (e.g. when
                // the system Back button is pressed).

                // Replace any fragments currently in the container view with a fragment
                // representing the next page (indicated by the just-incremented currentPage
                // variable).
                .replace(R.id.container, new CardBackFragment())

                // Add this transaction to the back stack, allowing users to press Back
                // to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }
    
    /**
     * A fragment representing the front of the card.
     */
    public static class CardFrontFragment extends Fragment {
        public CardFrontFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_front, container, false);
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public static class CardBackFragment extends Fragment {
        public CardBackFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            ImageView v = (ImageView)inflater.inflate(R.layout.fragment_card_back, container, false);
            v.setImageResource(cardFaceImages[index]);
            return v;
        }
    }
    
        
}

