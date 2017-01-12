package cg.stevendende.popularmovies.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.ParseException;

import cg.stevendende.popularmovies.R;
import cg.stevendende.popularmovies.ServerAsyncTask;
import cg.stevendende.popularmovies.Tools;
import cg.stevendende.popularmovies.model.MdbMovie;

public class DetailsFragment extends Fragment {

    private static final float RATING_BAR_MAX_STAR = 5;
    private static final float MAX_API_RATING = 10;
    MdbMovie movie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.from(getActivity()).inflate(R.layout.fragment_details, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.app_bar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(R.mipmap.ic_launcher);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("______", verticalOffset + "");
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed

                    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
                } else {
                    //expanded
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(false);
                }

            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (movie == null) {
            Bundle data = getArguments();
            movie = data.getParcelable(MainActivity.EXTRA_MOVIE);
        }

        ImageView backdropImg = (ImageView) rootView.findViewById(R.id.backdropImageView);
        ImageView posterImg = (ImageView) rootView.findViewById(R.id.posterImageView);
        TextView releaseDate = (TextView) rootView.findViewById(R.id.releaseDate);
        TextView rating = (TextView) rootView.findViewById(R.id.rating);
        TextView overview = (TextView) rootView.findViewById(R.id.overview);

        SimpleRatingBar ratingBar = (SimpleRatingBar) rootView.findViewById(R.id.simpleRatingBar);

        if (movie != null) {

            //The text has to be set on Collapsing instead of Toobar to avoid configChange manipulations
            collapsingToolbarLayout.setTitle(movie.getOriginalTitle());

            releaseDate.setText(movie.getReleaseDate());

            rating.setText(movie.getRating() + "/10");
            float ratingBarValue = ((movie.getRating() * RATING_BAR_MAX_STAR) / MAX_API_RATING);
            ratingBar.setRating(ratingBarValue);

            overview.setText(movie.getDecription());

            Glide.with(getActivity())
                    .load(ServerAsyncTask.API_IMAGE_BASE_URL + ServerAsyncTask.API_IMAGE_SIZE_BIG + movie.getPosterUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(backdropImg);

            Glide.with(getActivity())
                    .load(ServerAsyncTask.API_IMAGE_BASE_URL + ServerAsyncTask.API_IMAGE_SIZE_BIG + movie.getImageUrl())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(posterImg);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        return true;
    }
}
