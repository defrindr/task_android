package com.example.task.ui.DetailProject;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task.Model.Another.Example;
import com.example.task.R;
import com.example.task.helpers.SessionManager;
import com.example.task.helpers.UrlHelper;
import com.example.task.ui.SubTaskCompleteStaff.SubTaskCompleteStaffFragment;
import com.google.gson.Gson;

public class DetailProject extends Fragment {
    SessionManager sessionManager;
    LinearLayout pbLayout;
    ScrollView scrollView;
    TextView tvDescription, tvAssignBy, tvDeadline, tvStatus, tvTaskName;
    private int id_project;

    public DetailProject() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail_project, container, false);
        initializeComponent(root);
        return root;
    }

    protected void initializeComponent(View root){
        tvTaskName = root.findViewById(R.id.project_detail_project_name);
        tvDescription = root.findViewById(R.id.project_detail_description);
        tvAssignBy = root.findViewById(R.id.project_detail_assign_to_name);
        tvDeadline = root.findViewById(R.id.project_detail_deadline);
        tvStatus = root.findViewById(R.id.project_detail_status);
        scrollView = root.findViewById(R.id.project_detail_scrollview_container);
        pbLayout = root.findViewById(R.id.project_detail_pblayout);

        scrollView.setVisibility(View.GONE);
        refreshPage();
    }

    private void refreshPage() {

        if (getArguments() != null) {
            id_project = getArguments().getInt("id_project");
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String param_role_id = sessionManager.getUserDetail().get("role");
            String param_user_id = sessionManager.getUserDetail().get("id_user");
            String url_target = UrlHelper.base_task+"?role_id="+param_role_id+"&user_id="+param_user_id+"&project_id="+id_project;

            StringRequest request = new StringRequest(Request.Method.GET, url_target, response -> {
                Gson g = new Gson();
                Example subTask = g.fromJson(response, Example.class);
                String status = subTask.getDetailProject().getStatus();
                System.out.println("status "+status);
                tvTaskName.setText(subTask.getDetailProject().getProjectName());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvDescription.setText(Html.fromHtml(subTask.getDetailProject().getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvDescription.setText(Html.fromHtml(subTask.getDetailProject().getDescription()));
                }
                tvAssignBy.setText(subTask.getDetailProject().getAssignTo().getName());
                tvDeadline.setText(subTask.getDetailProject().getDeadline());
                tvStatus.setText(status);
                pbLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }, error -> {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            });
            queue.add(request);
        }
    }

//    public void close() {
//        Toast.makeText(getContext(), "coba", Toast.LENGTH_SHORT).show();
//        Fragment rm = getActivity().getSupportFragmentManager().findFragmentByTag("DetailSubTask");
//        Fragment fragment = new SubTaskCompleteStaffFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(rm);
//        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
//        fragmentTransaction.commit();
//    }
}