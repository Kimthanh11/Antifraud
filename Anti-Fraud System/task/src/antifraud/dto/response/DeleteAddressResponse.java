package antifraud.dto.response;

public class DeleteAddressResponse {
    private final String status;
    public DeleteAddressResponse(String ipAddress) {
        this.status = String.format("IP %s successfully removed!", ipAddress);
    }

    public String getStatus(){
        return status;
    }
}
