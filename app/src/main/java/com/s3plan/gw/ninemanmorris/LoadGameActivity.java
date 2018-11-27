package com.s3plan.gw.ninemanmorris;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.s3plan.gw.ninemanmorris.Model.SavedGames;

import java.util.ArrayList;
import java.util.List;

public class LoadGameActivity extends AppCompatActivity implements LoadGameAdapter.ItemClickListener {
    private RecyclerView mRecyclerView;
    private LoadGameAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SavedGames savedGames;

    /**
     * Gets the weatherLocations model contains the locations to show.
     * Creates the recycler which contains the locations.
     *
     * @param savedInstanceState The saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        savedGames = SavedGames.getInstance();
        createRecycler();
    }

    /**
     * Creates the recycler which contains the locations. Creates the adapter used with the recycler.
     */
    private void createRecycler() {
        mRecyclerView = findViewById(R.id.loadgame_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> list = savedGames.getSavedGames();
        if (list != null)
            mAdapter = new LoadGameAdapter(this, list);
        else {
            mAdapter = new LoadGameAdapter(this, new ArrayList<String>());
        }
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * When a location is clicked it is picked out and sent back to the main activity.
     * This activity closes.
     *
     * @param view     The view the click came from.
     * @param position The position in the list of the item clicked.
     */
    @Override
    public void onItemClick(View view, int position) {
        Intent result = new Intent(
                LoadGameActivity.this,
                MainActivity.class);
        String s = mAdapter.getName(position);
        result.putExtra(MainActivity.SAVEDGAME_RESULT, s);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
