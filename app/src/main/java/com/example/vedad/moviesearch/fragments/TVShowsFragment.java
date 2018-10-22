package com.example.vedad.moviesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vedad.moviesearch.api.TheMovieDbClient;
import com.example.vedad.moviesearch.api.TheMovieDbClientService;
import com.example.vedad.moviesearch.R;
import com.example.vedad.moviesearch.fragments.adapters.MyTVShowsRecyclerViewAdapter;
import com.example.vedad.moviesearch.models.ApiResult;
import com.example.vedad.moviesearch.models.TVShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TVShowsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private MyTVShowsRecyclerViewAdapter myTVShowsRecyclerViewAdapter;
    private MyTVShowsRecyclerViewAdapter mySearchRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private static final int LIST_COUNT = 10;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TVShowsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshows_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            GetTopRatedTVShows();
        }
        return view;
    }

    private void GetTopRatedTVShows() {
        TheMovieDbClient client = TheMovieDbClientService.createService(TheMovieDbClient.class);

        Call<ApiResult<TVShow>> call = client.getTopRatedTVShows();
        call.enqueue(new Callback<ApiResult<TVShow>>() {
            @Override
            public void onResponse(Call<ApiResult<TVShow>> call, Response<ApiResult<TVShow>> response) {
                myTVShowsRecyclerViewAdapter = new MyTVShowsRecyclerViewAdapter(response.body().results.subList(0,LIST_COUNT),mListener);
                recyclerView.setAdapter(myTVShowsRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ApiResult<TVShow>> call, Throwable t) {
                Toast.makeText(getContext(),getResources().getString(R.string.error),Toast.LENGTH_SHORT);
            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TVShow item);
    }

    public void SetAdapter(ApiResult<TVShow> tvShowsApiResult)
    {
        if(tvShowsApiResult.total_results>LIST_COUNT) {
            tvShowsApiResult.results = tvShowsApiResult.results.subList(0,LIST_COUNT);
        }
            mySearchRecyclerViewAdapter = new MyTVShowsRecyclerViewAdapter(tvShowsApiResult.results, mListener);
            recyclerView.removeAllViewsInLayout();
            recyclerView.setAdapter(mySearchRecyclerViewAdapter);

    }
    public void RestoreAdapter()
    {
        recyclerView.removeAllViewsInLayout();
        if(recyclerView.getAdapter()!=myTVShowsRecyclerViewAdapter) {
            GetTopRatedTVShows();
        }
    }
}
