package com.example.task.Activity;

import android.content.Intent;
import android.os.Bundle;

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

        sessionManager = new SessionManager(getApplicationContext());
        checkRole();

        final TextView textView = findViewById(R.id.login_page_title);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                auth_user(usernameEditText.getText().toString(), passwordEditText.getText().toString(), loadingProgressBar);
            }
        });


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


    public void auth_user(String username,String password, ProgressBar loadingProgressBar){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest req = new StringRequest(Request.Method.POST, UrlHelper.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success") == false){
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else{
                        JSONObject data_user = jsonObject.getJSONObject("data");
                        sessionManager.createSession(data_user.getString("id"),
                                data_user.getString("username"),
                                data_user.getString("role_id"));
                        checkRole();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };


        queue.add(req);
    }

}