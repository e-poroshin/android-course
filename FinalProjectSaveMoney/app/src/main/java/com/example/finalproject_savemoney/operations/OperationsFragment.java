package com.example.finalproject_savemoney.operations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import com.example.finalproject_savemoney.AddOperationActivity;
import com.example.finalproject_savemoney.R;
import com.example.finalproject_savemoney.fragments.FragmentCommunicator;
import com.example.finalproject_savemoney.fragments.OnFragmentActionListener;
import com.example.finalproject_savemoney.repo.database.Operation;
import com.example.finalproject_savemoney.repo.database.OperationEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class OperationsFragment extends Fragment {

    private static final String KEY_OPERATION_ID = "KEY_OPERATION_ID";
    public static final int REQUEST_CODE_ADD_OPERATION = 1;

    private OnFragmentActionListener onFragmentActionListener;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private OperationsAdapter adapter;
    private List<Operation> operations = new ArrayList<>();
    private OperationsViewModel viewModel;
    private FragmentCommunicator communicator = new FragmentCommunicator() {
        @Override
        public void onItemClickListener(String text) {
            if (onFragmentActionListener != null) {
//                onOpenFragmentListener.onOpenAccountsFragment();
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static OperationsFragment newInstance() {
        OperationsFragment fragment = new OperationsFragment();
        return fragment;
    }

    public static OperationsFragment newBundleInstance(Operation operation) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_OPERATION_ID, (Serializable) operation);
        OperationsFragment fragment = new OperationsFragment();
        fragment.setArguments(bundle);
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
        View view = inflater.inflate(R.layout.fragment_operations, container, false);
        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        view.findViewById(R.id.fabAddOperation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AddOperationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_OPERATION);
            }
        });
        recyclerView = view.findViewById(R.id.recycler_view_operations);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new OperationsAdapter(operations, communicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(OperationsViewModel.class);
        viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<Operation>>() {
            @Override
            public void onChanged(List<Operation> operations) {
                OperationsFragment.this.operations = operations;
                adapter.setOperations(operations);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_OPERATION && resultCode == RESULT_OK) {
            OperationEntity operation = (OperationEntity) data.getSerializableExtra(AddOperationActivity.EXTRA_REPLY);
            viewModel.insert(operation);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onFragmentActionListener = null;
    }
}
