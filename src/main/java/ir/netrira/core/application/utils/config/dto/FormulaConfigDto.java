package ir.netrira.core.application.utils.config.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data @Accessors(chain = true) 
public class FormulaConfigDto {
    private String expression;
    private List<String> argument;
}
