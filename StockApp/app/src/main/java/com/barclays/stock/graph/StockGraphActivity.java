package com.barclays.stock.graph;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.barclays.stock.R;
import com.barclays.stock.ui.BaseActivity;
import com.barclays.stock.utils.AppUtils;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


public class StockGraphActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.frame_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.frame_container);
        final Intent intent = getIntent();
        String symbol = intent.getStringExtra(AppUtils.SYMBOL);


        StockGraphFragment mainFragment = (StockGraphFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_container);
        if (mainFragment == null) {
            // Create the fragment
            mainFragment = StockGraphFragment.newInstance(symbol);
            addFragmentToActivity(
                    getSupportFragmentManager(), mainFragment, R.id.frame_container);
        }
    }
    public void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}