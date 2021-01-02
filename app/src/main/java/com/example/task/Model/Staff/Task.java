
package com.example.task.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_project")
    @Expose
    private Integer idProject;
    @SerializedName("task_name")
    @Expose
    private String taskName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tanggal_acc")
    @Expose
    private Object tanggalAcc;
    @SerializedName("deadline")
    @Expose
    private String deadline;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("flag")
    @Expose
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProject() {
        return idProject;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getTanggalAcc() {
        return tanggalAcc;
    }

    public void setTanggalAcc(Object tanggalAcc) {
        this.tanggalAcc = tanggalAcc;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
