package antifraud.dto.response;

public class DeleteCardResponse {
    private final String status;
    public DeleteCardResponse(String number) {
        this.status = String.format("Card %s successfully removed!", number);
    }

    public String getStatus(){ return status;}
}
