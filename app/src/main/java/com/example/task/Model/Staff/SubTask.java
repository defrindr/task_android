
package com.example.task.Model.Staff;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubTask {

    @SerializedName("sub_task")
    @Expose
    private List<SubTask_> subTask = null;

    public List<SubTask_> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<SubTask_> subTask) {
        this.subTask = subTask;
    }

}
