package com.example.task.ui.subTaskStaff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task.Adapter.SubTaskAdapter;
import com.example.task.Model.Staff.SubTask;
import com.example.task.R;
import com.example.task.helpers.SessionManager;
import com.example.task.helpers.UrlHelper;
import com.google.gson.Gson;

public class SubTaskStaffFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SessionManager sessionManager;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    SubTaskAdapter adapter;
    CoordinatorLayout pbLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sub_task_staff, container, false);
        sessionManager = new SessionManager(getActivity());
        rv = root.findViewById(R.id.sub_task_recyclerView);
        pbLayout = root.findViewById(R.id.news_pblayout);
        swipeRefreshLayout = root.findViewById(R.id.sub_task_swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryColor,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        requestSoal();
        return root;
    }


    /**
     * Called when a swipe gesture triggers a refresh.
     */
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
            SubTask subTask = g.fromJson(response, SubTask.class);
            if(subTask.getSubTask().size() > 0){
                Toast.makeText(getActivity(), "Loading Complete", Toast.LENGTH_SHORT).show();
                adapter = new SubTaskAdapter(getActivity(), subTask.getSubTask());
                rv.setAdapter(adapter);
                pbLayout.setVisibility(View.GONE);
            }
        }, error -> {
            System.out.println(error);
            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }
}