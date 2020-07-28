package com.example.serien_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class WatchlistFragment extends Fragment {

    private static final String ARG_TITLELIST = "argTitleList";
    private static final String ARG_IMGLIST = "argImgList";
    private static ViewPagerAdapter adapter;
    private static ViewPager viewPager;

    private ArrayList<Item> arrayList = new ArrayList<>();
    private static ArrayList<String> titlesList = new ArrayList<>();
    private static ArrayList<String> imgList = new ArrayList<>();


    public static WatchlistFragment newInstance(ArrayList<Item> arrayList) {
        WatchlistFragment fragment = new WatchlistFragment();
        Bundle args = new Bundle();

        ArrayList<String> tempTitleList = new ArrayList<>();
        ArrayList<String> tempImgList = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            tempTitleList.add(arrayList.get(i).getTitle());
            tempImgList.add(arrayList.get(i).getImage());
        }

        titlesList = tempTitleList;
        imgList = tempImgList;

        args.putStringArrayList(ARG_TITLELIST, titlesList);
        args.putStringArrayList(ARG_IMGLIST, imgList);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        viewPager = view.findViewById(R.id.viewPager);

        if (getArguments() != null) {
            titlesList = getArguments().getStringArrayList(ARG_TITLELIST);
            imgList = getArguments().getStringArrayList(ARG_IMGLIST);

            for (int i = 0; i < titlesList.size(); i++) {
                arrayList.add(new Item(titlesList.get(i), "", imgList.get(i).replace("https://", "")));
            }
        }

        adapter = new ViewPagerAdapter(arrayList, getContext());
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        return view;
    }

    public static void setAdapter(Context context) {
        ArrayList<Item> arrayList = Listen.getWatchList();
        for (Item item : arrayList) {
            item.setImage(item.getImage().replace("https://", ""));
        }

        adapter = new ViewPagerAdapter(arrayList, context);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);
    }

}
