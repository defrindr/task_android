package com.example.task.Adapter;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.Model.Another.TaskAssign;
import com.example.task.Model.Staff.SubTask_;
import com.example.task.R;
import com.example.task.ui.DetailSubTask.DetailSubTaskFragment;

import java.util.List;

import static com.example.task.helpers.Constant.LIST_TASK_STATUS;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder>{
    private List<TaskAssign> list_task;
    private FragmentActivity c;
    private boolean clickable = true;

    public TaskAdapter(FragmentActivity c, List<TaskAssign> datum) {
        this.c = c;
        this.list_task = datum;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_list_task, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            System.out.println("test onBindViewHolder Task Adapter "+holder.mTextViewJudul);
            holder.mTextViewJudul.setText(Html.fromHtml(list_task.get(position).getTaskName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.mTextViewJudul.setText(Html.fromHtml(list_task.get(position).getTaskName()));
        }
        holder.mTextViewDate.setText(list_task.get(position).getDeadline());
        holder.mTextStatus.setText(LIST_TASK_STATUS.get(list_task.get(position).getStatus() - 1));

//        holder.itemView.setOnClickListener(view -> {});
    }

    @Override
    public int getItemCount () {
        return list_task == null ? 0 : list_task.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewJudul, mTextViewDate, mTextStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            mTextViewJudul = itemView.findViewById(R.id.project_detail_task_recycleview_description);
            mTextViewDate= itemView.findViewById(R.id.project_detail_task_recycleview_deadline);
            mTextStatus= itemView.findViewById(R.id.project_detail_task_recycleview_status);
        }
    }

    public void setClickable(boolean val){
        this.clickable = val;
    }

}