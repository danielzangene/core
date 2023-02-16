package ir.netrira.core.models.application.personnel;

import ir.netrira.core.business.utils.element.ElementUtils;
import ir.netrira.core.models.BaseEntity;
import ir.netrira.core.models.application.systemaccess.GroupSystemAccess;
import ir.netrira.core.models.application.utils.Element;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class User extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    private String username;

    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(max = 1000)
    private String password;

    @NotBlank
    @Size(max = 1000)
    private String name;

    private Element role;

    public User(String username, String email, String password, String name, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = ElementUtils.getElement(role);
    }

    public User(String username, String email, String password, String name, Element role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
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

    @ManyToOne
    @JoinColumn(name = "c_role")
    public Element getRole() {
        return role;
    }

    public void setRole(Element role) {
        this.role = role;
    }
}
