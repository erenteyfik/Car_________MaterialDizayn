package com.onepiece_eren.car;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Teyfik on 23.3.2017.
 */
public class SecondActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        mToolbar.setTitle("2 Activity");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inc_toolbar_bottom activity mainde include id'si toolbar_bottom yaparsan gg^^
        mToolbarBottom = (Toolbar) findViewById(R.id.inc_toolbar_bottom);
        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent intent = null;

                switch (menuItem.getItemId()){
                    case R.id.action_facebook:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.facebook.com/"));
                        break;
                    case R.id.action_twitter:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.twitter.com/"));
                        break;
                    case R.id.action_youtube:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.youtube.com/"));
                        break;
                    case R.id.action_google_plus:
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://www.google.com"));
                        break;
                }

                startActivity(intent);
                return true;
            }
        });
        mToolbarBottom.inflateMenu(R.menu.menu_bottom);

        mToolbarBottom.findViewById(R.id.iv_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Settings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){

            mToolbar.setBackgroundResource(R.drawable.toolbar_r_corners);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home){
            finish();
        }
        return true;
    }
}
