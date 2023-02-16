package ir.netrira.core.models.application.systemaccess;

import ir.netrira.core.models.SimpleEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_GroupSystemAccess",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "c_name"),
        })
@NoArgsConstructor @AllArgsConstructor
public class GroupSystemAccess extends SimpleEntity {

    private String name;
    private String code;
    private List<SystemAccess> accessList;

    @ManyToMany
    @JoinTable(name = "mm_groupAccess",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "access_id"))
    public List<SystemAccess> getAccessList() {
        return accessList;
    }

    @Column(name = "c_code")
    public String getCode() {
        return code;
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
