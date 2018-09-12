package com.owaisnaat.appbits.owaisrazaqadri;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AudioStartPage extends AppCompatActivity {



    TextView more_app;
    LinearLayout shareit;
    ImageView fb_share;
    public int getTaskRunningNow() {
        return taskRunningNow;
    }

    public void setTaskRunningNow(int taskRunningNow) {
        this.taskRunningNow = taskRunningNow;
    }

    int taskRunningNow;
    boolean s_inter=true;
    ListView listData;
    SharedPreferences pref_back;
    SharedPreferences.Editor editor;

    private AdView dtAdViewmain;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_start_page);

        listData= findViewById(R.id.listData);




        pref_back = this.getSharedPreferences("OwaisSaveData", Context.MODE_PRIVATE);
        editor = pref_back.edit();

        Adapt_all adapter2 = new Adapt_all(AudioStartPage.this, getResources().getStringArray(R.array.allNameBang));
        listData.setAdapter(adapter2);

        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("posData", i);
                editor.commit();
                taskRunningNow = i;
                s_inter=false;
                Intent intent= new Intent(AudioStartPage.this,MainActivity.class);
                intent.putExtra("Position",i);
                startActivity(intent);

            }
        });

        rateTask();

        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstiial_ad_unit));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

            }
        });


        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s_inter) {

//                                        Log.e("eeeeeeeeee","see"+s_inter);
                            try {

                                mInterstitialAd.show();
                            }catch (Exception ec){
                                ec.printStackTrace();
                            }
                        }
                    }
                },1000);
            }
        });




        dtAdViewmain = (AdView) findViewById(R.id.dtadViewmain);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        dtAdViewmain.loadAd(adRequest);
    }

    private void requestNewInterstitial() {
        try {
            AdRequest adRequest = new AdRequest.Builder().build();

            mInterstitialAd.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rateTask(){


        LinearLayout rateus = (LinearLayout) findViewById(R.id.rateushome);
        more_app = (TextView) findViewById(R.id.more_apps);
        shareit = (LinearLayout) findViewById(R.id.share);
        fb_share = (ImageView) findViewById(R.id.fbI);


        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myappPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + myappPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + myappPackageName)));
                }
            }
        });

        more_app.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:AppBits")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=AppBits")));
                }


            }
        });
        shareit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String myappPackageName = getPackageName();

                try {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    String sAux = "https://play.google.com/store/apps/details?id=" + myappPackageName;
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Share via"));
                } catch (Exception e) {
                    //e.toString();
                }

            }
        });

        fb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1782714495291272")));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/1782714495291272")));
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.homeButton: {
                onBackPressed();
                return true;
            }
            case R.id.rate_us: {
                final String myappPackageName = this.getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + myappPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + myappPackageName)));
                }

                return true;
            }

            case R.id.newGoj: {
                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1782714495291272")));
                } catch (Exception e) {
                    startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/1782714495291272")));
                }

                return true;

            }




            default: {


                return super.onOptionsItemSelected(item);
            }
        }
    }

}
