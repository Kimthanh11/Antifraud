package antifraud.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponseDTO {
    @JsonProperty("result")
    private String result;

    // Constructors, getters, setters
    public TransactionResponseDTO() {
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
