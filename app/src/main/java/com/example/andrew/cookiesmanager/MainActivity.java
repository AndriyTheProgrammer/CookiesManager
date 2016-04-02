package com.example.andrew.cookiesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.andrew.cookiesmanager.adapter.UserListAdapter;
import com.example.andrew.cookiesmanager.fragments.ChatFragment;
import com.example.andrew.cookiesmanager.fragments.CompanyStatisticFragment;
import com.example.andrew.cookiesmanager.fragments.NotificationsFragment;
import com.example.andrew.cookiesmanager.pojo.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FloatingActionButton fab;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    RecyclerView usersList;

    ChatFragment chatFragment;
    NotificationsFragment notificationsFragment;
    CompanyStatisticFragment companyStatisticFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        initViewsData();
        initUiListeners();
        initFragments();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content_container, chatFragment)
                .commit();


    }

    private void initFragments() {
        chatFragment = new ChatFragment();
        companyStatisticFragment = new CompanyStatisticFragment();
        notificationsFragment = new NotificationsFragment();
    }

    private void initUiListeners() {
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void initViewsData() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        UserListAdapter adapter = new UserListAdapter();
        adapter.setUser(generateMockUsers());
        usersList.setLayoutManager(new LinearLayoutManager(this));
        usersList.setAdapter(adapter);
    }

    private ArrayList<User> generateMockUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("User 1"));
        users.add(new User("User 2"));
        users.add(new User("Slackbot"));
        users.add(new User("someone.weird"));
        users.add(new User("User N"));
        return users;
    }

    private void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        usersList = (RecyclerView) navigationView.inflateHeaderView(R.layout.nav_header_main).findViewById(R.id.userList);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
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

        switch (id){
            case R.id.action_profile:
                break;
            case R.id.action_notifications:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content_container, notificationsFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_statistics:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content_container, companyStatisticFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.action_logout:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return true;
    }
}
