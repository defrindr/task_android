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

import com.example.task.Model.Staff.SubTask_;
import com.example.task.ui.DetailSubTask.DetailSubTaskFragment;
import com.example.task.R;

import java.util.List;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.MyViewHolder>{
    private List<SubTask_> list_sub_task;
    private FragmentActivity c;
    private boolean clickable = true;

    public SubTaskAdapter(FragmentActivity c, List<SubTask_> datum) {
        this.c = c;
        this.list_sub_task = datum;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_list_sub_task_staff, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.mTextViewJudul.setText(Html.fromHtml(list_sub_task.get(position).getTask().getTaskName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.mTextViewJudul.setText(Html.fromHtml(list_sub_task.get(position).getTask().getTaskName()));
        }
        holder.mTextViewDate.setText(list_sub_task.get(position).getDeadline());
        holder.mTextStatus.setText(list_sub_task.get(position).getStatus());

        holder.itemView.setOnClickListener(view -> {

            if(!clickable){
            }else{
                Bundle bundle=new Bundle();

                bundle.putInt("id_sub_task", list_sub_task.get(position).getId());

                Fragment fragment = new DetailSubTaskFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = c.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.add(R.id.nav_host_fragment, fragment, "DetailSubTask");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount () {
        return list_sub_task == null ? 0 : list_sub_task.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewJudul, mTextViewDate, mTextStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            mTextViewJudul = itemView.findViewById(R.id.sub_task_recycleview_description);
            mTextViewDate= itemView.findViewById(R.id.sub_task_recycleview_deadline);
            mTextStatus= itemView.findViewById(R.id.sub_task_recycleview_status);
        }
    }

    public void setClickable(boolean val){
        this.clickable = val;
    }

}