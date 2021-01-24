package com.barclays.stock.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.barclays.stock.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.barclays.stock.graph.StockGraphActivity;
import com.barclays.stock.graph.StockGraphFragment;
import com.barclays.stock.ui.fragments.StockSearchFragment;
import com.barclays.stock.ui.fragments.WatchListFragment;
import com.barclays.stock.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class StockActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = StockActivity.class.getName();
    private MenuItem searchViewItem;
    private SearchView searchView;
    private Button btnWatchlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Stock");
        setSupportActionBar(toolbar);
       /* FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new StockSearchFragment(),"stock_search");
        ft.commit();*/

        StockSearchFragment mainFragment = (StockSearchFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_container);
        if (mainFragment == null) {
            // Create the fragment
            mainFragment = StockSearchFragment.newInstance();
            //Fragment fragment = new StockSearchFragment();
            openFragment(mainFragment);
        }



       /* btnWatchlist = (Button) findViewById(R.id.btn_watchlist);
        btnWatchlist.setOnClickListener(this::onClick);*/

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Fragment mainFragment = (StockSearchFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.frame_container);
                        if (mainFragment == null) {
                            // Create the fragment
                            mainFragment = StockSearchFragment.newInstance();
                            //Fragment fragment = new StockSearchFragment();
                            openFragment(mainFragment);
                        }
                        break;
                    case R.id.navigation_watchlist:
                         mainFragment = (WatchListFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.frame_container);
                        if (mainFragment == null) {
                            // Create the fragment
                            mainFragment = WatchListFragment.newInstance();
                            //Fragment fragment = new StockSearchFragment();
                            openFragment(mainFragment);
                        }
                        break;
                    case R.id.navigation_graph:
                        mainFragment = (StockGraphFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.frame_container);
                        if (mainFragment == null) {
                            // Create the fragment
                            mainFragment = StockGraphFragment.newInstance(AppUtils.SYMBOL);
                            //Fragment fragment = new StockSearchFragment();
                            openFragment(mainFragment);
                        }
                        break;
                }
                return true;
            }
        });

    }

    private void openFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        searchViewItem = menu.findItem(R.id.menuSearch);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchViewItem.getActionView();

        //you can put a hint for the search input field
        searchView.setQueryHint("Search...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(true);

        //here we will get the search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit :: query result :: "+query);
                //do the search here
                String symbol = query.toUpperCase(Locale.US);
                searchStockInfoByName(symbol);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // mAdapter.getFilter().filter(newText);
                Log.d(TAG, "query result :: "+newText);
                return true;
            }
        });

        return true;
    }

    public void showSearchMenu(){
        if (searchViewItem!=null){
            searchViewItem.setVisible(true);
            searchView.setVisibility(View.VISIBLE);
        }
    }
    private void hideSearchMenu(){
        searchViewItem.setVisible(false);
        searchView.setVisibility(View.GONE);
    }
    private void searchStockInfoByName(final String symbol){
        Log.d(TAG, "subscribeToLiveData ::  start " );

        boolean isNumberExist = symbol.matches(".*\\d.*");

        if(isNumberExist){
            Toast.makeText(this,"Symbol is not valid. Please try again", Toast.LENGTH_LONG).show();
            if (searchView!=null){
                searchView.setQuery("", false);
            }
            return;
        }
        showLoading();
        FragmentManager fm = getSupportFragmentManager();
        StockSearchFragment fragment = (StockSearchFragment)fm.findFragmentById(R.id.fragment_container);

        if(isNetworkConnected()) {
            fragment.loadStocksInfo(symbol);
        } else {
            Log.i(TAG, "No internet connection found");
            hideLoading();
            hideKeyboard();
            Toast.makeText(this,"No internet connection found!", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
            showWatchListButton();
        }

    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick :: called");
     /*   WatchListFragment fragment = new WatchListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        hideSearchMenu();*/

        Intent intent = new Intent(this, StockGraphActivity.class);
        intent.putExtra(AppUtils.SYMBOL, AppUtils.MICROSOFT_STOCK_SYMBOL);
        startActivity(intent);

    }

    public void hideWatchListButton() {
        if (btnWatchlist != null && btnWatchlist.getVisibility() == View.VISIBLE) {
            // Its visible
            btnWatchlist.setVisibility(View.GONE);
        }
    }

    public void showWatchListButton() {
        if (btnWatchlist != null && btnWatchlist.getVisibility() == View.GONE) {
            // Its visible
            btnWatchlist.setVisibility(View.VISIBLE);
        }
    }


}