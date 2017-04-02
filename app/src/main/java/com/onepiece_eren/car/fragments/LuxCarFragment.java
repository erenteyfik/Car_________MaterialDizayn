package com.onepiece_eren.car.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.onepiece_eren.car.MainActivity;
import com.onepiece_eren.car.adapters.CarAdapter;
import com.onepiece_eren.car.domain.Car;
import com.onepiece_eren.car.R;
import com.onepiece_eren.car.interfaces.RecyclerViewOnClickListenerInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Teyfik on 28.3.2017.
 */
public class LuxCarFragment extends CarFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            myList=savedInstanceState.getParcelableArrayList("myList");
        }
        else{
            myList=((MainActivity)getActivity()).getCarsByCategory(1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car, container, false);

        myrecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        myrecyclerView.setHasFixedSize(false);
        myrecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    fabmenu.hideMenuButton(true);
                } else {
                    fabmenu.showMenuButton(true);
                }

                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                CarAdapter adapter = (CarAdapter) recyclerView.getAdapter();

                if (myList.size() == gridLayoutManager.findLastCompletelyVisibleItemPosition() + 1) {
                    List<Car> List2aux = ((MainActivity) getActivity()).getSetCarList(10, 0);
                    for (int i = 0; i < List2aux.size(); i++) {
                        adapter.addListItem(List2aux.get(i), myList.size());
                    }
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        myrecyclerView.setLayoutManager(gridLayoutManager);

        CarAdapter adapter = new CarAdapter(getActivity(), myList);
        adapter.RecyclerViewOnClickListenerInterface(this);
        myrecyclerView.setAdapter(adapter);
        setFloatingActionButton();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) { super.onSaveInstanceState(outState);  }
}
