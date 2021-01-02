
package com.example.task.Model.Another;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailProject {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("project_name")
    @Expose
    private String projectName;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("assign_to")
    @Expose
    private AssignTo_ assignTo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deadline")
    @Expose
    private String deadline;
    @SerializedName("tanggal_pengajuan")
    @Expose
    private Object tanggalPengajuan;
    @SerializedName("tanggal_acc")
    @Expose
    private Object tanggalAcc;
    @SerializedName("komentar")
    @Expose
    private Object komentar;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("updated_by")
    @Expose
    private Integer updatedBy;
    @SerializedName("flag")
    @Expose
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssignTo_ getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(AssignTo_ assignTo) {
        this.assignTo = assignTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Object getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public void setTanggalPengajuan(Object tanggalPengajuan) {
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public Object getTanggalAcc() {
        return tanggalAcc;
    }

    public void setTanggalAcc(Object tanggalAcc) {
        this.tanggalAcc = tanggalAcc;
    }

    public Object getKomentar() {
        return komentar;
    }

    public void setKomentar(Object komentar) {
        this.komentar = komentar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
