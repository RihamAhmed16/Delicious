package com.example.delicious.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.delicious.AdapterCategory;
import com.example.delicious.Category;
import com.example.delicious.GetCategory;
import com.example.delicious.R;
import com.example.delicious.SubcategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static String Cno;

    private HomeViewModel homeViewModel;

    GridView gv;
    Category cate;
    GetCategory gc=new GetCategory();
    AdapterCategory adapterC;
    ArrayList<Category> data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        gv=root.findViewById(R.id.cate_GV);
        data=new ArrayList<>(gc.getData());
        adapterC=new AdapterCategory(getActivity(),data);
        gv.setAdapter(adapterC);

        SwipeRefreshLayout swp=root.findViewById(R.id.swp);
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data=new ArrayList<>(gc.getData());
                adapterC=new AdapterCategory(getActivity(),data);
                gv.setAdapter(adapterC);
                swp.setRefreshing(false);
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cate=data.get(i);
                Cno=cate.getCategoryNo();
                startActivity(new Intent(getActivity(), SubcategoryActivity.class));

            }
        });


        return root;
    }
}