package ir.netrira.core.models.application.utils;

import ir.netrira.core.models.SimpleEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Setter
@Entity
@Table(name = "t_Element",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "c_code")
        })
@NoArgsConstructor  @AllArgsConstructor
public class Element extends SimpleEntity {

    private String name;
    private String code;
    private String rootCode;

    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    @Column(name = "c_code")
    public String getCode() {
        return code;
    }

    @Column(name = "c_root")
    public String getRootCode() {
        return rootCode;
    }
}
