package antifraud.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionResponseDTO {
    @JsonProperty("result")
    private String result;
    private String info;

    // Constructors, getters, setters
    public TransactionResponseDTO() {
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInfo() {
        return this.info;
    }


    public void setInfo(String info) {
        this.info = info;
    }
}
