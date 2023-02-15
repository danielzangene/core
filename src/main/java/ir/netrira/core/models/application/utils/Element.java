package ir.netrira.core.models.application.utils;

import ir.netrira.core.models.SimpleEntity;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Setter
@Entity
@Table(name = "t_Element")
public class Element extends SimpleEntity {

    private String name;
    private String code;
    private Element root;

    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    @Column(name = "c_code")
    public String getCode() {
        return code;
    }

    @ManyToOne()
    @JoinColumn(name = "c_root")
    public Element getRoot() {
        return root;
    }
}
