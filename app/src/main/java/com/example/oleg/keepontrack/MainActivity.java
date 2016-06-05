package com.example.oleg.keepontrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.oleg.keepontrack.adapter.UserListAdapter;
import com.example.oleg.keepontrack.database.SharedPreferencesDatabase;
import com.example.oleg.keepontrack.fragments.ChatFragment;
import com.example.oleg.keepontrack.fragments.CompanyStatisticFragment;
import com.example.oleg.keepontrack.fragments.EditProfileFragment;
import com.example.oleg.keepontrack.fragments.NotificationsFragment;
import com.example.oleg.keepontrack.model.NetworkAPI;
import com.example.oleg.keepontrack.pojo.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NetworkActivity {


    FloatingActionButton fab;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    View navHeader;
    RecyclerView usersList;
    SearchView searchView;

    UserListAdapter adapter;

     ChatFragment chatFragment;
    NotificationsFragment notificationsFragment;
    CompanyStatisticFragment companyStatisticFragment;
    public EditProfileFragment editProfileFragment;
    SharedPreferencesDatabase sharedPreferencesDatabase;

    NetworkAPI backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLogic();
        findViewsById();
        initViewsData();
        initUiListeners();
        initFragments();

        if (!getIntent().getBooleanExtra("openProfile", false)) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content_container, chatFragment)
                    .commit();
            chatFragment.setDirectChat(3);

        }
        else
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content_container, editProfileFragment)
                    .commit();


    }

    private void initLogic() {
        sharedPreferencesDatabase = new SharedPreferencesDatabase(this);
        backend = new Retrofit.Builder()
                .baseUrl(ApplicationConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkAPI.class);
    }

    private void initFragments() {
        chatFragment = new ChatFragment();
        companyStatisticFragment = new CompanyStatisticFragment();
        notificationsFragment = new NotificationsFragment();
        editProfileFragment = new EditProfileFragment();
    }

    private void initUiListeners() {
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.searchQuery(query);
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.searchQuery(newText);
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            adapter.searchQuery(null);
            return true;
        });

    }

    private void initViewsData() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        adapter = new UserListAdapter();
        adapter.setOnClick(v -> {
            drawer.closeDrawer(GravityCompat.START);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content_container, chatFragment)
                    .addToBackStack(null)
                    .commit();
            chatFragment.setDirectChat(Integer.parseInt(v.getTag().toString()));

        });

        usersList.setLayoutManager(new LinearLayoutManager(this));
        usersList.setAdapter(adapter);

        backend.getAllUsers(sharedPreferencesDatabase.getCurrentUser().getAccessToken()).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                adapter.setUser(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Snackbar.make(fab, "Cant load users: " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });

    }


    private void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
        usersList = (RecyclerView) navHeader.findViewById(R.id.userList);
        searchView = (SearchView) navHeader.findViewById(R.id.search_view);
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
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content_container, editProfileFragment)
                        .addToBackStack(null)
                        .commit();
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
                sharedPreferencesDatabase.clearData();
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

    @Override
    public NetworkAPI getBackend() {
        return backend;
    }
}
