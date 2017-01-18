/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

import cg.stevendende.popularmovies.MdbMovieRecyclerAdapter;
import cg.stevendende.popularmovies.R;
import cg.stevendende.popularmovies.ServerAsyncTask;
import cg.stevendende.popularmovies.Tools;
import cg.stevendende.popularmovies.model.MdbMovie;
import cg.stevendende.popularmovies.model.MdbMovieList;

/**
 * Displays the list of popular Movies in the MainActivity
 */
public class MainActivityFragment extends Fragment implements MdbMovieRecyclerAdapter.MovieClickInterface {

    public ActivityCallbackInterface mCallback;
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private MdbMovieRecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
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

        mLayoutManager = new GridLayoutManager(getActivity(), mNumberOfCols, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.offsetChildrenHorizontal(mHorinzontalSpacing);
        mLayoutManager.offsetChildrenVertical(mVerticalSpacing);

        mRecyclerView.setLayoutManager(mLayoutManager);

        if (mRecyclerAdapter == null) {
            mRecyclerAdapter = new MdbMovieRecyclerAdapter(new MdbMovieList(), getActivity());

            //Pass the interface to the adapter
            mRecyclerAdapter.setMovieClickInterface(this);

        } else {
            //Avoid Glide "java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity"
            mRecyclerAdapter.mContext = getActivity();
        }

        mRecyclerView.setAdapter(mRecyclerAdapter);

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
                            mRecyclerAdapter.setMoviesList(mdbMoviesList);
                            mRecyclerAdapter.notifyDataSetChanged();

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mCallback == null) {
            mCallback = (ActivityCallbackInterface) context;
        }
    }

    @Override
    public void onMovieClick(MdbMovie movie) {
        mCallback.onItemClick(movie);
        Log.i("Fragment Main ", "item clicked : Callback to Activity");
    }

    interface ActivityCallbackInterface {
        void onItemClick(MdbMovie movie);
    }
}
