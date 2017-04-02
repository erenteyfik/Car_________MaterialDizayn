package com.onepiece_eren.car.fragments;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.onepiece_eren.car.CarActivity;
import com.onepiece_eren.car.MainActivity;
import  com.onepiece_eren.car.R;
import com.onepiece_eren.car.adapters.CarAdapter;
import com.onepiece_eren.car.domain.Car;
import com.onepiece_eren.car.interfaces.RecyclerViewOnClickListenerInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Teyfik on 23.3.2017.
 */
public class CarFragment extends Fragment implements RecyclerViewOnClickListenerInterface, View.OnClickListener{
    protected static final String TAG = "LOG";
    protected RecyclerView myrecyclerView;
    protected List<Car> myList;
    //private FloatingActionButton fab;
    protected FloatingActionMenu fabmenu;
    //private protected arasındaki farka bak
    // arasındaki fark protected yapmamızın sebebi lux spor kamyonet populer fragmentlerde de
    // oluşturduğumuz View'imizi kullanmamızı saglıyor

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            myList=savedInstanceState.getParcelableArrayList("myList");
        }
        else{
            myList=((MainActivity)getActivity()).getCarsByCategory(0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car, container, false);

        myrecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        myrecyclerView.setHasFixedSize(true);
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

                LinearLayoutManager llm = (LinearLayoutManager) myrecyclerView.getLayoutManager();
                CarAdapter adapter = (CarAdapter) myrecyclerView.getAdapter();

                if (myList.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                    List<Car> List2aux = ((MainActivity) getActivity()).getSetCarList(10, 0);
                    ((MainActivity) getActivity()).getListCars().addAll( List2aux );
                    for (int i = 0; i < List2aux.size(); i++) {
                        adapter.addListItem(List2aux.get(i), myList.size());
                    }
                }
            }
        });


        myrecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), myrecyclerView, this));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //linearLayoutManager.setReverseLayout(true); altan başlatır
        myrecyclerView.setLayoutManager(linearLayoutManager);


        //farklı layoutlar kullanarak yanyana farklı şekilde de dizebiliriz item_car.xml'i
        /*GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity() , 3, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);*/

        //myList = ((MainActivity) getActivity()).getSetCarList(10);
        CarAdapter adapter = new CarAdapter(getActivity(), myList);
        //adapter.setMyrecyclerViewOnClickListenerInterface(this);
        myrecyclerView.setAdapter(adapter);
        setFloatingActionButton();
        return view;
    }
     public void setFloatingActionButton(){
        fabmenu = (FloatingActionMenu) getActivity().findViewById(R.id.fab_menu);
        fabmenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                Toast.makeText(getActivity(), "menu acıldı? " + (opened ? "true" : "false"), Toast.LENGTH_SHORT).show();
            }
        });
        fabmenu.showMenuButton(true);
        fabmenu.setClosedOnTouchOutside(true);

        FloatingActionButton fab1 = (FloatingActionButton) getActivity().findViewById(R.id.fab1);
        FloatingActionButton fab2 = (FloatingActionButton) getActivity().findViewById(R.id.fab2);
        FloatingActionButton fab3 = (FloatingActionButton) getActivity().findViewById(R.id.fab3);

        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);


    }
        // https://github.com/makovkastar/FloatingActionButton ' kütüphanesini kullanarakta bu şekilde oluşturma
        /*fab= (FloatingActionButton)getActivity().findViewById(R.id.fab_butonu);
        fab.attachToRecyclerView(recyclerView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
            }
            @Override
            public void onScrollUp() {

            }
        },new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        CarAdapter adapter = (CarAdapter) recyclerView.getAdapter();

                        if (myList.size() == linearLayoutManager.findLastCompletelyVisibleItemPosition()+1 ){
                            List<Car> List2aux =  ((MainActivity)getActivity()).getSetCarList(10);
                            for (int i =0 ; i<List2aux.size();i++){
                                adapter.addListItem(List2aux.get(i),myList.size());
                            }
                        }
                    }
                });*/



    @Override
    public void onClickListener(View view, int position) {

        Intent intent = new Intent(getActivity(), CarActivity.class);
        intent.putExtra("myCar",myList.get( position ));
        getActivity().startActivity(intent);

        // Toast.makeText(getActivity(),"onClickListener: "  +position,Toast.LENGTH_LONG).show();
        /*CarAdapter adapter = (CarAdapter) recyclerView.getAdapter();
        adapter.removeListItem(position); */
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        Toast.makeText(getActivity(),"onLongPressClickListener: " +position,Toast.LENGTH_LONG).show();
        /*CarAdapter adapter = (CarAdapter) recyclerView.getAdapter();
        adapter.removeListItem(position);*/
    }


    //RecyclerView deki itemleri dokunulduklarında gerekli itemlerı almak için kullanıyoruz.
    //kısa tık uzun tıkta farklı işlemler uygulamak içinde kullandık.
    protected static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context myContext;
        //hareket detektkörü
        private GestureDetector myGestureDetector;
        private RecyclerViewOnClickListenerInterface myRecyclerViewOnClickListenerInterface;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerInterface rvocli){
            myContext = c;
            myRecyclerViewOnClickListenerInterface = rvocli;

            myGestureDetector = new GestureDetector(myContext,new GestureDetector.SimpleOnGestureListener(){

                //longpress için longpressclicklistener
                public void onLongPress(MotionEvent e){
                    super.onLongPress(e);
                    View view = rv.findChildViewUnder(e.getX(),e.getY());

                    if (view !=null && myRecyclerViewOnClickListenerInterface !=null){
                        myRecyclerViewOnClickListenerInterface.onLongPressClickListener(view , rv.getChildPosition(view));
                    }

                }

                // single için clicklistener
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View view = rv.findChildViewUnder(e.getX(),e.getY());

                    if (view !=null && myRecyclerViewOnClickListenerInterface !=null){
                        myRecyclerViewOnClickListenerInterface.onClickListener(view , rv.getChildPosition(view));
                    }
                    return (true);
                }
            });
        }
        //kesmek durdurmak
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            myGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
    }
    @Override
    public void onClick(View view) {
        String calısıyormu= "";
        switch (view.getId()){
            case R.id.fab1:
                calısıyormu= " fab1 calıstı";
            case R.id.fab2:
                calısıyormu= " fab2 calıstı";
            case R.id.fab3:
                calısıyormu= " fab3 calıstı";
                break;
        }
        Toast.makeText(getActivity(), calısıyormu ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("myList",(ArrayList<Car>) myList);
    }
}
