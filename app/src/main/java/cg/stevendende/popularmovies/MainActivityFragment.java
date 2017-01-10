/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import cg.stevendende.popularmovies.model.MdbMovieList;

/**
 * Displays the list of popular Movies in the MainActivity
 */
public class MainActivityFragment extends Fragment {

    public static final String API_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    public static final String API_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    public static String API_IMAGE_SIZE_NORMAL = "w185";
    public static String API_IMAGE_SIZE_BIG = "w300";

    private GridView mGridView;
    private MdbMovieListAdapter mListAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) rootView.findViewById(R.id.gridView);
        mGridView.setNumColumns(2);
        mGridView.setHorizontalSpacing(1);
        mGridView.setVerticalSpacing(1);

        mListAdapter = new MdbMovieListAdapter(new MdbMovieList(), getActivity());
        mGridView.setAdapter(mListAdapter);

        return rootView;
    }

}
