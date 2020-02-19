package com.example.weathernetwork.fragments;

import android.content.Context;
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

import com.example.weathernetwork.BuildConfig;
import com.example.weathernetwork.R;
import com.example.weathernetwork.adapter.CityListAdapter;
import com.example.weathernetwork.repo.database.CityEntity;
import com.example.weathernetwork.viewmodel.CityViewModel;
import com.example.weathernetwork.weather.Consts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityListFragment extends Fragment implements AddCityDialogFragment.EditNameDialogListener {

    private Toolbar toolbar;
    private FloatingActionButton fabAddCity;
    private AddCityDialogFragment addCityDialogFragment;
    private RecyclerView recyclerView;
    private CityListAdapter adapter;
    private List<CityEntity> cities = new ArrayList<CityEntity>();
    private CityViewModel viewModel;

    private OnOpenFragmentListener onOpenFragmentListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnOpenFragmentListener) {
            onOpenFragmentListener = (OnOpenFragmentListener) context;
        }
    }

    private FragmentCommunicator communicator = new FragmentCommunicator() {
        @Override
        public void onItemClickListener(String cityName) {
            if (onOpenFragmentListener != null) {
                onOpenFragmentListener.onOpenForecastFragmentByCityName(cityName);
            }
        }
    };


    public static CityListFragment newInstance() {
        CityListFragment fragment = new CityListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        fabAddCity = view.findViewById(R.id.fabAddCity);
        recyclerView = view.findViewById(R.id.recycler_view_city_list);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setTitle("Choose a City");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOpenFragmentListener != null) {
                    onOpenFragmentListener.onOpenForecastFragment();
                }
            }
        });

        fabAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCityDialogFragment = new AddCityDialogFragment();
                addCityDialogFragment.setTargetFragment(CityListFragment.this, 1);
                addCityDialogFragment.show(getParentFragmentManager(), addCityDialogFragment.getClass().getName());
            }
        });

        adapter = new CityListAdapter(cities, communicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(CityViewModel.class);
        viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<CityEntity>>() {
                    @Override
                    public void onChanged(List<CityEntity> cityEntities) {
                        cities = cityEntities;
                        adapter.setCities(cities);
                    }
                });
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        validateCityName(inputText);
    }

    private void validateCityName(final String cityName) {

        String apiKey = BuildConfig.API_KEY;
        String url = String.format(Consts.GET_CURRENT_WEATHER_BY_CITY_NAME_METRIC, cityName, apiKey);

        final Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                showToast("Connection failed, try again please");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str = response.body().string();

                if (str.equals("{\"cod\":\"404\",\"message\":\"city not found\"}")) {
                    showToast("This city does not exist, try again please");
                } else {
                    showToast("The City has been successfully added");
                    viewModel.insert(new CityEntity(cityName));
                }
            }
        });
    }

    private void showToast(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onOpenFragmentListener = null;
    }
}
