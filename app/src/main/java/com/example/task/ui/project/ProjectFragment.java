package com.example.task.ui.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task.Adapter.ProjectAdapter;
import com.example.task.Model.Another.Example;
import com.example.task.R;
import com.example.task.helpers.SessionManager;
import com.example.task.helpers.UrlHelper;
import com.google.gson.Gson;

public class ProjectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SessionManager sessionManager;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    ProjectAdapter adapter;
    CoordinatorLayout pbLayout;
    LinearLayout dataTidakAda;
    FragmentActivity main;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_project, container, false);
        sessionManager = new SessionManager(getActivity());
        rv = root.findViewById(R.id.project_recyclerView);
        pbLayout = root.findViewById(R.id.project_pblayout);
        swipeRefreshLayout = root.findViewById(R.id.project_swipeRefresh);
        dataTidakAda = root.findViewById(R.id.project_dataTidakAda);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryColor,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        main = getActivity();
        requestSoal();
        return root;
    }

    @Override
    public void onRefresh() {
        requestSoal();
        swipeRefreshLayout.setRefreshing(false);
    }

    public  void requestSoal(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String param_role_id = sessionManager.getUserDetail().get("role");
        String param_user_id = sessionManager.getUserDetail().get("id_user");
        String url_target = UrlHelper.base_task+"?role_id="+param_role_id+"&user_id="+param_user_id;
        StringRequest request = new StringRequest(Request.Method.GET, url_target, response -> {
            System.out.println(response);
            Gson g = new Gson();
            Example subTask = g.fromJson(response, Example.class);
            if(subTask.getProject().size() > 0){
                Toast.makeText(main, "Loading Complete", Toast.LENGTH_SHORT).show();
                adapter = new ProjectAdapter(main, subTask.getProject());
                rv.setAdapter(adapter);
                pbLayout.setVisibility(View.GONE);
            }else{
                dataTidakAda.setVisibility(View.VISIBLE);
                pbLayout.setVisibility(View.GONE);
            }
        }, error -> {
            System.out.println(error);
            Toast.makeText(main, error.toString(), Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }
}