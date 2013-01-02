package com.tornyak.planningpoker;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.tornyak.planningpoker.cards.Deck;

public class CardGrid
{
    public static final String LOG_TAG = "CardGrid";
    private final int[] cardImages = {R.drawable.ic_zero,
                                      R.drawable.ic_half,
                                      R.drawable.ic_one,
                                      R.drawable.ic_two,
                                      R.drawable.ic_three,
                                      R.drawable.ic_five,
                                      R.drawable.ic_eight,
                                      R.drawable.ic_thirteen,
                                      R.drawable.ic_twenty,
                                      R.drawable.ic_forty,
                                      R.drawable.ic_hundred,
                                      R.drawable.ic_unknown,
                                      R.drawable.ic_break}; 
    
    private Context context;
    private CardGridAdapter adapter;
    private Deck deck;
    private float pictureWidth;
    
    

    public CardGrid(GridView gridView, Context context)
    {
        this.context = context;
        deck = new Deck();
        adapter = new CardGridAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new CardGridItemClickListener());
        pictureWidth = context.getResources().getDimension(R.dimen.grid_card_picture_width);
        
        Log.d(LOG_TAG, "Constructor: pictureWidth = " + pictureWidth);
    }
    
    private class CardGridItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(LOG_TAG, "CardGridItemClickListener.onItemClick: position: " + position);
            Intent in = new Intent(context, ShowCardActivity.class);
            in.putExtra("index", position);
            context.startActivity(in);
        }

    }



    private class CardGridAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return deck.getCardCount();
        }

        @Override
        public Object getItem(int position)
        {
            return deck.getCard(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams((int)pictureWidth, (int)pictureWidth));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                imageView = (ImageView) convertView;
            }
            
            
            imageView.setImageResource(cardImages[position]);
            
            return imageView;
        }

    }

}
