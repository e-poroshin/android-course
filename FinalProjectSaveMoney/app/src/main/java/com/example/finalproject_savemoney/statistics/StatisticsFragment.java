package com.example.finalproject_savemoney.statistics;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject_savemoney.R;
import com.example.finalproject_savemoney.categories.CategoryViewModel;
import com.example.finalproject_savemoney.fragments.OnFragmentActionListener;
import com.example.finalproject_savemoney.operations.OperationsViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private OnFragmentActionListener onFragmentActionListener;
    private Toolbar toolbar;
    private PieChart pieChart;
    private List<String> categoryNames = new ArrayList<>();
    private CategoryViewModel viewModelCategory;
    private OperationsViewModel viewModelOperation;

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentActionListener) {
            onFragmentActionListener = (OnFragmentActionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        pieChart = view.findViewById(R.id.pieChart);

//        viewModelCategory = new ViewModelProvider(this).get(CategoryViewModel.class);
//        viewModelCategory.getLiveDataCategoryNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> categories) {
//                categoryNames = categories;
//                Log.d("MY_TAG", categoryNames.toString());
//                setUpPieChart(categoryNames);
//            }
//        });
        viewModelOperation = new ViewModelProvider(this).get(OperationsViewModel.class);
        viewModelOperation.getLiveDataCategoryNamesFromOperations().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                categoryNames = categories;
                Log.d("MY_TAG", categoryNames.toString());
                setUpPieChart(categoryNames);
            }
        });

        return view;
    }

    private void setUpPieChart(List<String> categories) {
        float consumptionValues[] = {98.8f, 123.8f, 161.6f, 24.2f, 52f, 58.2f, 35.4f, 13.8f, 78.4f, 203.4f, 240.2f};
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            pieEntries.add(new PieEntry(consumptionValues[i], categories.get(i)));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "Отчет по категориям");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextColor(R.color.colorPrimaryDark);
        dataSet.setValueTextSize(14);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onFragmentActionListener = null;
    }
}
