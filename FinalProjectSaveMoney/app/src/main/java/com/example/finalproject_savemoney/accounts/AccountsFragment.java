package com.example.finalproject_savemoney.accounts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_savemoney.R;
import com.example.finalproject_savemoney.fragments.FragmentCommunicator;
import com.example.finalproject_savemoney.fragments.OnFragmentActionListener;
import com.example.finalproject_savemoney.repo.database.AccountEntity;

import java.util.ArrayList;
import java.util.List;

public class AccountsFragment extends Fragment {

    private OnFragmentActionListener onFragmentActionListener;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AccountsAdapter adapter;
    private List<AccountEntity> accounts = new ArrayList<AccountEntity>();
    private AccountsViewModel viewModel;
    private FragmentCommunicator communicator = new FragmentCommunicator() {
        @Override
        public void onItemClickListener(String text) {
            if (onFragmentActionListener != null) {
//                onOpenFragmentListener.onOpenAccountsFragment();
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static AccountsFragment newInstance() {
        AccountsFragment fragment = new AccountsFragment();
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
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycler_view_accounts);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.accounts_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            if (onFragmentActionListener != null) {
//                onOpenFragmentListener.onOpenCityListFragment();
                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AccountsAdapter(accounts, communicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
        viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccountEntity>>() {
            @Override
            public void onChanged(List<AccountEntity> accountEntities) {
                accounts = accountEntities;
                adapter.setAccounts(accounts);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onFragmentActionListener = null;
    }
}
