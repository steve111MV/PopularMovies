/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;

import cg.stevendende.popularmovies.MdbMovieListAdapter;
import cg.stevendende.popularmovies.R;
import cg.stevendende.popularmovies.ServerAsyncTask;
import cg.stevendende.popularmovies.Tools;
import cg.stevendende.popularmovies.model.MdbMovieList;

/**
 * Displays the list of popular Movies in the MainActivity
 */
public class MainActivityFragment extends Fragment {

    private GridView mGridView;
    private MdbMovieListAdapter mListAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) rootView.findViewById(R.id.gridView);
        int mVerticalSpacing = 1;
        int mHorinzontalSpacing = 1;
        int mNumberOfCols = 2;

        if (Tools.isTabletScreen(getActivity())) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mNumberOfCols = 3;
            } else {
                mNumberOfCols = 4;
            }
            mVerticalSpacing = 2;
            mHorinzontalSpacing = 2;
        } else {
            /** */
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mNumberOfCols = 2;
            } else {
                mNumberOfCols = 3;
            }
            mVerticalSpacing = 2;
            mHorinzontalSpacing = 2;
        }

        mGridView.setNumColumns(mNumberOfCols);
        mGridView.setVerticalSpacing(mVerticalSpacing);
        mGridView.setHorizontalSpacing(mHorinzontalSpacing);

        mListAdapter = new MdbMovieListAdapter(new MdbMovieList(), getActivity());
        mGridView.setAdapter(mListAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            if (Tools.isInternetAvailaible(getActivity())) {
                refreshData();
            } else {
                Toast.makeText(getActivity(), "No internet access, check your internet settings", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "No internet access, check your internet settings", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Requests the movie list(in JSON format) to themoviedb.org API and populates the ListView (GridView)
     */
    private void refreshData() {

        String mSortBy = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popular));

        new ServerAsyncTask() {
            @Override
            protected void onPostExecute(MdbMovieList mdbMoviesList) {

                if (mdbMoviesList != null) {
                    mListAdapter.setMoviesList(mdbMoviesList);
                    mListAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "Connetion error, try again !", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(mSortBy);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings){

            startActivity(new Intent(getActivity(), SettingsActivity.class));
        }

        return true;
    }
}
