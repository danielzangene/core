package ir.netrira.core.models.personnel.personnel;

import ir.netrira.core.models.BaseEntity;
import ir.netrira.core.models.personnel.systemaccess.GroupSystemAccess;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "t_User",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class User extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(max = 1000)
    private String password;

    @NotBlank
    @Size(max = 1000)
    private String name;

//    @NotBlank
//    @Size(max = 1000)
    private String role;

    private User supervisor;

    private List<GroupSystemAccess> groupAccess;

    public User() {
    }

    public User(String username, String email, String password, String name, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_supervisor")
    public User getSupervisor() {
        return supervisor;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mm_userGroupAccess",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    public List<GroupSystemAccess> getGroupAccess() {
        return groupAccess;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public void setGroupAccess(List<GroupSystemAccess> groupAccess) {
        this.groupAccess = groupAccess;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
