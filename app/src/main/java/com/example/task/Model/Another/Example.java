
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
    private List<TaskAssign> tasks = null;
    @SerializedName("tasksSubmitted")
    @Expose
    private List<TaskAssign> tasksSubmitted = null;
    @SerializedName("tasksClosed")
    @Expose
    private List<TaskAssign> tasksClosed = null;
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

    public List<TaskAssign> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskAssign> tasks) {
        this.tasks = tasks;
    }

    public List<TaskAssign> getTasksSubmitted() {
        return tasksSubmitted;
    }

    public void setTasksSubmitted(List<TaskAssign> tasksSubmitted) {
        this.tasksSubmitted = tasksSubmitted;
    }

    public List<TaskAssign> getTasksClosed() {
        return tasksClosed;
    }

    public void setTasksClosed(List<TaskAssign> tasksClosed) {
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
