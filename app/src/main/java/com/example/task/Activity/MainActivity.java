package com.example.task.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.task.R;
import com.example.task.helpers.Constant;
import com.example.task.helpers.SessionManager;
import com.example.task.helpers.Utils;
import com.example.task.ui.DetailSubTask.DetailSubTaskFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import static android.app.PendingIntent.getActivity;
import static com.example.task.helpers.Constant.LIST_ROLE;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    SessionManager sessionManager;
    NavigationView navigationView;
    NavController navController;
    TextView txtUsername, txtRole;
    NavGraph navGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInternetConnection();
        sessionManager = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerNav = navigationView.getHeaderView(0);
        txtUsername = headerNav.findViewById(R.id.display_username);
        txtRole = headerNav.findViewById(R.id.display_role);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logout();
            return true;
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_sub_task, R.id.nav_project, R.id.nav_slideshow,  R.id.nav_sub_task_complete)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
        checkRole();
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
        String username = detail_user.get("username");
        String role = detail_user.get("role");
        if(role != null){
            txtUsername.setText(username);
            txtRole.setText(LIST_ROLE.get(parseInt(role)));
            if(role.equals(Constant.ROLE_STAFF)){
                navGraph.setStartDestination(R.id.nav_sub_task);
                navigationView.getMenu().findItem(R.id.nav_project).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_slideshow).setVisible(false);
            }else if(role.equals(Constant.ROLE_MANAGER)){
                navGraph.setStartDestination(R.id.nav_project);
                navigationView.getMenu().findItem(R.id.nav_sub_task).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_sub_task_complete).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_slideshow).setVisible(false);
            }else if(role.equals(Constant.ROLE_DIRECTOR)){
                navGraph.setStartDestination(R.id.nav_project);
                navigationView.getMenu().findItem(R.id.nav_sub_task).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_sub_task_complete).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_slideshow).setVisible(false);
            }else if(role.equals(Constant.ROLE_CEO)){
                navGraph.setStartDestination(R.id.nav_project);
                navigationView.getMenu().findItem(R.id.nav_sub_task).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_sub_task_complete).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_slideshow).setVisible(false);
            }
            navController.setGraph(navGraph);
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