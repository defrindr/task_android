package com.example.task.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task.R;
import com.example.task.helpers.Constant;
import com.example.task.helpers.SessionManager;
import com.example.task.helpers.UrlHelper;
import com.example.task.helpers.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    SessionManager sessionManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkInternetConnection();
        sessionManager = new SessionManager(getApplicationContext());
        checkRole();

        final TextView textView = findViewById(R.id.login_page_title);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
                auth_user(usernameEditText.getText().toString(), passwordEditText.getText().toString(), instanceIdResult.getToken().toString(), loadingProgressBar);
            });
        });


    }
    public void checkRole(){
        HashMap<String, String> detail_user = sessionManager.getUserDetail();
        String role = detail_user.get("role");
        if(role != null){
            if(role.equals(Constant.ROLE_STAFF)){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }else if(role.equals(Constant.ROLE_MANAGER)){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }else if(role.equals(Constant.ROLE_DIRECTOR)){

            }else if(role.equals(Constant.ROLE_CEO)){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }


    public void auth_user(String username,String password,String fcm_token, ProgressBar loadingProgressBar){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest req = new StringRequest(Request.Method.POST, UrlHelper.login, response -> {
            JSONObject jsonObject = null;
            try {
//                Toast.makeText(getApplicationContext(), fcm_token, Toast.LENGTH_SHORT).show();
                jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("success") == false){
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }else{
                    JSONObject data_user = jsonObject.getJSONObject("data");
                    sessionManager.createSession(data_user.getString("id"),
                            data_user.getString("username"),
                            data_user.getString("role_id"),
                            fcm_token);
                    checkRole();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            loadingProgressBar.setVisibility(View.INVISIBLE);
        }, error -> {
            System.out.println(error);
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            loadingProgressBar.setVisibility(View.INVISIBLE);
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("fcm_token", fcm_token);
                return params;
            }
        };


        queue.add(req);
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