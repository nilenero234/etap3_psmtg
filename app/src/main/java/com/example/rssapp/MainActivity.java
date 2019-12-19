package com.example.rssapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayoutCategories;
    TabLayout tabLayoutDays;
    String category = "Films";
    String day = "Pn,";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayoutCategories = findViewById(R.id.categoryTabs);
        tabLayoutDays = findViewById(R.id.daysTabs);
        textView = findViewById(R.id.textOutput);
        textView.setMovementMethod(new ScrollingMovementMethod());

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tabLayoutCategories.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        category = "Films";
                        Toast.makeText(MainActivity.this, "Selected: Films",
                                Toast.LENGTH_SHORT).show();
                        //textView.setText(readRSS("https://teletydzien.interia.pl/hity/filmy/feed"));

                        break;
                    case 1:
                        category = "Hits";
                        Toast.makeText(MainActivity.this, "Selected: Hits",
                                Toast.LENGTH_SHORT).show();
                        //textView.setText(readRSS("https://teletydzien.interia.pl/hity/filmy/feed"));
                        break;
                    case 2:
                        category = "Children";
                        Toast.makeText(MainActivity.this, "Selected: Children",
                                Toast.LENGTH_SHORT).show();
                        //textView.setText(readRSS("https://teletydzien.interia.pl/hity/filmy/feed"));
                        break;
                    case 3:
                        category = "Documentary";
                        Toast.makeText(MainActivity.this, "Selected: Documentary",
                                Toast.LENGTH_SHORT).show();
                        //textView.setText(readRSS("https://teletydzien.interia.pl/hity/filmy/feed"));
                        break;
                    case 4:
                        category = "Entertainment";
                        Toast.makeText(MainActivity.this, "Selected: Entertainment",
                                Toast.LENGTH_SHORT).show();
                        //textView.setText(readRSS("https://teletydzien.interia.pl/hity/filmy/feed"));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //textView.setText("Przestrzeń dla rss feed.");

        tabLayoutCategories.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        day = "dzisiaj";
                        break;
                    case 1:
                        day = "jutro";
                        break;
                    case 2:
                        day = "Pn,";
                        break;
                    case 3:
                        day = "Wt,";
                        break;
                    case 4:
                        day = "Śr,";
                        break;
                    case 5:
                        day = "Cz,";
                        break;
                    case 6:
                        day = "Pt";
                        break;
                    case 7:
                        day = "Sb,";
                        break;
                    case 8:
                        day = "Nd,";
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public String readRSS(String urlAddress){      // Metod służąca zczytywaniu kanału rss, obecnie jeszcze nie działa jak należy :(
        try{
            URL rrsUrl = new URL(urlAddress);
            HttpsURLConnection  urlConnection = (HttpsURLConnection) rrsUrl.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //BufferedReader in = new BufferedReader(new InputStreamReader(rrsUrl.openStream()));
            String message="";
            String line;

            while((line=bufferedReader.readLine())!=null){
                if(line.contains(day+" ")) {
                    if(line.contains("<title>")){
                        if(line.contains("[CDATA[")){
                            int oindex = line.lastIndexOf("[");

                            int eindex =line.indexOf("]");
                            String temp =line.substring(oindex, eindex);
                            message+=temp+"\n";
                        }
                    }
                }
            }
            bufferedReader.close();
            urlConnection.disconnect();
            return message;
        }
        catch(MalformedURLException ue){
        }
        catch(IOException ioe){
        }
        return null;
    }

}
