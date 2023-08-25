package antifraud.service;


import antifraud.dto.request.CardRequest;
import antifraud.dto.response.CardResponse;
import antifraud.dto.response.DeleteCardResponse;
import antifraud.entity.Card;
import antifraud.persistence.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    //save Card
    public CardResponse saveCard(CardRequest cardRequest) {
        if (cardRepository.existsByNumber(cardRequest.getNumber())) {
            throw new UnsupportedOperationException("Card number already exists");
        }

        if (!isValidCardNumber(cardRequest.getNumber())) {
            throw new UnsupportedOperationException("Wrong format");
        }
        Card card = new Card(cardRequest.getNumber());
        cardRepository.save(card);
        return new CardResponse(card.getId(), card.getNumber());
    }

    //delete card
    public DeleteCardResponse deleteCard(String number){
        Card card = cardRepository.findByNumber(number).orElse(null);

        if (card != null) {
            cardRepository.delete(card);
        } else {
            throw new UnsupportedOperationException("CARD NOT FOUND");
        }

        return new DeleteCardResponse(number);
    }

    // get all stolen cards
    public List<Card> getAllCard(){ return (List<Card>) cardRepository.findAll();}

    public static boolean isValidCardNumber(String cardNumber) {
        int nDigits = cardNumber.length();
        int nSum = 0;
        boolean isSecond = false;

        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNumber.charAt(i) - '0';
            if (isSecond)
                d = d * 2;

            // Add two digits to handle cases where doubling results in a two-digit number
            nSum += d / 10 + d % 10;
            isSecond = !isSecond;
        }

        return nSum % 10 == 0;
    }

}
