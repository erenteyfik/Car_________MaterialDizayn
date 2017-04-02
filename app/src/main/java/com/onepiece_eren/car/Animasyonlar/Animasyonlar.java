package com.onepiece_eren.car.Animasyonlar;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.onepiece_eren.car.adapters.CarAdapter;

/**
 * Created by Teyfik on 25.3.2017.
 */
public class Animasyonlar {

    public static void itemAnimasyonu (View v, int i){

        switch (i){

            case 0:
                YoYo.with(Techniques.BounceIn)
                        .duration(700)
                        //.repeat(5)
                        .playOn(v);
            case 1:
                YoYo.with(Techniques.DropOut)
                        .duration(700)
                        //.repeat(5)
                        .playOn(v);
            case 2:
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        //.repeat(5)
                        .playOn(v);
            case 3:
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        //.repeat(5)
                        .playOn(v);
            case 4:
                YoYo.with(Techniques.Flash)
                        .duration(700)
                        //.repeat(5)
                        .playOn(v);

        }


    }
}
