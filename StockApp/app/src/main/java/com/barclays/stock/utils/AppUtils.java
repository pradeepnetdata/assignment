package com.barclays.stock.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.barclays.stock.R;
import com.barclays.stock.model.graph.Series;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class AppUtils {

    public static final String DB_NAME = "stock.db";
    public static final String STATUS_CODE_FAILED = "failed";
    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static String MICROSOFT_STOCK_SYMBOL = "MSFT";
    public static String SYMBOL = "symbol";
    public static String GRAPH_LABLES = "graph lables";
    public static String GRAPH_VALUES = "graph values";

    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
    public static List<String> getPlottingLables(Context context, List<Series> series) {
        List<String> lables = new ArrayList<>();

        for (Series series1 : series) {

            try {
                SimpleDateFormat srcFormat = new SimpleDateFormat("yyyyMMdd");
                String date = android.text.format.DateFormat.getMediumDateFormat(context).
                        format(srcFormat.parse(series1.getDate()));
                lables.add(date);

            } catch (ParseException ignored) {

            }
        }
        return lables;
    }

    public static List<Float> getPlottingValues(List<Series> series) {

        List<Float> values = new ArrayList<>();

        for (Series series1 : series) {
            values.add(series1.getClose());
        }

        return values;
    }
}
