/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */
package cg.stevendende.popularmovies;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cg.stevendende.popularmovies.model.MdbMovie;
import cg.stevendende.popularmovies.model.MdbMovieList;

/**
 * Receives a List of Popular Movies and populates ListView (GridView in my case) with.
 */
public class MdbMovieRecyclerAdapter extends RecyclerView.Adapter<MdbMovieRecyclerAdapter.MyViewHolder> {

    private static final int ITEM_TYPE_LEFT = 1;
    private static final int ITEM_TYPE_RIGHT = 2;
    public Context mContext;
    private MdbMovieList mMoviesList;
    private MovieClickInterface mClickListener;

    public MdbMovieRecyclerAdapter(MdbMovieList list, Context context) {
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
     * Sets the received MdbMovieList to the global MdbMovieList presefined in the adapter.
     *
     * @param mListener Instanciate the listener
     */
    public void setMovieClickInterface(MovieClickInterface mListener) {
        this.mClickListener = mListener;
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
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */

    public MdbMovie getItem(int position) {
        return mMoviesList.get(position);
    }

    @Override
    public int getItemCount() {
        return (mMoviesList == null) ? 0 : mMoviesList.size();
    }

    /**
     * Called when RecyclerView needs a new {@link MyViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(MyViewHolder, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(MyViewHolder, int)
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link MyViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link MyViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(MyViewHolder, int)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.mTitleTv.setText(mMoviesList.get(position).getTitre());

        Glide.with(mContext)
                .load(ServerAsyncTask.API_IMAGE_BASE_URL
                        + ServerAsyncTask.API_IMAGE_SIZE_NORMAL
                        + mMoviesList.get(position).getImageUrl())
                .placeholder(R.drawable.alt_image)
                .animate(R.anim.anim_zoom)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);
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

    public interface MovieClickInterface {
        void onMovieClick(MdbMovie movie);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;
        TextView mTitleTv;

        public MyViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTitleTv = (TextView) itemView.findViewById(R.id.title);

            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            mClickListener.onMovieClick(mMoviesList.get(getAdapterPosition()));
            Log.i("Fragment Main ", "item clicked : Callback to fragment");
        }
    }

}
