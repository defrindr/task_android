
package com.example.task.Model.Another;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("project")
    @Expose
    private List<Project> project = null;
    @SerializedName("detailProject")
    @Expose
    private DetailProject detailProject;
    @SerializedName("tasks")
    @Expose
    private List<Object> tasks = null;
    @SerializedName("tasksSubmitted")
    @Expose
    private List<Object> tasksSubmitted = null;
    @SerializedName("tasksClosed")
    @Expose
    private List<TasksClosed> tasksClosed = null;
    @SerializedName("taskAssign")
    @Expose
    private List<TaskAssign> taskAssign = null;
    @SerializedName("persentase")
    @Expose
    private Integer persentase;

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }

    public DetailProject getDetailProject() {
        return detailProject;
    }

    public void setDetailProject(DetailProject detailProject) {
        this.detailProject = detailProject;
    }

    public List<Object> getTasks() {
        return tasks;
    }

    public void setTasks(List<Object> tasks) {
        this.tasks = tasks;
    }

    public List<Object> getTasksSubmitted() {
        return tasksSubmitted;
    }

    public void setTasksSubmitted(List<Object> tasksSubmitted) {
        this.tasksSubmitted = tasksSubmitted;
    }

    public List<TasksClosed> getTasksClosed() {
        return tasksClosed;
    }

    public void setTasksClosed(List<TasksClosed> tasksClosed) {
        this.tasksClosed = tasksClosed;
    }

    public List<TaskAssign> getTaskAssign() {
        return taskAssign;
    }

    public void setTaskAssign(List<TaskAssign> taskAssign) {
        this.taskAssign = taskAssign;
    }

    public Integer getPersentase() {
        return persentase;
    }

    public void setPersentase(Integer persentase) {
        this.persentase = persentase;
    }

}
