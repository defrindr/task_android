
package com.example.task.Model.Staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubTask_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_task")
    @Expose
    private Integer idTask;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("assign_by")
    @Expose
    private AssignBy assignBy;
    @SerializedName("assign_to")
    @Expose
    private AssignTo assignTo;
    @SerializedName("deadline")
    @Expose
    private String deadline;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("komentar")
    @Expose
    private String komentar;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tanggal_pengajuan")
    @Expose
    private String  tanggalPengajuan;
    @SerializedName("tanggal_acc")
    @Expose
    private String tanggalAcc;
    @SerializedName("task")
    @Expose
    private Task task;
    @SerializedName("project")
    @Expose
    private Project project;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public AssignBy getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(AssignBy assignBy) {
        this.assignBy = assignBy;
    }

    public AssignTo getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(AssignTo assignTo) {
        this.assignTo = assignTo;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public void setTanggalPengajuan(String tanggalPengajuan) {
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public String getTanggalAcc() {
        return tanggalAcc;
    }

    public void setTanggalAcc(String tanggalAcc) {
        this.tanggalAcc = tanggalAcc;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
