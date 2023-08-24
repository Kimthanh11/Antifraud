package antifraud.dto.response;

public class ChangeUserStatusResponse {

    public String status;

    public ChangeUserStatusResponse(String username, Boolean locked) {
        String status = locked ? "locked!" : "unlocked!";
        this.status = "User " + username + " " + status;
    }


}