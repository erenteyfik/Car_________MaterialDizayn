package com.onepiece_eren.car;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.onepiece_eren.car.domain.Car;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Teyfik on 28.3.2017.
 */
public class CarActivity extends AppCompatActivity {
    private Toolbar myToolBar;
    private Car myCar;
    private Drawer.Result SolNavigationDrawer;
    private MaterialDialog myMaterialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);


        if (savedInstanceState != null) {
            myCar = savedInstanceState.getParcelable("myCar");
        } else {
            if(getIntent() !=null && getIntent().getExtras() !=null && getIntent().getExtras().getParcelable("myCar") != null ){
                myCar = getIntent().getExtras().getParcelable("myCar");
            }else{
                Toast.makeText(this,"Fail!",Toast.LENGTH_SHORT).show();
            }
        }

        myToolBar = (Toolbar) findViewById(R.id.tb_main);
        myToolBar.setTitle(myCar.getModel());
        setSupportActionBar(myToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        ImageView ivCar = (ImageView) findViewById(R.id.iv_car);
        TextView tvModel = (TextView) findViewById(R.id.tv_model);
        TextView tvMarka = (TextView) findViewById(R.id.tv_marka);
        TextView tvDescription = (TextView) findViewById(R.id.tv_description);
        Button btPhone = (Button) findViewById(R.id.btn_phone);

        btPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMaterialDialog = new MaterialDialog( new ContextThemeWrapper( CarActivity.this, R.style.MyAlertDialog ) )
                        .setTitle( "Telefon")
                        .setMessage( myCar.getTel() )
                        .setPositiveButton("Ara", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent( Intent.ACTION_CALL );
                                it.setData( Uri.parse( "tel:"+ myCar.getTel().trim() ) );
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("Kapat", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myMaterialDialog.dismiss();
                            }
                        });
                myMaterialDialog.show();
            }
        });


        ivCar.setImageResource(myCar.getFoto());
        tvModel.setText(myCar.getModel());
        tvMarka.setText(myCar.getBrand());
        tvDescription.setText(myCar.getDescription());

        SolNavigationDrawer = new Drawer()
                .withActivity(this)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { return true; }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("myCar",myCar);
    }
}
