package com.example.task.ui.DetailProject;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task.Adapter.TaskAdapter;
import com.example.task.Model.Another.Example;
import com.example.task.R;
import com.example.task.helpers.SessionManager;
import com.example.task.helpers.UrlHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailProject extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SessionManager sessionManager;
    LinearLayout pbLayout;
    ScrollView scrollView;
    TextView tvDescription, tvAssignBy, tvDeadline, tvStatus, tvTaskName;
    private int id_project;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    TaskAdapter adapter;
    PieChart pieChart;
    String persentase;
    List<PieEntry> pieEntires;

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
        rv = root.findViewById(R.id.project_detail_taskRecyclerView);
        swipeRefreshLayout = root.findViewById(R.id.project_detail_task_swipeRefresh);

        pieChart = (PieChart) root.findViewById(R.id.chart);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryColor,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        scrollView.setVisibility(View.GONE);


        refreshPage();
    }

    private void setupPieChart(){
        PieDataSet dataSet = new PieDataSet(pieEntires,"Task");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);
        //Get the chart
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setCenterText(persentase+"% \n ");
        pieChart.setDrawEntryLabels(true);
        pieChart.setContentDescription("");
        pieChart.getDescription().setEnabled(false);
//        pieChart.setDrawMarkers(true);
//        pieChart.setMaxHighlightDistance(34);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setHoleRadius(75);

        //legend attributes
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setFormSize(20);
        legend.setFormToTextSpace(2);
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
                persentase = subTask.getPersentase().toString();
                pieEntires = new ArrayList<>();
                pieEntires.add(new PieEntry(subTask.getTaskAssign().size(), getActivity()));
                pieEntires.add(new PieEntry(subTask.getTasksSubmitted().size(), getActivity()));
                pieEntires.add(new PieEntry(subTask.getTasksClosed().size(), getActivity()));
                setupPieChart();

                tvTaskName.setText(subTask.getDetailProject().getProjectName());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvDescription.setText(Html.fromHtml(subTask.getDetailProject().getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvDescription.setText(Html.fromHtml(subTask.getDetailProject().getDescription()));
                }
                tvAssignBy.setText(subTask.getDetailProject().getAssignTo().getName());
                tvDeadline.setText(subTask.getDetailProject().getDeadline());
                tvStatus.setText(status);


                if(subTask.getTasks().size() > 0){
                    Toast.makeText(getActivity(), "Loading Complete", Toast.LENGTH_SHORT).show();
                    adapter = new TaskAdapter(getActivity(), subTask.getTasks());
                    rv.setAdapter(adapter);
                    pbLayout.setVisibility(View.GONE);
                }

                pbLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }, error -> {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            });
            queue.add(request);
        }
    };

    @Override
    public void onRefresh() {
        refreshPage();
        swipeRefreshLayout.setRefreshing(false);
    }

}