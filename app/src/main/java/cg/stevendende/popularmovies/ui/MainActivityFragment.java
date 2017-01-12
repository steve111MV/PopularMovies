/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;

import cg.stevendende.popularmovies.MdbMovieListAdapter;
import cg.stevendende.popularmovies.R;
import cg.stevendende.popularmovies.ServerAsyncTask;
import cg.stevendende.popularmovies.Tools;
import cg.stevendende.popularmovies.model.MdbMovie;
import cg.stevendende.popularmovies.model.MdbMovieList;

/**
 * Displays the list of popular Movies in the MainActivity
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView mGridView;
    private Toolbar toolbar;
    private MdbMovieListAdapter mListAdapter;

    private CallbackInterface mCallback;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);

        mGridView = (GridView) rootView.findViewById(R.id.gridView);

        int mNumberOfCols = 2;

        //Dyna
        if (Tools.isTabletScreen(getActivity())) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mNumberOfCols = 3;
            } else {
                mNumberOfCols = 4;
            }
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mNumberOfCols = 2;
            } else {
                mNumberOfCols = 3;
            }
        }

        mGridView.setNumColumns(mNumberOfCols);
        if (mListAdapter == null) {
            mListAdapter = new MdbMovieListAdapter(new MdbMovieList(), getActivity());
        } else {
            //Avoid Glide "java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity"
            mListAdapter.setContext(getActivity());
        }

        mGridView.setAdapter(mListAdapter);

        mGridView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        refreshData();
        Log.i("fragment life cycle", "onStart()");
    }

    /**
     * Requests the movie list(in JSON format) to themoviedb.org API and populates the ListView (GridView)
     */
    private void refreshData() {

        try {
            if (Tools.isInternetAvailaible(getActivity())) {
                String mSortBy = PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popular));

                new ServerAsyncTask() {
                    @Override
                    protected void onPostExecute(MdbMovieList mdbMoviesList) {

                        if (mdbMoviesList != null) {
                            mListAdapter.setMoviesList(mdbMoviesList);
                            mListAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), "Connetion error, try again !", Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute(mSortBy);
            } else {
                Toast.makeText(getActivity(), "No internet access, check your internet settings", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "No internet access, check your internet settings", Toast.LENGTH_SHORT).show();
        }
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
        } else if (id == R.id.action_refresh) {
            refreshData();
        }

        return true;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCallback != null) {
            mCallback.onMovieItemClieckListener((MdbMovie) mListAdapter.getItem(position));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCallback = (CallbackInterface) activity;
    }

    public interface CallbackInterface {
        void onMovieItemClieckListener(MdbMovie movie);
    }
}
