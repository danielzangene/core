package ir.netrira.core.models.application.systemaccess;

import ir.netrira.core.models.SimpleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_SystemAccess",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "method"),
                @UniqueConstraint(columnNames = "requestUri")
        })
public class SystemAccess extends SimpleEntity {

    private String method;
    private String requestUri;
    private String name;

    public SystemAccess() {
    }

    public SystemAccess(String method, String requestUri, String name) {
        this.method = method;
        this.requestUri = requestUri;
        this.name = name;
    }

    @NotBlank
    @Size(max = 10)
    public String getMethod() {
        return method;
    }

    @NotBlank
    @Size(max = 1000)
    public String getRequestUri() {
        return requestUri;
    }

    @NotBlank
    @Size(max = 1000)
    public String getName() {
        return name;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public void setName(String name) {
        this.name = name;
    }
}
