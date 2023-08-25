package antifraud.dto.response;

public class CardResponse {
    private Long id;
    private final String number;
    public CardResponse(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Long getId(){ return id;}

    public String getNumber() { return number;}


}
