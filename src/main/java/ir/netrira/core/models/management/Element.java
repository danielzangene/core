package ir.netrira.core.models.management;

import ir.netrira.core.models.SimpleEntity;

import javax.persistence.*;

@Entity
@Table(name = "t_Element")
public class Element extends SimpleEntity {

    private String name;
    private String code;

    private Type type;

    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    @Column(name = "c_code")
    public String getCode() {
        return code;
    }

    @ManyToOne()
    @JoinColumn(name = "c_Type")
    public Type getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
