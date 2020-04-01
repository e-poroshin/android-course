package com.example.finalproject_savemoney;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject_savemoney.accounts.AccountsFragment;
import com.example.finalproject_savemoney.categories.CategoriesFragment;
import com.example.finalproject_savemoney.fragments.OnFragmentActionListener;
import com.example.finalproject_savemoney.operations.OperationsFragment;
import com.example.finalproject_savemoney.statistics.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnFragmentActionListener {

    private static long back_pressed;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView bottomNavigationView;
    public final String SAVED_STATE = "SAVED_STATE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if (!isFirstVisit()) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            saveFirstVisit(true);
        }
        setContentView(R.layout.activity_main);
        onOpenOperationsFragment();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_categories:
                        onOpenCategoriesFragment();
                        break;
                    case R.id.action_accounts:
                        onOpenAccountsFragment();
                        break;
                    case R.id.action_operations:
                        onOpenOperationsFragment();
                        break;
                    case R.id.action_statistics:
                        onOpenStatisticsFragment();
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
        }
        else {
            Toast.makeText(getBaseContext(), R.string.backPressed_ru,
                    Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    private void saveFirstVisit(boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SAVED_STATE, state);
        editor.apply();
    }

    private boolean isFirstVisit() {
        return sharedPreferences.getBoolean(SAVED_STATE, false);
    }

    @Override
    public void onOpenOperationsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, OperationsFragment.newInstance(), OperationsFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onOpenAccountsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, AccountsFragment.newInstance(), AccountsFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onOpenCategoriesFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, CategoriesFragment.newInstance(), CategoriesFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onOpenStatisticsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, StatisticsFragment.newInstance(), StatisticsFragment.class.getSimpleName())
                .commit();
    }

}
