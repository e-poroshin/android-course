package com.example.finalproject_savemoney.fragments;

import com.example.finalproject_savemoney.repo.database.AccountEntity;

public interface FragmentCommunicator {
    void onItemClickListener(String categoryName);
    void onItemAccountClickListener(AccountEntity accountEntity);
}
