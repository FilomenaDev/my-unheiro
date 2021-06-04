package com.filomenadeveloper.myunheiro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.filomenadeveloper.myunheiro.ui.Menu.TablePrice_Activity;
import com.filomenadeveloper.myunheiro.ui.Profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
      //  Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        TextView email = findViewById(R.id.logaut);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer =  findViewById(R.id.drawer_layout);

        ImageView mDrawerButton = findViewById(R.id.drawerButton);
        DrawerLayout finalDrawer = drawer;
        mDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDrawer.openDrawer(Gravity.LEFT);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);
        getUserData();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
      //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

    }

    private void getUserData() {
        String driverId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference assignedCustomerRef = FirebaseDatabase.getInstance().getReference().child("User").child("customer").child(driverId);
        assignedCustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    NavigationView navigationView =  findViewById(R.id.nav_view1);
                    View header = navigationView.getHeaderView(0);
                    if (snapshot.child("name").getValue() != null) {
                        TextView mUsername = header.findViewById(R.id.usernameDrawer);
                        mUsername.setText(snapshot.child("name").getValue().toString());
                    }
                    if (snapshot.child("email").getValue() != null) {
                        TextView mUseremail = findViewById(R.id.logaut);
                        mUseremail.setText(snapshot.child("email").getValue().toString());
                    }
                    if (snapshot.child("profileImageUrl").getValue() != null) {
                        ImageView mProfileImage = header.findViewById(R.id.imageViewDrawer);
                        Glide.with(getApplication()).load(snapshot.child("profileImageUrl").getValue().toString()).apply(RequestOptions.circleCropTransform()).into(mProfileImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void logOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ActivityDrawer.this, Screen_Main.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        if(id ==R.id.nav_profile){
            intent = new Intent(ActivityDrawer.this, Profile.class);
            startActivity(intent);

        }else if(id == R.id.nav_message){
//   Intent intent = new Intent(ActivityDrawer.this,MainActivity.class);
            //   startActivity(intent);
        }else if(id == R.id.nav_gps){
           intent = new Intent(ActivityDrawer.this,activity_maps_customer.class);
          startActivity(intent);
        }else if(id == R.id.nav_tabelapreco){
            intent = new Intent(ActivityDrawer.this, TablePrice_Activity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}