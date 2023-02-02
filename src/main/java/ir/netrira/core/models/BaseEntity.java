package ir.netrira.core.models;

import ir.netrira.core.application.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class BaseEntity extends SimpleEntity {

    private String creationDateTime;
    private String lastModifiedDateTime;

    @Column(name = "c_creationDateTime")
    public String getCreationDateTime() {
        return creationDateTime;
    }

    @Column(name = "c_lastModifiedDateTime")
    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    @PrePersist
    protected void onCreate() {
        String currentDateTime = DateUtil.getCurrentDateTime();
        creationDateTime = currentDateTime;
        lastModifiedDateTime = currentDateTime;
    }

    @PreUpdate
    protected void onUpdate() {
        creationDateTime = creationDateTime;
        lastModifiedDateTime = DateUtil.getCurrentDateTime();
    }

}
