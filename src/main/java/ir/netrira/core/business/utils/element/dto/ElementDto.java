package ir.netrira.core.business.utils.element.dto;

import ir.netrira.core.models.application.utils.Element;

public class ElementDto {

    private String name;
    private String code;
    private String rootCode;

    public ElementDto(String name, String code, String typeCode) {
        this.name = name;
        this.code = code;
        this.rootCode = typeCode;
    }

    public ElementDto(Element element) {
        this.name = element.getName();
        this.code = element.getCode();
        this.rootCode =element.getRoot().getCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRootCode() {
        return rootCode;
    }

    public void setRootCode(String rootCode) {
        this.rootCode = rootCode;
    }
}
