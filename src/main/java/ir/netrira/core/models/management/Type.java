package ir.netrira.core.models.management;

import ir.netrira.core.models.SimpleEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_Type")
public class Type extends SimpleEntity {
    private String code;
    private List<Element> elements;

    @Column(name = "c_code")
    public String getCode() {
        return code;
    }
    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    @OrderColumn(name = "i_index")
    public List<Element> getElements() {
        return elements;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
