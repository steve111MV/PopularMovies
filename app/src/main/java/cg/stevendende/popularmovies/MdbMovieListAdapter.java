/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */
package cg.stevendende.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cg.stevendende.popularmovies.model.MdbMovieList;

/**
 * Receives a List of Popular Movies and populates ListView (GridView in my case) with.
 */
public class MdbMovieListAdapter extends BaseAdapter {

    private static final int ITEM_TYPE_LEFT = 1;
    private static final int ITEM_TYPE_RIGHT = 2;
    private MdbMovieList mMoviesList;
    private Context mContext;

    public MdbMovieListAdapter(MdbMovieList list, Context context) {
        this.mMoviesList = list;
        this.mContext = context;
    }

    /**
     * Sets the received MdbMovieList to the global MdbMovieList presefined in the adapter.
     */
    public void setMoviesList(MdbMovieList list) {
        this.mMoviesList = list;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position The position of the item within the adapter's data set whose ItemView Type
     *                 we want.
     * @return The type of item corresponding the specified position.
     */
    @Override
    public int getItemViewType(int position) {
        if ((position + 1) % 2 == 0)
            return ITEM_TYPE_RIGHT;
        return ITEM_TYPE_LEFT;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return (mMoviesList == null) ? 0 : 3;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mMoviesList.get(position);
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout rootItemview = null;

        if (convertView != null) {
            rootItemview = (LinearLayout) convertView;
        } else {
            int viewType = getItemViewType(position);

            if (viewType == ITEM_TYPE_LEFT) {
                rootItemview = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
            } else {
                rootItemview = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
            }
        }

        ImageView mPposterImg = (ImageView) rootItemview.findViewById(R.id.image);
        TextView mTitleTv = (TextView) rootItemview.findViewById(R.id.title);

        Glide.with(mContext)
                .load(MainActivityFragment.API_IMAGE_BASE_URL
                        + MainActivityFragment.API_IMAGE_SIZE_NORMAL
                        + mMoviesList.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mPposterImg)
                .onLoadFailed(null, null);

        return rootItemview;
    }

}
