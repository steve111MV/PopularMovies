/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cg.stevendende.popularmovies.model.MdbMovie;
import cg.stevendende.popularmovies.model.MdbMovieList;

/**
 * Contains methods used to parse the JSON String of Popular Movies list from themoviedb.org API
 */
public class MDBApiParser {

    public static final String TAG_RESULAT = "results";
    public static final String TAG_TOTAL_RESULAT = "total_results";
    public static final String TAG_PAGE = "page";
    public static final String TAG_TOTAL_PAGE = "total_page";

    public static final String TAG_RESULT_IMAGE = "poster_path";
    public static final String TAG_RESULT_IMAGE_BACK = "backdrop_path";
    public static final String TAG_RESULT_TITRE = "title";
    public static final String TAG_RESULT_ORIGINAL_TITRE = "original_title";
    public static final String TAG_RESULT_DESCRIPTION = "overview";
    public static final String TAG_RESULT_RELEASE = "release_date";
    public static final String TAG_RESULT_ADULT = "adult";

    public static final String TAG_RESULT_ID = "id";
    public static final String TAG_RESULT_ORIGINAL_LANG = "original_language";
    public static final String TAG_RESULT_VIDEO_ENABLED = "video";

    /**
     * Parses the JSON String from themoviedb.org API
     *
     * @param jsonObjectStr The json text from themoviedb.org API.
     * @return The list of movies
     */
    public static MdbMovieList getPopularMovies(String jsonObjectStr) throws JSONException {

        MdbMovieList mMovieList = new MdbMovieList();
        JSONObject mJsonObject = new JSONObject();

        if (jsonObjectStr == null) {
            return mMovieList;
        } else {
            mJsonObject = new JSONObject(jsonObjectStr);
        }

        MdbMovie mMovie;

        JSONArray results = mJsonObject.getJSONArray(TAG_RESULAT);
        for (int i = 0; i < results.length(); i++) {

            JSONObject object = results.getJSONObject(i);
            mMovie = new MdbMovie();
            mMovie.setTitre(object.getString(TAG_RESULT_TITRE));
            mMovie.setOriginalTitle(object.getString(TAG_RESULT_ORIGINAL_TITRE));
            mMovie.setImageUrl(object.getString(TAG_RESULT_IMAGE));
            mMovie.setPosterUrl(object.getString(TAG_RESULT_IMAGE_BACK));
            mMovie.setDecription(object.getString(TAG_RESULT_DESCRIPTION));
            mMovie.setId(object.getLong(TAG_RESULT_ID));

            mMovie.setVideoEnabled(object.getBoolean(TAG_RESULT_VIDEO_ENABLED));
            mMovie.setOriginalLanguage(object.getString(TAG_RESULT_ORIGINAL_LANG));
            mMovie.setReleaseDate(object.getString(TAG_RESULT_RELEASE));

            mMovieList.add(mMovie);
        }

        mMovieList.setPageNumber(mJsonObject.getInt(TAG_PAGE));
        mMovieList.setTotalPages(mJsonObject.getInt(TAG_TOTAL_PAGE));

        Log.i("popularmovies json", "ArrayList size is: " + mMovieList.size());
        return mMovieList;
    }
}