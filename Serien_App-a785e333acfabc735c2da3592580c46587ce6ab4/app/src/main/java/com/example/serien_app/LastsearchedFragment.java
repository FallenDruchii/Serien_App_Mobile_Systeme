package com.example.serien_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment in dem die zuletzt gesuchten Titel angezeigt werden.
 * Nutzt die custom_list_view für die List View.
 */

public class LastsearchedFragment extends Fragment {

    private static final String ARG_TITLELIST = "argTitleList";

    private ListView listView;

    private static ArrayList<String> titlesList = new ArrayList<>();


    public static LastsearchedFragment newInstance(ArrayList<String> arrayList) {
        LastsearchedFragment fragment = new LastsearchedFragment();
        Bundle args = new Bundle();

        titlesList = arrayList;

        args.putStringArrayList(ARG_TITLELIST, titlesList);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lastsearched, container, false);
        listView = view.findViewById(R.id.lv_fragment_lastsearched);

        if (getArguments() != null) {
            titlesList = getArguments().getStringArrayList(ARG_TITLELIST);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.custom_list_view, titlesList);
        listView.setAdapter(arrayAdapter);

        return view;
    }
}
