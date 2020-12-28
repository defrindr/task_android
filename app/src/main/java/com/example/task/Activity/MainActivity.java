package com.example.task.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task.Adapter.SubTaskAdapter;
import com.example.task.Model.SubTask;
import com.example.task.Model.SubTask_;
import com.example.task.R;
import com.example.task.helpers.Constant;
import com.example.task.helpers.SessionManager;
import com.example.task.helpers.UrlHelper;
import com.example.task.helpers.Utils;
import com.example.task.ui.DetailSubTask.DetailSubTaskFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInternetConnection();
        sessionManager = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logout();
            return true;
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_sub_task_complete)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment f = this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if(f instanceof DetailSubTaskFragment)
            // do something with f
            ((DetailSubTaskFragment) f).close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void checkRole(){
        HashMap<String, String> detail_user = sessionManager.getUserDetail();
        String role = detail_user.get("role");
        if(role != null){
            if(role.equals(Constant.ROLE_STAFF)){

            }else if(role.equals(Constant.ROLE_MANAGER)){

            }else if(role.equals(Constant.ROLE_DIRECTOR)){

            }else if(role.equals(Constant.ROLE_CEO)){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public void logout(){
        if(sessionManager.is_login()){
            HashMap<String, String> detail_user = sessionManager.getUserDetail();
            String role = detail_user.get("role");
            if(role != null){
                sessionManager.logout();
                redirectLogin();
            }
        }else{
            System.out.println("Session Kosong ");
            redirectLogin();
        }
        finish();
    }

    public void redirectLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Check internet connection
     */
    private void checkInternetConnection(){
        if(Utils.isNetworkAvailable(getApplication())){
            AlertDialog alert = new AlertDialog
                    .Builder(this)
                    .setTitle(R.string.error_connection_internet)
                    .setMessage(R.string.error_connection_internet)
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setCancelable(false)
                    .setPositiveButton("OKE", (dialog, id) -> {
                        // jika tombol diklik, maka akan menutup activity ini
                        finish();
                    }).create();
            alert.show();
        }
    }
}