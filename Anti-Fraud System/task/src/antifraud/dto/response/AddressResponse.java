package antifraud.dto.response;

public class AddressResponse {
    private Long id;
    private String ip;


    public AddressResponse(Long id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

}
