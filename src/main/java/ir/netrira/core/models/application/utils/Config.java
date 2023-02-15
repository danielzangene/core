package ir.netrira.core.models.application.utils;

import ir.netrira.core.models.SimpleEntity;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Setter
@Entity
@Table(name = "t_Config",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"c_code", "c_root"})
        })
public class Config extends SimpleEntity {

    private String code;
    private String value;
    private Config root;

    @Column(name = "c_code")
    public String getCode() {
        return code;
    }

    @Column(name = "c_value")
    public String getValue() {
        return value;
    }

    @ManyToOne()
    @JoinColumn(name = "c_root")
    public Config getRoot() {
        return root;
    }
}
