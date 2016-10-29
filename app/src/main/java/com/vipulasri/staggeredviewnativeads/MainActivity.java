package com.vipulasri.staggeredviewnativeads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Test unit ids for Advanced Native Ads as currently its in beta
    public static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/3986624511";
    private static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

    public static final int ITEMS_PER_AD = 8;


    RecyclerView recyclerView;
    private ArtsAdapter artsAdapter;
    private List<Object> mArts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fresco.initialize(this);
        MobileAds.initialize(this, ADMOB_APP_ID);

        int columnCount = getResources().getInteger(R.integer.arts_columns);
        int spacing = Utils.dpToPx(5, this); // 50px

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacing));
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        addDummyArtData();
        addDummyArtData();
        addNativeAppInstallAds();

        artsAdapter = new ArtsAdapter(mArts);
        recyclerView.setAdapter(artsAdapter);
    }

    private void addDummyArtData(){
        mArts.add(new Artwork("The Windmill at Wijk bij Duurstede", "http://lh6.ggpht.com/1gH99j2GD85SW4r3CA18uwTDuRioMYTNZlH5N2xuZsbh_4QUnzxettm6WqCsLa_ciGCzhWwLzF35QtHEpz4M9LWv_yvl=s0", 2880, 2376));
        mArts.add(new Artwork("Winter Landscape with Ice Skaters", "http://lh5.ggpht.com/fnW9Pyd4QvVnz1NmbiU2j1v5gRHgtmjEcmNGAhgqJ0nD9oQSmi8Iddje-Da6ozlYLI9QP32OASS7A3qT9izEYTmcDA=s0", 2898, 1691));
        mArts.add(new Artwork("Floral Still Life", "http://lh4.ggpht.com/y-B7FwRdKdFzKCrrAzgvnGOW1e1-z0w2sfJqjVWuqDt_Du6s8q5Y2_bAyjk_SVTPDWb9ZLqwMCq3FemdsGHJkf7tdC46=s0", 1904, 2424));
        mArts.add(new Artwork("Portraits of Giuliano and Francesco Giamberti da Sangallo", "http://lh4.ggpht.com/NwCWmjro4h__Ord5RqicIJsJbTY104UditPHR-swB9a7pQRt67KfneX_tBEazLnkNGsWqCvfsZam8Pxj1Ixiqbne7Q=s0", 2143, 1520));
        mArts.add(new Artwork("Night Watch","http://lh6.ggpht.com/ZYWwML8mVFonXzbmg2rQBulNuCSr3rAaf5ppNcUc2Id8qXqudDL1NSYxaqjEXyDLSbeNFzOHRu0H7rbIws0Js4d7s_M=s0", 2500, 2034));
        mArts.add(new Artwork("The Jewish Bride", "http://lh5.ggpht.com/H-KfOaNgW2an_g0kODWKua5BELckMTr7zauQZCbnOZ69fyNlr67uavKaDmvSawg8j6TB88abmtAjNbcMjbOdU94zuzM=s0", 3000, 2196));
        mArts.add(new Artwork("Portrait of Don Ramón Satué", "http://lh4.ggpht.com/wyy5JOPbVx1wQ9ax57OmfOz4kooWGwaW7iX7OS5VkveFRTxLQ6J73t4bunTkrerA9nvlgUIyutax_0KzR9kbhKOesMY=s0", 2131, 2708));
        mArts.add(new Artwork("Worship of the Golden Calf", "http://lh5.ggpht.com/s6sGZ2OFkUPvX83i1JwioP0RckwCQfI2xXIjgcI1V5WlEbTXfFQvcr97gFMVwFqJNofPycMeB1u5Wwo6CJ8y1pVenLGZ=s0", 3000, 2255));
        mArts.add(new Artwork("Ten weepers from the tomb of Isabella of Bourbon", "http://lh5.ggpht.com/EHhJDrv4IB_89m9w9VlcYRYHYOuvU72iwD11oZ1HL3J5QcCMfmAD48CVxAtUwts9RT55W4lWSPI19wb1lSRZ9zecKMA=s0", 1656, 2500));
        mArts.add(new Artwork("Portrait of a Girl Dressed in Blue", "http://lh6.ggpht.com/gOTbLnfHUVFp3PgQQSNiEmQ0fjVAPCNJbO8ofTXlFJMpUWDye9ernn75qmkGj8KqAQTr60cyOHiXK3LnWwhwvc1mGQ=s0", 2190, 2699));
        mArts.add(new Artwork("River Landscape with Riders", "http://lh4.ggpht.com/1OOoY0qISJncBpdzHk1dKTkNcmXJndXA7kfUMrm9B_-g57lGyb-eEjl_BCQzdmsthkk7HS93eSpHx_l_P_sFXoEDKeo=s0", 2500, 1402));
        mArts.add(new Artwork("The Fête champêtre", "http://lh4.ggpht.com/aSyhtY1rLfuYrMOVhEuvUua1WDwph30DIs4nJc6ACRpAM-avOaQHb9lmdqNsSWq1H-3rCEjBFlozD3f50b3Qf_XFLaaK=s0", 2946, 1656));
    }

    private void addNativeAppInstallAds() {

        // Loop through the items array and place a new Native Express ad in every ith position in
        // the items List.,
        for (int i = 0; i <= mArts.size(); i += ITEMS_PER_AD) {
            final NativeAppInstallAdView adView = new NativeAppInstallAdView(this);
            mArts.add(i, adView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
