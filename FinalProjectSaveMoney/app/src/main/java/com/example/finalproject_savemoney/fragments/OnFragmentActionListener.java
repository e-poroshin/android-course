package com.example.finalproject_savemoney.fragments;

import com.example.finalproject_savemoney.repo.database.Operation;

public interface OnFragmentActionListener {

    void onOpenOperationsFragment();
    void onOpenOperationsFragmentBundle(Operation operation);
    void onOpenAccountsFragment();
    void onOpenCategoriesFragment();
    void onOpenStatisticsFragment();
}
