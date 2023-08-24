package antifraud.dto.request;

public class ChangeUserStatusRequest {
    private String username;

    private String operation;

    public String getUsername() {
        return username;
    }

    public String getOperation() {
        return operation;
    }
}