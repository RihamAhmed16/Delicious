package com.example.delicious;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delicious.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;

public class MainUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_fav,R.id.nav_MyRecipes)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        TextView nav_username = headerView.findViewById(R.id.nav_username);
        nav_username.setText("welome: "+ LoginActivity.name);
        //Menu nav_Menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(this);

            /*public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int nav_id=item.getItemId();
                if (nav_id==R.id.nav_home) {
                    startActivity(new Intent(MainUserActivity.this, HomeFragment.class));
                }
                else if (nav_id==R.id.nav_fav) {
                    startActivity(new Intent(MainUserActivity.this, FavouriteActivity.class));
                }
                else if (nav_id==R.id.nav_MyRecipes) {
                    startActivity(new Intent(MainUserActivity.this,ClientRecipesActivity.class));
                }
                else {
                    Toast.makeText(MainUserActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                }

                NavigationUI.onNavDestinationSelected(item,navController);


                drawer.closeDrawer(GravityCompat.START);
                return true;
            }*/

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int nav_id=item.getItemId();
        if (nav_id==R.id.nav_home) {
            startActivity(new Intent(MainUserActivity.this, HomeFragment.class));
        }
        else if (nav_id==R.id.nav_fav) {
            //startActivity(new Intent(MainUserActivity.this, FavouriteActivity.class));
            Toast.makeText(this, "hello fav", Toast.LENGTH_SHORT).show();
        }
        else if (nav_id==R.id.nav_MyRecipes) {
            startActivity(new Intent(MainUserActivity.this,ClientRecipesActivity.class));
        }
        else {
            Toast.makeText(MainUserActivity.this, "Empty", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_user, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_logout)
        {
            getSharedPreferences("Shlogin",MODE_PRIVATE)
                    .edit()
                    .clear()
                    .commit();
            startActivity(new Intent(MainUserActivity.this,LoginActivity.class));
        }
        if(id==R.id.action_delete)
        {
            Database db=new Database();
            db.ConnectDB();
            String ms=db.RUNDML("delete from  Client where client_no='"+LoginActivity.id+"'");
            if(ms.equals("Ok")){
                getSharedPreferences("Shlogin",MODE_PRIVATE)
                        .edit()
                        .clear()
                        .commit();
                startActivity(new Intent(MainUserActivity.this,LoginActivity.class));
            }
        }
        if(id==R.id.action_profile)
        {
            startActivity(new Intent(MainUserActivity.this,MyProfileActivity.class));
        }
        if(id==R.id.action_add)
        {
            startActivity(new Intent(MainUserActivity.this,AddActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                    }
                }).create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finishAffinity();
                        }
                    }).create().show();
        }
        return true;
    }

}