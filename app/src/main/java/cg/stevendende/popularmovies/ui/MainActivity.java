/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cg.stevendende.popularmovies.R;
import cg.stevendende.popularmovies.model.MdbMovie;

/**
 * This Activity is the main entrance of The Application.
 */
public class MainActivity extends AppCompatActivity implements MainActivityFragment.ActivityCallbackInterface {

    public static final String EXTRA_MOVIE = "cg.stevendende.extra.movie";

    private static final String TAG_FRAGMENT_MAIN = "tag.main.fragment";
    private static final String TAG_FRAGMENT_DETAILS = "tag.details.fragment";
    private String mFragmentTag = TAG_FRAGMENT_MAIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentByTag(mFragmentTag) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MainActivityFragment(), mFragmentTag)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Loads a Details Fragment when the user clicks on a List Item
     *
     * @param movie The data belonging to the view that was clicked.
     */
    @Override
    public void onItemClick(MdbMovie movie) {
        Bundle data = new Bundle();
        data.putParcelable(EXTRA_MOVIE, movie);

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(data);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailsFragment, mFragmentTag)
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .addToBackStack(null)
                .commit();

        Log.i("Activity ", "item clicked : Transition to details");
        mFragmentTag = TAG_FRAGMENT_DETAILS;
    }
}