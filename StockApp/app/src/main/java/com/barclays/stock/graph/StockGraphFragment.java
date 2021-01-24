package com.barclays.stock.graph;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.barclays.stock.R;
import com.barclays.stock.StockApplication;
import com.barclays.stock.ui.fragments.BaseFragment;
import com.barclays.stock.utils.AppUtils;
import com.barclays.stock.viewmodel.StockViewModel;
import com.barclays.stock.viewmodel.ViewModelProviderFactory;
import com.barclays.stock.viewmodel.base.BaseViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StockGraphFragment extends BaseFragment implements
        OnChartGestureListener, OnChartValueSelectedListener {

    private static final String TAG = StockGraphFragment.class.getSimpleName();
    private LineChart mChart;
    ProgressBar mProgressBar;
    private String mSymbol;
    private List<String> lables;
    private List<Float> values;
    private ViewModelProviderFactory factory;
    private StockViewModel stockViewModel;



    public static StockGraphFragment newInstance(String symbol) {
        StockGraphFragment fragment = new StockGraphFragment();
        Bundle args = new Bundle();
        args.putString(AppUtils.SYMBOL, symbol);
        fragment.setArguments(args);
        return fragment;
}

    @Override
    public int getLayoutId() {
        return R.layout.fragment_graph;
    }


    @Override
    public BaseViewModel getViewModel() {
        Log.d(TAG, "getViewModel :: " + ((StockApplication) getActivity().getApplicationContext()).getDataManager());
        factory = new ViewModelProviderFactory(StockApplication.getInstance().getDataManager(), StockApplication.getInstance().getSchedulerProvider());
        stockViewModel = ViewModelProviders.of(this, factory).get(StockViewModel.class);
        return stockViewModel;
    }
        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lables = new ArrayList<>();
        values = new ArrayList<>();
        if (getArguments() != null) {
            mSymbol = getArguments().getString(AppUtils.SYMBOL);
        }
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated :: called");

        mChart = view.findViewById(R.id.linechart);
        mProgressBar = view.findViewById(R.id.progressbar);
        initGraph();
        //loadGraphDetails(mSymbol);
        showChartData();
        showProgressBar(false);
    }

    public void showChartData() {

        lables = AppUtils.getPlottingLables(getActivity(), stockViewModel.getSeries());
        values = AppUtils.getPlottingValues(stockViewModel.getSeries());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ArrayList<Entry> val = new ArrayList<Entry>();
                for (int i = 0; i < lables.size(); i++) {
                    val.add(new Entry(values.get(i), i));
                }

                LineDataSet set = new LineDataSet(val, "Stocks Data");

                set.enableDashedLine(10f, 5f, 0f);
                set.enableDashedHighlightLine(10f, 5f, 0f);
                set.setColor(Color.BLACK);
                set.setCircleColor(Color.BLACK);
                set.setLineWidth(1f);
                set.setCircleRadius(3f);
                set.setDrawCircleHole(false);
                set.setValueTextSize(9f);
                set.setDrawFilled(true);

                ArrayList<String> dates = new ArrayList<String>();
                for (int i = 0; i < lables.size(); i++) {
                    dates.add(i, lables.get(i));
                }
                LineData data = new LineData(lables, set);
                mChart.setData(data);
                mChart.invalidate();


            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            lables = savedInstanceState.getStringArrayList(AppUtils.GRAPH_LABLES);
            values = (List<Float>) savedInstanceState.getSerializable(AppUtils.GRAPH_VALUES);
        }
    }

    private void loadGraphDetails(String symbol){
        String interval ="5m";
        String range="1d";
        stockViewModel.loadGraphSeriesDetails(interval,symbol,range);
    }
    public void initGraph() {
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setVisibleXRangeMaximum(20);
        mChart.setPinchZoom(true);
        mChart.getAxisRight().setEnabled(false);
        //mChart.setDescription("");
        mChart.setNoDataText("");
       // mChart.setNoDataTextDescription("");
        mChart.invalidate();
    }

    public void showProgressBar(Boolean show) {
        if (show) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void showError() {
        Toast.makeText(getActivity(), getResources()
                .getString(R.string.failed_to_load_finanace_chart_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(AppUtils.GRAPH_LABLES, (ArrayList<String>) lables);
        savedInstanceState.putSerializable(AppUtils.GRAPH_VALUES, (Serializable) values);
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }



    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
