package ir.netrira.core.exception;

public class AccessException extends RuntimeException {

    private String useCaseName;

    public AccessException(String useCaseName) {
        super("no access to :" + useCaseName);
        this.useCaseName = useCaseName;
    }

    public String getUseCaseName() {
        return useCaseName;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }
}
