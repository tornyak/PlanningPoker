package com.tornyak.planningpoker;


import android.app.Activity;
import android.app.Fragment;
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
public class ShowCardActivity extends Activity
{
    public static final String LOG_TAG = "ShowCardActivity";
    
    /**
     * A handler object, used for deferring UI operations.
     */
    private Handler mHandler = new Handler();
    
    private ImageView cardImageView;
    private final int cardCoverImage = R.drawable.ic_cover;
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
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
            
        } else {
            coverSide = (getFragmentManager().getBackStackEntryCount() > 0);
        }     
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            getActionBar().hide();
    }


    public void turnCard(View v)
    {
        index = getIntent().getIntExtra("index", -1);
        if(coverSide)
        {
            if(index >= 0 && index < cardFaceImages.length)
            {
                Log.d(LOG_TAG, "turnCard() index=" + index);
                cardImageView.setImageResource(cardFaceImages[index]);
                coverSide = false;
            }
        }
        else
        {
            cardImageView.setImageResource(cardCoverImage);
            coverSide = true;
        }
    }
    
    
    public void flipCard(View v) {
        
        
        Log.d(LOG_TAG, "flipCard() index=" + index);
        
        if (coverSide) {
            getFragmentManager().popBackStack();
            coverSide = false;
            return;
        }

        // Flip to the back.
        coverSide = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.
        getFragmentManager().beginTransaction()

                // Replace the default fragment animations with animator resources representing
                // rotations when switching to the back of the card, as well as animator
                // resources representing rotations when flipping back to the front (e.g. when
                // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a fragment
                // representing the next page (indicated by the just-incremented currentPage
                // variable).
                .replace(R.id.container, new CardBackFragment())

                // Add this transaction to the back stack, allowing users to press Back
                // to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();

        // Defer an invalidation of the options menu (on modern devices, the action bar). This
        // can't be done immediately because the transaction may not yet be committed. Commits
        // are asynchronous in that they are posted to the main thread's message loop.
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
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
