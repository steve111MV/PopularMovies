/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cg.stevendende.popularmovies.R;

/**
 * This Activity is the main entrance of The Application.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MainActivityFragment())
                .commit();

    }

}