package antifraud.dto.response;

public class DeleteResponseDTO {
    private String username;
    private String status;

    public DeleteResponseDTO(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return "Deleted successfully!";
    }
}
