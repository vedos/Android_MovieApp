package com.example.vedad.moviesearch.fragments.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vedad.moviesearch.DetailsActivity;
import com.example.vedad.moviesearch.R;
import com.example.vedad.moviesearch.fragments.MoviesFragment.OnListFragmentInteractionListener;
import com.example.vedad.moviesearch.helper.Config;
import com.example.vedad.moviesearch.models.Movie;

import java.util.List;


public class MyMoviesRecyclerViewAdapter extends RecyclerView.Adapter<MyMoviesRecyclerViewAdapter.ViewHolder> {

    private final List<Movie> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMoviesRecyclerViewAdapter(List<Movie> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        holder.mItem = mValues.get(position);
        Glide.with(holder.mView).asBitmap().
                load(Config.imageUrl + "w92/" + mValues.get(position).poster_path).into(holder.mImageView);

        holder.mContentView.setText(mValues.get(position).title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
               }

                Intent myIntent = new Intent((holder.mView.getContext()), DetailsActivity.class);
                myIntent.putExtra("title", mValues.get(position).title);
                myIntent.putExtra("backdrop_path", mValues.get(position).backdrop_path);
                myIntent.putExtra("overview", mValues.get(position).overview);
                holder.mView.getContext().startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mContentView;
        public Movie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.image);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
