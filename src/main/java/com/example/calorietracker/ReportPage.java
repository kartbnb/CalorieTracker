package com.example.calorietracker;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportPage extends Fragment {
    View vReportPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        vReportPage = inflater.inflate(R.layout.report_page, container, false);
        return vReportPage;
    }
}

