package ir.netrira.core.business.management.element.dto;

import ir.netrira.core.models.management.Element;

public class ElementDto {

    private String name;
    private String code;
    private String typeCode;

    public ElementDto(String name, String code, String typeCode) {
        this.name = name;
        this.code = code;
        this.typeCode = typeCode;
    }

    public ElementDto(Element element) {
        this.name = element.getName();
        this.code = element.getCode();
        this.typeCode =element.getType().getCode();
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
