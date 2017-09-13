package com.warden.mobilecampus.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.warden.mobilecampus.R;


import com.warden.mobilecampus.view.fragment.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewsFragment.OnFragmentInteractionListener,
        LibraryFragment.OnFragmentInteractionListener,
        ToolFragment.OnFragmentInteractionListener,
        NewsListFragment.OnFragmentInteractionListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private Unbinder unbinder;
    private int currentFragmentId = 0;
    private Fragment currentFragment;
    FragmentManager fragmentManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initView(savedInstanceState);
    }
    private void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        // 主页面默认添加NewsFragment
        fragmentManager = getSupportFragmentManager();
        toolbar.setTitle("新闻");
        navigationView.setCheckedItem(R.id.nav_camera);
        /**
         * 防止碎片重叠
         */
        Fragment newsFragment;
        Fragment libraryFragment;
        Fragment toolFragment;
        if (savedInstanceState != null) {
            currentFragment = fragmentManager.findFragmentByTag("news");
            libraryFragment = fragmentManager.findFragmentByTag("library");
            toolFragment = fragmentManager.findFragmentByTag("tools");
            fragmentManager.beginTransaction().show(currentFragment)
                    .hide(libraryFragment)
                    .hide(toolFragment)
                    .commit();
        }else {
            currentFragment = new NewsFragment();
            libraryFragment = new LibraryFragment();
            toolFragment = new ToolFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_content, currentFragment, "news")
                    .add(R.id.fragment_content, libraryFragment, "library")
                    .add(R.id.fragment_content,toolFragment,"tools")
                    .show(currentFragment)
                    .hide(libraryFragment)
                    .hide(toolFragment)
                    .commit();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        //navigationView listener
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_camera:
                switchFragment(0);
                return true;
            case R.id.nav_gallery:
                switchFragment(1);
                return true;
            case R.id.nav_manage:
                switchFragment(2);
                return true;
        }
        return false;
    }

    private void switchFragment(int fragmentId) {
        drawer.closeDrawer(GravityCompat.START);
        if (currentFragmentId == fragmentId)
            return;
        currentFragmentId = fragmentId;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment toFragment = null;
        switch (fragmentId) {
            case 0:
                toFragment = fragmentManager.findFragmentByTag("news");
                toolbar.setTitle("新闻资讯");
                break;
            case 1:
                toFragment = fragmentManager.findFragmentByTag("library");
                toolbar.setTitle("图书馆");
                break;
            case 2:
                toFragment = fragmentManager.findFragmentByTag("tools");
                toolbar.setTitle("tools");
                break;
        }
        fragmentTransaction.hide(currentFragment).show(toFragment).commit();
        currentFragment = toFragment;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
