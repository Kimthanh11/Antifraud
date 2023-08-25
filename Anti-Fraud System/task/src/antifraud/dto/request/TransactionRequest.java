package antifraud.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequest {
    @JsonProperty("amount")
    private Long amount;
    private String ip;
    private String number;

    // Getter and Setter methods

    public Long getAmount() {
        return amount;
    }

    public String getNumber() {
        return number;
    }

    public String getIp() {
        return ip;
    }
}
