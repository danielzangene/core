package ir.netrira.core.business.utils.element.dto;

import ir.netrira.core.models.application.utils.Element;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data @Accessors(chain = true)
public class ElementListDto {
    String rootCode;
    List<Element> elements;
}
