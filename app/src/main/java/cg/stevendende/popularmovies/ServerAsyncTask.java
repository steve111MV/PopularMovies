package cg.stevendende.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import cg.stevendende.popularmovies.model.MdbMovieList;

public class ServerAsyncTask extends AsyncTask<String, Void, MdbMovieList> {

    /**
     * TODO: replace the following value with your key from themoviedb.org
     */
    public static final String THE_MOVIE_DB_API_KEY = "YOUR_API_KEY_HERE";

    public static final String API_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String API_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static String API_IMAGE_SIZE_NORMAL = "w185";
    public static String API_IMAGE_SIZE_BIG = "w300";
    private static HttpURLConnection sUrlCon;
    private static BufferedReader sBufferReader;
    private String mMoviesJsonString;

    @Override
    protected MdbMovieList doInBackground(String... param) {

        String paramSort = param[0];

        Uri builder = Uri.parse(API_BASE_URL + paramSort)
                .buildUpon()
                .appendQueryParameter("api_key", THE_MOVIE_DB_API_KEY)
                .build();

        URL httpUrl = null;

        try {
            httpUrl = new URL(builder.toString());

            //Creating the connextion to the API
            sUrlCon = (HttpURLConnection) httpUrl.openConnection();
            sUrlCon.setRequestMethod("GET");
            sUrlCon.connect();

            // Reading the request answer body
            InputStream inputStream = null;

            inputStream = sUrlCon.getInputStream();

            if (inputStream == null) return null;

            StringBuffer stringBuffer = new StringBuffer();
            sBufferReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = sBufferReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

            if (stringBuffer.length() == 0) return null;

            mMoviesJsonString = stringBuffer.toString();
            //Log.i("pm json ___ ", mMoviesJsonString);

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            Log.i("pm json ___ ", "MalformedURLException");
        } catch (ProtocolException e1) {
            e1.printStackTrace();
            Log.i("pm json ___ ", "ProtocolException");
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.i("pm json ___ ", "IOException");

        } finally {
            if (sUrlCon != null) sUrlCon.disconnect();

            if (null != sBufferReader) {
                try {
                    sBufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //Parses a JSON String
        MDBApiParser parser = new MDBApiParser();

        try {
            return parser.getPopularMovies(mMoviesJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
