package com.example.vedad.moviesearch;

import android.app.SearchManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vedad.moviesearch.api.TheMovieDbClient;
import com.example.vedad.moviesearch.api.TheMovieDbClientService;
import com.example.vedad.moviesearch.fragments.MoviesFragment;
import com.example.vedad.moviesearch.fragments.TVShowsFragment;
import com.example.vedad.moviesearch.models.ApiResult;
import com.example.vedad.moviesearch.models.Movie;
import com.example.vedad.moviesearch.models.TVShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int FRAGMENT_MOVIE = 0;
    private static final int FRAGMENT_TVSHOWS = 1;
    private ViewPager viewPager;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        actionBar = getSupportActionBar();
        preparePager();
        prepareTabs();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.action_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark));

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {

                    MoviesFragment fragmentMovies = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(getFragmentName(R.id.viewpager, FRAGMENT_MOVIE));
                    if (fragmentMovies != null) {
                        fragmentMovies.RestoreAdapter();
                    }

                    TVShowsFragment fragmentTVShows = (TVShowsFragment) getSupportFragmentManager().findFragmentByTag(getFragmentName(R.id.viewpager, FRAGMENT_TVSHOWS));
                    if (fragmentTVShows != null) {
                        fragmentTVShows.RestoreAdapter();
                    }
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                return true;
            }
        });

        SearchManager searchManager = (SearchManager) this.getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                TheMovieDbClient client = TheMovieDbClientService.createService(TheMovieDbClient.class);

                if(viewPager.getCurrentItem()==FRAGMENT_MOVIE) {
                    SearchMovies(query, client);
                }

                if(viewPager.getCurrentItem()==FRAGMENT_TVSHOWS){
                    SearchTVShows(query,client);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(query.length()>=3) {
                    TheMovieDbClient client = TheMovieDbClientService.createService(TheMovieDbClient.class);

                    if (viewPager.getCurrentItem() == FRAGMENT_MOVIE) {
                        SearchMovies(query, client);
                    }

                    if (viewPager.getCurrentItem() == FRAGMENT_TVSHOWS) {
                        SearchTVShows(query, client);
                    }
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void SearchMovies(String query, TheMovieDbClient client) {
        Call<ApiResult<Movie>> call = client.searchMovies(query);
        call.enqueue(new Callback<ApiResult<Movie>>() {
            @Override
            public void onResponse(Call<ApiResult<Movie>> call, Response<ApiResult<Movie>> response) {
                MoviesFragment fragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(getFragmentName(R.id.viewpager, FRAGMENT_MOVIE));
                if (fragment != null) {
                    fragment.SetAdapter(response.body());
                }
            }

            @Override
            public void onFailure(Call<ApiResult<Movie>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT);
            }
        });
    }

    private void SearchTVShows(String query, TheMovieDbClient client) {
        Call<ApiResult<TVShow>> call = client.searchTvShows(query);
        call.enqueue(new Callback<ApiResult<TVShow>>() {
            @Override
            public void onResponse(Call<ApiResult<TVShow>> call, Response<ApiResult<TVShow>> response) {
                TVShowsFragment fragment = (TVShowsFragment) getSupportFragmentManager().findFragmentByTag(getFragmentName(R.id.viewpager, FRAGMENT_TVSHOWS));
                if (fragment != null) {
                    fragment.SetAdapter(response.body());
                }
            }

            @Override
            public void onFailure(Call<ApiResult<TVShow>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT);
            }
        });
    }

    private static String getFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    private void prepareTabs() {

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.TabListener listener = new ActionBar.TabListener()
        {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
            {
                int position=tab.getPosition();
                viewPager.setCurrentItem(position);

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
            {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
            {

            }
        };
        ActionBar.Tab tab1 = actionBar.newTab().setText(getResources().getString(R.string.movies)).setTabListener(listener);
        actionBar.addTab(tab1);

        ActionBar.Tab tab2 = actionBar.newTab().setText(getResources().getString(R.string.tv_shows)).setTabListener(listener);
        actionBar.addTab(tab2);
    }

    private void preparePager() {
        final FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                if (position == FRAGMENT_TVSHOWS) {
                    fragment = new TVShowsFragment();
                }

                if (position == FRAGMENT_MOVIE) {
                    fragment = new MoviesFragment();
                }

                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(actionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS) {
                    super.onPageSelected(position);
                    actionBar.setSelectedNavigationItem(position);
                }
            }
        });
    }

    public void onResume(){
        super.onResume();
    }
}
