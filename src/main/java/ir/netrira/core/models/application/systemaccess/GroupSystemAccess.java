package ir.netrira.core.models.application.systemaccess;

import ir.netrira.core.models.SimpleEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_GroupSystemAccess")
public class GroupSystemAccess extends SimpleEntity {

    private List<SystemAccess> accessList;
    private String name;

    @ManyToMany
    @JoinTable(name = "mm_groupAccess",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "access_id"))
    public List<SystemAccess> getAccessList() {
        return accessList;
    }

    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    public void setAccessList(List<SystemAccess> accessList) {
        this.accessList = accessList;
    }

    public void setName(String name) {
        this.name = name;
    }
}
