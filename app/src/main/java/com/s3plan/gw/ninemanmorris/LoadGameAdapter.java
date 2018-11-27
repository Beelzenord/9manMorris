package com.s3plan.gw.ninemanmorris;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Functionality to click on a item in the recycler view is from https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example.
 * Adapter for a RecyclerView to let the user choose to load a saved game.
 */
public class LoadGameAdapter extends RecyclerView.Adapter<LoadGameAdapter.MyViewHolder> {
    private Activity activity;
    private List<String> mDataset;
    private ItemClickListener mClickListener;
    public LoadGameAdapter(Activity Activity, List<String> myDataset) {
        this.activity = Activity;
        this.mDataset = myDataset;
    }

    /**
     * Returns the name of a saved game selected at position i.
     * @param i The position selected.
     * @return The name at position i.
     */
    public String getName(int i) {
        return mDataset.get(i);
    }

    /**
     * When created, finds the view item to be used in each item.
     * @param viewGroup The view group of a certain layout.
     * @param i The position.
     * @return The viewholder created from the view items.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.save_file_name, viewGroup, false);

        MyViewHolder vh = new MyViewHolder(v);
        vh.saveName = v.findViewById(R.id.saveName);

        return vh;
    }

    /**
     * Sets new values for the viewholder when a user scrolls.
     * @param myViewHolder The viewholder to be updated.
     * @param i The position of the list.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        try {
            String s = mDataset.get(i);
            myViewHolder.saveName.setText(s);

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the listener to be used when a user clicks an item.
     * @param itemClickListener The listener to be used.
     */
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * Interface so another class can be used to to get the information from a click.
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * The number of items in the list.
     * @return The number of items in the list.
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * The view holder from every item in the list.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView saveName;
        public MyViewHolder(View v) {
            super(v);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
