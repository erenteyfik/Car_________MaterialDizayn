package com.onepiece_eren.car;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.List;

import com.onepiece_eren.car.Animasyonlar.Animasyonlar;
import com.onepiece_eren.car.domain.Car;
import com.onepiece_eren.car.domain.Person;
import com.onepiece_eren.car.fragments.CarFragment;
import com.onepiece_eren.car.fragments.KamyonetCarFragment;
import com.onepiece_eren.car.fragments.LuxCarFragment;
import com.onepiece_eren.car.fragments.PopularCarFragment;
import com.onepiece_eren.car.fragments.SporCarFragment;






public class MainActivity extends ActionBarActivity {
    private static String TAG = "LOG";

    private Toolbar mToolbar;
    private Drawer.Result SolNavigation;
    private Drawer.Result SagNavigation;
    private AccountHeader.Result headerNavigationSol;
    private FloatingActionMenu fabmenu;
    private int myItemDrawerSelected;
    private int myProfileDrawerSelected;


    private List<PrimaryDrawerItem> listCategory;
    private List<Person> listProfil;
    private List<Car> listCars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            myItemDrawerSelected = savedInstanceState.getInt("myItemDrawerSelected",0);
            myProfileDrawerSelected = savedInstanceState.getInt("myProfileDrawerSelected", 0);
            listCars = savedInstanceState.getParcelableArrayList("listCars");
        }else{
            listCars = getSetCarList(50);
        }

        //floating action buton
        fabmenu = (FloatingActionMenu) findViewById(R.id.fab_menu);

        //TOOLBAR
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("ArabaWiki");
        //mToolbar.setSubtitle("altyazi");
        //mToolbar.setLogo(R.drawable.arabaikon);
        setSupportActionBar(mToolbar);

        /* ************************************************
        //hamburger menude geri okla işlem yapmamızı saglar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        ************************************************ */


        //Fragment için
        Fragment fragment  = getSupportFragmentManager().findFragmentByTag("mainfrag");
        if(fragment == null) {
            fragment = new CarFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container,fragment,"mainfrag");
            ft.commit();
        }

        // Navigation Drawer LEFT

            //HEADER
            headerNavigationSol = new AccountHeader()
                    .withActivity(this)
                    .withCompactStyle(false)
                    .withSavedInstance(savedInstanceState)
                    .withThreeSmallProfileImages(true)
                    //.withHeaderBackground(R.drawable.headerarkaplan)
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                            Person aux = getPersonByEmail(listProfil, (ProfileDrawerItem) profile);
                            myProfileDrawerSelected = getPersonPositionByEmail(listProfil, (ProfileDrawerItem) profile);
                            headerNavigationSol.setBackgroundRes(aux.getBackground());
                            return true;

                        }
                    })
                    .build();

                    listProfil = getSetProfileList();
                    if(listProfil != null && listProfil.size() >0 ){
                        if(myProfileDrawerSelected !=0){
                            Person person= listProfil.get(myProfileDrawerSelected);
                            listProfil.set(myProfileDrawerSelected,listProfil.get(0));
                            listProfil.set(0,person);
                        }
                        for (int i =0; i<listProfil.size(); i++){
                            headerNavigationSol.addProfile(listProfil.get(i).getProfile(),i);
                        }
                        headerNavigationSol.setBackgroundRes(listProfil.get(0).getBackground());
                    }


            //Body
            SolNavigation = new Drawer()
                    .withActivity(this)
                    .withToolbar(mToolbar)
                    .withDisplayBelowToolbar(false)
                    .withActionBarDrawerToggle(true) //ok işareti gelir
                    //.withShowDrawerOnFirstLaunch(true) hambuerger menu acık şekilde acılır apk
                    .withDrawerWidthDp(250)
                    .withActionBarDrawerToggleAnimated(true)
                    .withDrawerGravity(Gravity.START)
                    //.withSliderBackgroundDrawableRes(R.drawable.blue)  //background
                    .withSavedInstance(savedInstanceState)
                    //.withSelectedItem(0)
                    .withActionBarDrawerToggle(true)
                    .withAccountHeader(headerNavigationSol)

                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem drawerItem) {
                            Fragment frag=null;
                            myItemDrawerSelected=i;

                            if(i==0){
                                frag= new CarFragment();
                            }else if(i==1){
                                frag= new LuxCarFragment();
                            }else if(i==2){
                                frag= new SporCarFragment();
                            }else if(i==3){
                                frag= new KamyonetCarFragment();
                            }else if(i==4){
                                frag= new PopularCarFragment();
                            }

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.rl_fragment_container,frag,"mainFrag");
                            ft.commit();


                            mToolbar.setTitle(((PrimaryDrawerItem ) drawerItem).getName() );
                        }
                    })
                    .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem drawerItem) {
                            Toast.makeText(MainActivity.this, "onItemLongClick:  "+position ,Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                    //.withSliderBackgroundColor()  background rengi burda ayarlıyoruz.
                    .build();

        listCategory = getSetCategoryList();
        if(listCategory !=null && listCategory.size()>0){
            for (int i=0; i<listCategory.size();i++){
                SolNavigation.addItem(listCategory.get(i));
            }
            SolNavigation.setSelection(myItemDrawerSelected);
        }


        /*
        SolNavigation.addItem(new PrimaryDrawerItem().withName("spor arabalar").withIcon(getResources().getDrawable(R.drawable.spor_araba)));
        SolNavigation.addItem(new PrimaryDrawerItem().withName("lüx arabalar").withIcon(R.drawable.lux_araba));
        //SolNavigation.addItem(new DividerDrawerItem()); // ekranı böler bir cizgi koyar
        SolNavigation.addItem(new PrimaryDrawerItem().withName("klasik arabalar").withIcon(R.drawable.klasik_araba));
        SolNavigation.addItem(new PrimaryDrawerItem().withName("kamyonetler").withIcon(R.drawable.kamyonet_araba));
        SolNavigation.addItem(new SectionDrawerItem().withName("Yapılandırmalar"));
        SolNavigation.addItem(new SwitchDrawerItem().withName("Navigasyon").withChecked(true).withOnCheckedChangeListener(myOnCheckedChangeListener));
        SolNavigation.addItem(new ToggleDrawerItem().withName("Parlaklık").withChecked(true).withOnCheckedChangeListener(myOnCheckedChangeListener));
        */

        // EXTRA END-RIGHT için
        SagNavigation = new Drawer()
                .withActivity(this)
                //.withToolbar(mToolbar)
                //.withDisplayBelowToolbar(true)
                //.withDisplayBelowStatusBar(true)
                .addDrawerItems(
                        new SecondaryDrawerItem().withName("spor arabalar").withIcon(getResources().getDrawable(R.drawable.spor_araba)),
                        new SecondaryDrawerItem().withName("lüx arabalar").withIcon(R.drawable.lux_araba),
                        new SecondaryDrawerItem().withName("klasik arabalar").withIcon(R.drawable.klasik_araba),
                        new SecondaryDrawerItem().withName("kamyonetler").withIcon(R.drawable.kamyonet_araba)
                )
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.END)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem drawerItem) {
                        Toast.makeText(getApplication(), "onItemClick:  "+position ,Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l, IDrawerItem drawerItem) {
                        Toast.makeText(getApplication(), "onItemLongClick:  "+position ,Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

    }

    //Category
    private List<PrimaryDrawerItem> getSetCategoryList(){
        String[] names = new String[]{"spor arabalar","lüx arabalar","klasik arabalar","kamyonetler"};
        int[] icons = new int[]{R.drawable.spor_araba_2, R.drawable.lux_araba, R.drawable.klasik_araba, R.drawable.kamyonet_araba};
        int[] iconsSelected = new int[]{R.drawable.spor_araba_mavi, R.drawable.lux_araba, R.drawable.klasik_araba, R.drawable.kamyonet_araba};
        // tıklandıgında ikonları mavi renklerini göster..
        List<PrimaryDrawerItem> list = new ArrayList<>();

        for(int i=0; i<names.length; i++){
            PrimaryDrawerItem pDi = new PrimaryDrawerItem();
            pDi.setName(names[i]);
            pDi.getLayoutRes();
            pDi.setIcon(getResources().getDrawable(icons[i]));
            pDi.setTextColor(getResources().getColor(R.color.colorPrimarytext2));
            pDi.setSelectedIcon(getResources().getDrawable((iconsSelected[i])));
            pDi.setSelectedTextColor(getResources().getColor(R.color.colorPrimarytext));

            list.add(pDi);
        }
        return (list);
    }

    //Person
    private Person getPersonByEmail(List<Person> list, ProfileDrawerItem p){
        Person email = null;
        for (int i =0; i<list.size();i++){
            //equalsIgnoreCase
            if(list.get(i).getProfile().getEmail().equalsIgnoreCase(p.getEmail())){
                email = list.get(i);
                break;
            }
        }
        return email;
    }
    private List<Person> getSetProfileList(){
        String[] names = new String[]{"User_1", "User_2","User_3"};
        String[] emails = new String[]{"emailUser_1_@gmail.com", "emailUser_2_@gmail.com","emailUser_3_@gmail.com"};
        int[] photos = new int[]{R.drawable.profil1, R.drawable.profil2,R.drawable.profil3};
        int[] background = new int[]{R.drawable.gallardo, R.drawable.vyron, R.drawable.bmw_720};
        List<Person> list = new ArrayList<>();

        for(int i = 0; i < names.length; i++){
            ProfileDrawerItem aux = new ProfileDrawerItem();
            aux.setName(names[i]);
            aux.setEmail(emails[i]);
            aux.setIcon(getResources().getDrawable(photos[i]));

            Person p = new Person();
            p.setProfile(aux);
            p.setBackground(background[i]);

            list.add( p );
        }
        return(list);
    }

    private int getPersonPositionByEmail( List<Person> list, ProfileDrawerItem p ){
        for(int i = 0; i < list.size(); i++){
            if( list.get(i).getProfile().getEmail().equalsIgnoreCase( p.getEmail() ) ){
                return(i);
            }
        }
        return( -1 );
    }



    //Car
    public List <Car> getSetCarList(int qtd){
        return (getSetCarList(qtd,0));
    }

    public List <Car> getSetCarList(int qtd,int categoryyyyy){
        String[] models = new String[] {"Vosvos","Minicooper","Lombarghini","Kamyonet","Cadillac"};
        String[] brands = new String[] {"Volkswagen","MINI","LOMBARGHINI","KamyoneT","Ct6"};
        int[] category = new int[]{4,1,2,3,1};
        int[] fotolar = new int[] {R.drawable.vosvos,R.drawable.bmw_720 ,R.drawable.lombarghini,R.drawable.kamyonet,R.drawable.ct6};
        String description = "-----------açıklama--------------";
        List<Car> ListArabalar = new ArrayList<>();

        for (int i=0 ;i<qtd ; i++ ){
            Car c  = new Car( models[i % models.length], brands[ i % brands.length], fotolar[i % models.length] );
            c.setDescription( description );
            c.setCategory(category[i % brands.length ]);
            c.setTel("5553301021");

            if (categoryyyyy !=0 && c.getCategory()!= categoryyyyy){
                continue;
            }
            ListArabalar.add(c);
        }
        return (ListArabalar);
    }
    public List<Car> getCarsByCategory(int category){
        List<Car> list = new ArrayList<>();
        for(int i=0 ; i<listCars.size() ;i++){
            if(category!=0 && listCars.get(i).getCategory()!=category){
                continue;
            }
            list.add(listCars.get(i));
        }
        return (list);
    }
    public List<Car> getListCars(){
        return (listCars);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("myItemDrawerSelected",myItemDrawerSelected);
        outState.putInt("myProfileDrawerSelected", myProfileDrawerSelected);
        outState.putParcelableArrayList("listCars",(ArrayList<Car>)listCars);
        outState = SolNavigation.saveInstanceState(outState);
        outState = SagNavigation.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }



    // sol ve sag navigasyonu backpress işlemi + fab_menu
    @Override
    public void onBackPressed() {
        if (SolNavigation != null && SolNavigation.isDrawerOpen() || SagNavigation != null && SagNavigation.isDrawerOpen()) {
            SolNavigation.closeDrawer();
            SagNavigation.closeDrawer();
        } else if (fabmenu.isOpened()){
            fabmenu.close(true);
        }
        else {
            super.onBackPressed();
        }
    }
    // setting 3 nokta ... xD ordan tıklayarak 2. sayfaya geciyoruz.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.action_settings ){
            startActivity(new Intent(this, SecondActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


}
